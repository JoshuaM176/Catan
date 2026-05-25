package luis.josh.catan.host.game;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.host.HostLogger;
import luis.josh.catan.host.game.actionmanager.ActionManager;
import luis.josh.catan.host.game.actions.*;

public abstract class Game {
    
    private Consumer<JSONObject> messageQueue;
    private Board board;
    private ActionManager actionManager;
    private Player[] players;
    private static final Logger logger = HostLogger.getLogger(Game.class);

    public Game(Consumer<JSONObject> messageQueue, int players) {
        this.messageQueue = messageQueue;
        this.board = generateBoard();
        this.players = new Player[players];
        this.actionManager = new ActionManager(this.players, generateActions(this.board, this.players));
        for(int i = 0; i < players; i++) {
            this.players[i] = new Player();
        }
    }

    public abstract Board generateBoard();

    public abstract Map<String, Action> generateActions(Board board, Player[] players);

    public void acceptData(JSONObject data) {
        logger.info("Host recieved incoming message: {}", data);
        if(data.containsKey("action")) {
            executeAction(data);
        }
    }

    private void executeAction(JSONObject data) {
        JSONObject[] results = actionManager.executeAction(data);
        for(JSONObject jsonObject: results) {
            messageQueue.accept(jsonObject);
        }
    }
    
}
