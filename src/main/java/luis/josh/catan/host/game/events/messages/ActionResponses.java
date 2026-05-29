package luis.josh.catan.host.game.events.messages;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import luis.josh.catan.util.JSONUtil;

public class ActionResponses {

    public static JSONObject actionResponse(String action, int player, JSONObject data) {
        return actionResponse(action, JSONUtil.ArrayToJSON(new Integer[]{player}), data);
    }

    public static JSONObject actionResponse(String action, JSONArray players, JSONObject data) {
        JSONObject rtn = new JSONObject(
            Map.of(
                "action", action,
                "players", players,
                "data", data
            )
        );
        return rtn;
    }

}
