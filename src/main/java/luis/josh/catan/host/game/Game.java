package luis.josh.catan.host.game;

import java.util.HashMap;
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
import luis.josh.catan.host.game.events.Event;
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
    private int turn;
    private Event currentEvent = null;
    private static final Logger logger = HostLogger.getLogger(Game.class);

    public Game(Consumer<JSONObject> messageQueue, int players) {
        logger.info("Initializing game...");
        this.messageQueue = messageQueue;
        logger.info("Generating board...");
        this.board = generateBoard();
        logger.info("\n{}", board.toString());
        this.players = new Player[players];
        this.actionManager = new ActionManager(this.players, generateActions(this.board, this.players));
        for(int i = 0; i < players; i++) {
            this.players[i] = new Player(e -> processEvent(e), i);
        }
        this.eventManager = new EventManager(board, this.players, generateEvents());
    }

    private Board generateBoard() {
        return new Board(
            getTilePattern(),
            getNumberTokenAssigner(),
            getTileCreator(),
            getHarborAssigner()
        );
    };

    public int[][] getTilePattern() {
        return new int[][] {
            {0,1,1,1,0},
            {1,1,1,1,0},
            {1,1,1,1,1},
            {1,1,1,1,0},
            {0,1,1,1,0}
        };
    }

    public NumberTokenAssigner getNumberTokenAssigner() {
        int[] numberTokens = new int[] {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};
        return new DefaultNumberTokenAssigner(numberTokens);
    }

    public TileCreator getTileCreator() {
        return new DefaultTileCreator(Map.of(
            Resource.STONE, 3,
            Resource.BRICK, 3,
            Resource.WHEAT, 4,
            Resource.LOG, 4,
            Resource.SHEEP, 4,
            Resource.DESERT, 1
        ));
    }

    public HarborAssigner getHarborAssigner() {
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

    public Map<String, Action> generateActions(Board board, Player[] players) {
        return generateDefaultActions(board, players);
    };

    public static Map<String, Action> generateDefaultActions(Board board, Player[] players) {
        HashMap<String, Action> actionMap = new HashMap<>();
        actionMap.put("placeCity", new PlaceCity(board, Map.of(
            Resource.WHEAT, 2,
            Resource.STONE, 3
        )));
        actionMap.put("placeRoad", new PlaceRoad(board, Map.of(
            Resource.BRICK, 1,
            Resource.LOG, 1
        )));
        actionMap.put("placeSettlement", new PlaceSettlement(board, Map.of(
            Resource.BRICK, 1,
            Resource.LOG, 1,
            Resource.SHEEP, 1,
            Resource.WHEAT, 1
        )));
        actionMap.put("rollDice", new RollDice(board));
        return actionMap;
    }

    public Map<String, Function<JSONObject, Event>> generateEvents() {
        return generateDefaultEvents();
    }

    public Map<String, Function<JSONObject, Event>> generateDefaultEvents() {
        return Map.of();
    }

    public void acceptData(JSONObject data) {
        logger.info("Host recieved incoming message: {}", data);
        if(currentEvent != null) {
            if(currentEvent.acceptData(data)) {
                currentEvent = null;
            };
            return;
        }
        if(data.containsKey("action")) {
            executeAction(data);
        }
    }

    private void executeAction(JSONObject data) {
        JSONObject[] results = actionManager.executeAction(data);
        for(JSONObject jsonObject: results) {
            processEvent(jsonObject);
        }
    }

    @SuppressWarnings("unchecked")
    private void processEvent(JSONObject data) {
        data.put("turn", turn);
        Event event = eventManager.processEvent(data);
        if(event != null) {
            currentEvent = event;
        }
        messageQueue.accept(data);
    }

    private void nextTurn() {
        turn = turn >= players.length ? turn++ : 0;
        messageQueue.accept(EventResponses.eventResponse(
            "changeTurn",
            "all",
            new JSONObject(Map.of(
                "player", turn
            ))
        ));
    }

}
