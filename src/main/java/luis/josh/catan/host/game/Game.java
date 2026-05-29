package luis.josh.catan.host.game;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.harborassigner.DefaultHarborAssigner;
import luis.josh.catan.host.game.board.tile.harborassigner.HarborAssigner;
import luis.josh.catan.host.game.board.tile.numbertokenassigner.DefaultNumberTokenAssigner;
import luis.josh.catan.host.game.board.tile.numbertokenassigner.NumberTokenAssigner;
import luis.josh.catan.host.game.board.tile.tilecreator.DefaultTileCreator;
import luis.josh.catan.host.game.board.tile.tilecreator.TileCreator;
import luis.josh.catan.host.game.eventmanager.EventManager;
import luis.josh.catan.host.game.events.DiscardEvent;
import luis.josh.catan.host.game.events.Event;
import luis.josh.catan.host.game.events.MoveRobberEvent;
import luis.josh.catan.host.game.events.SetupEvent;
import luis.josh.catan.host.game.gamepieces.cards.CardDeck;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.DevelopmentCard;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.Knight;
import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.host.HostLogger;
import luis.josh.catan.host.game.actionmanager.ActionManager;
import luis.josh.catan.host.game.actions.*;
import luis.josh.catan.host.game.actions.messages.EventResponses;

public abstract class Game {
    
    private Consumer<JSONObject> messageQueue;
    private Board board;
    private ActionManager actionManager;
    private EventManager eventManager;
    private Player[] players;
    private int turn = 0;
    private Event currentEvent = null;
    private static final Logger logger = HostLogger.getLogger(Game.class);

    public Game(Consumer<JSONObject> messageQueue, int players) {
        logger.info("Initializing game...");
        this.messageQueue = messageQueue;
        logger.info("Generating board...");
        this.board = generateBoard();
        logger.info("\n{}", board.toString());
        this.players = new Player[players];
        for(int i = 0; i < players; i++) {
            this.players[i] = new Player(e -> processEvent(e), i);
        }
        this.actionManager = generateActions(board, this.players);
        this.eventManager = new EventManager(board, this.players, this.messageQueue, generateEvents());
        startGame();
    }

    protected void startGame() {
        processEvent(EventResponses.eventResponse("setupTrigger", "none", new JSONObject()));
    }    

    protected Board generateBoard() {
        return new Board(
            getTilePattern(),
            getNumberTokenAssigner(),
            getTileCreator(),
            getHarborAssigner()
        );
    };

    protected int[][] getTilePattern() {
        return new int[][] {
            {0,1,1,1,0},
            {1,1,1,1,0},
            {1,1,1,1,1},
            {1,1,1,1,0},
            {0,1,1,1,0}
        };
    }

    protected NumberTokenAssigner getNumberTokenAssigner() {
        int[] numberTokens = new int[] {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};
        return new DefaultNumberTokenAssigner(numberTokens);
    }

    protected TileCreator getTileCreator() {
        return new DefaultTileCreator(Map.of(
            Resource.STONE, 3,
            Resource.BRICK, 3,
            Resource.WHEAT, 4,
            Resource.LOG, 4,
            Resource.SHEEP, 4,
            Resource.DESERT, 1
        ));
    }

    protected HarborAssigner getHarborAssigner() {
        return new DefaultHarborAssigner(9,
            Map.of(
                Resource.STONE, 1,
                Resource.BRICK, 1,
                Resource.WHEAT, 1,
                Resource.LOG, 1,
                Resource.SHEEP, 1,
                Resource.ANY, 4
            )
        );
    }

    protected ActionManager generateActions(Board board, Player[] players) {
        ActionManager actionManager = new ActionManager(players, Map.of());
        for(Action action: generateDefaultActions(board, players)){
            actionManager.addAction(action);
        }
        return actionManager;
    };

    protected Action[] generateDefaultActions(Board board, Player[] players) {
        return new Action[]{
            new PlaceCity(board, Map.of(
                Resource.STONE, 3,
                Resource.WHEAT, 2
            )),
            new PlaceRoad(board, Map.of(
                Resource.BRICK, 1,
                Resource.LOG, 1
            )),
            new PlaceSettlement(board, Map.of(
                Resource.BRICK, 1,
                Resource.LOG, 1,
                Resource.SHEEP, 1,
                Resource.WHEAT, 1
            )),
            new RollDice(board),
            new PurchaseDevelopmentCard(
                generateDefaultDevCards(),
                Map.of(
                    Resource.SHEEP, 1,
                    Resource.STONE, 1,
                    Resource.WHEAT, 1
                )
            )
        };
    }

    protected CardDeck<DevelopmentCard> generateDefaultDevCards() {
        CardDeck<DevelopmentCard> cardDeck = new CardDeck<>(Map.of(
            new Knight(), 14
        ));
        return cardDeck;
    }

    protected Map<String, Function<JSONObject, Event>> generateEvents() {
        return generateDefaultEvents();
    }

    protected Map<String, Function<JSONObject, Event>> generateDefaultEvents() {
        return Map.of(
            "moveRobberTrigger", data -> new MoveRobberEvent(data, turn),
            "discardTrigger", data -> new DiscardEvent(data),
            "setupTrigger", data -> new SetupEvent(() -> prevTurn(), () -> nextTurn(), () -> turn)
        );
    }

    public void acceptData(JSONObject data) {
        logger.info("Host recieved incoming message: {}", data);
        if(currentEvent != null) {
            processEvents(currentEvent.acceptData(data));
            if(currentEvent.isFinished()){
                messageQueue.accept(new JSONObject(
                    Map.of("event", "specialEventFinished")
                ));
                currentEvent = eventManager.next();
            }
            return;
        }
        executeAction(data);
    }

    private void executeAction(JSONObject data) {
        JSONObject[] results = actionManager.executeAction(data);
        processEvents(results);
    }

    private void processEvents(JSONObject[] data) {
        for(JSONObject jsonObject: data) {
            processEvent(jsonObject);
        }
    }

    private void processEvent(JSONObject data) {
        logger.debug("Processing event: {}", data);
        if(eventManager.processEvent(data)) {
            if(currentEvent == null) {
                currentEvent = eventManager.next();
            }
        };
        messageQueue.accept(data);
    }

    private void nextTurn() {
        turn = (turn >= players.length) ? 0 : ++turn;
        messageQueue.accept(EventResponses.eventResponse(
            "changeTurn",
            "all",
            new JSONObject(Map.of(
                "player", turn
            ))
        ));
    }

    private void prevTurn() {
        turn = (turn <= 0) ? players.length - 1 : --turn;
        messageQueue.accept(EventResponses.eventResponse(
            "changeTurn",
            "all",
            new JSONObject(Map.of(
                "player", turn
            ))
        ));
    }

    @Override
    public String toString() {
        String string = "";
        System.out.println("Board: " + board.toString());
        System.out.println("Players:");
        for(int i = 0; i < players.length; i++) {
            System.out.println("Player " + i + ":");
            System.out.println(players[i]);
        }
        return string;
    }
}
