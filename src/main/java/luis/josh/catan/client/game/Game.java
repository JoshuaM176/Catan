package luis.josh.catan.client.game;

import javax.swing.JFrame;

import org.json.simple.JSONObject;
import org.slf4j.Logger;

import luis.josh.catan.client.ClientLogger;
import luis.josh.catan.client.game.board.Board;
import luis.josh.catan.client.game.eventmanager.EventManager;
import luis.josh.catan.client.game.events.Event;

public abstract class Game {
    public JFrame frame = new JFrame();
    public Board board;
    protected EventManager eventManager;
    protected static final Logger logger = ClientLogger.getLogger(Game.class);

    public Game() {
        eventManager = new EventManager(generateEvents(board));
        frame.setSize(1600, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    protected abstract Event[] generateEvents(Board board);

    public void redraw(int size) {
        board.redraw(size);
    }

    public void acceptData(JSONObject data) {
        logger.info("Client recieved incoming message: {}", data);
        executeEvent(data);
    }

    protected void executeEvent(JSONObject data) {
        if(data.containsKey("event")){
            eventManager.executeEvent(data);
        }
    }
}
