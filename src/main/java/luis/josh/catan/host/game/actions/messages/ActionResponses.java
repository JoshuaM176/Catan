package luis.josh.catan.host.game.actions.messages;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ActionResponses {
    
    public static JSONObject discardHalf() {
        String jsonString = """
        {
            "action": "discard",
            "data": {},
            "players": "all"
        }
                """;
        JSONObject data = (JSONObject)JSONValue.parse(jsonString);
        return data;
    }

    public static JSONObject moveRobber() {
        String jsonString = """
        {
            "action": "moveRobber",
            "data": {},
            "players": "self"
        }       
                """;
        JSONObject data = (JSONObject)JSONValue.parse(jsonString);
        return data;
    }

}
