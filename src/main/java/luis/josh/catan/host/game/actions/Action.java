package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

public interface Action {
     
    public JSONObject execute(JSONObject data);

}
