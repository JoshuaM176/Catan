package luis.josh.catan.host.game.actions.messages;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class EventResponses {

    public static JSONObject eventResponse(String event, String players, JSONObject data) {
        JSONObject rtn = new JSONObject();
        rtn.put("event", event);
        rtn.put("players", players);
        rtn.put("data", data);
        return rtn;
    }

    public static JSONObject rolledDice(int numberRolled) {
        String jsonString = """
        "data": {
            "numberRolled": %d
        },
        """;
        JSONObject data = (JSONObject)JSONValue.parse(String.format(jsonString, numberRolled));
        return eventResponse("rolledDice", "all", data);
    }


    public static JSONObject discardHalf() {
        return eventResponse("discardedHalf", "self", new JSONObject());
    }

    public static JSONObject purchaseFailed(JSONObject data) {
        return eventResponse("purchaseFailed", "self", data);
    }

}
