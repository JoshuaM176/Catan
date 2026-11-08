package luis.josh.catan.client.game.events;

import org.json.simple.JSONObject;

public interface Event {
     
    /**
     * Execute the event using the data and player passed.
     * @param data The data from the action json.
     * @return The resulting events from the execution.
     */
    public void execute(JSONObject data);

}
