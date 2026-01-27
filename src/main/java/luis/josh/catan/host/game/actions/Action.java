package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.player.Player;

// By default, JSONObject returned will be sent to all players

public interface Action {
     
    public JSONObject execute(JSONObject data, Player player);

}
