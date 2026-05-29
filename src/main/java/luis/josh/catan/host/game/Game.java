package luis.josh.catan.host.game;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.tile.harborassigner.HarborAssigner;
import luis.josh.catan.host.game.board.tile.numbertokenassigner.NumberTokenAssigner;
import luis.josh.catan.host.game.board.tile.tilecreator.TileCreator;
import luis.josh.catan.host.game.eventmanager.EventManager;
import luis.josh.catan.host.game.events.Event;
import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.host.HostLogger;
import luis.josh.catan.host.game.actionmanager.ActionManager;
import luis.josh.catan.host.game.actions.*;
import luis.josh.catan.host.game.actions.messages.EventResponses;

public abstract class Game {
    
    protected Consumer<JSONObject> messageQueue;
    protected Board board;
    protected ActionManager actionManager;
    protected EventManager eventManager;
    protected Player[] players;
    protected int turn = 0;
    protected Event currentEvent = null;
    protected static final Logger logger = HostLogger.getLogger(Game.class);

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
        this.actionManager = new ActionManager(this.players, generateActions(board, this.players));
        this.eventManager = new EventManager(board, this.players, this.messageQueue, generateEvents());
        startGame();
    }

    protected abstract void startGame();

    protected Board generateBoard() {
        return new Board(
            getTilePattern(),
            getNumberTokenAssigner(),
            getTileCreator(),
            getHarborAssigner()
        );
    };

    protected abstract int[][] getTilePattern();

    protected abstract NumberTokenAssigner getNumberTokenAssigner();

    protected abstract TileCreator getTileCreator();

    protected abstract HarborAssigner getHarborAssigner();

    protected abstract Action[] generateActions(Board board, Player[] players);

    protected abstract Map<String, Function<JSONObject, Event>> generateEvents();

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

    protected void executeAction(JSONObject data) {
        JSONObject[] results = actionManager.executeAction(data);
        processEvents(results);
    }

    protected void processEvents(JSONObject[] data) {
        for(JSONObject jsonObject: data) {
            processEvent(jsonObject);
        }
    }

    protected void processEvent(JSONObject data) {
        logger.debug("Processing event: {}", data);
        if(eventManager.processEvent(data)) {
            if(currentEvent == null) {
                currentEvent = eventManager.next();
            }
        };
        messageQueue.accept(data);
    }

    protected void nextTurn() {
        turn = (turn >= players.length) ? 0 : ++turn;
        messageQueue.accept(EventResponses.eventResponse(
            "changeTurn",
            "all",
            new JSONObject(Map.of(
                "player", turn
            ))
        ));
    }

    protected void prevTurn() {
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
