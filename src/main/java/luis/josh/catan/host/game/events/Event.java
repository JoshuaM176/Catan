package luis.josh.catan.host.game.events;

import java.util.function.Consumer;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.player.Player;

/**
 * Event class that will temporarily take over action execution from the game.
 */
public interface Event {

    /**
     * Method that is called when the event becomes active.
     * @param board Board from the current game.
     * @param players Players from the current game.
     * @param messageQueue Message queue for sending event data back to the client.
     */
    public void initialize(Board board, Player[] players, Consumer<JSONObject> messageQueue);
  
    /**
     * Called while this event is active and data is recieved from the client.
     * @param data The data(action) sent by the client.
     * @return Event results of execution that should be processed by the game.
     */
    public JSONObject[] acceptData(JSONObject data);

    /**
     * Should return true when the event is over.
     * @return True if the event is over.
     */
    public boolean isFinished();

    /**
     * The name of the event, used for messages sent back to client.
     * @return The name of the event.
     */
    public String getName();
    
}
