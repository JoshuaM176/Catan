package luis.josh.catan.host.game.actions;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.player.Player;

public interface Action {
     
    /**
     * Execute the action using the data and player passed.
     * @param data The data from the action json.
     * @param player The player object to execute the action for.
     * @return The resulting events from the execution.
     */
    public JSONObject[] execute(JSONObject data, Player player);

    /**
     * Add {"player": playerNum} to data.
     * @param data The data to update.
     * @param playerNum The player number to set.
     */
    @SuppressWarnings("unchecked")
    public static void addPlayer(JSONObject data, int playerNum) {
        data.put("player", playerNum);
    };   
}
