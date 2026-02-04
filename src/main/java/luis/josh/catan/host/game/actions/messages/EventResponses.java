package luis.josh.catan.host.game.actions.messages;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class EventResponses {
    
    public static JSONObject rolledDice(int numberRolled) {
        String jsonString = """
            {
                "event": "rolledDice",
                "data": {
                    "numberRolled": %d
                },
                "players": "all"
            }    
                    """;
        JSONObject data = (JSONObject)JSONValue.parse(String.format(jsonString, numberRolled));
        return data;
    }



    public static JSONObject discardHalf() {
        String jsonString = """
        {
            "event": "discardedHalf",
            "data": {},
            "players": "self"
        }
                """;
        JSONObject data = (JSONObject)JSONValue.parse(jsonString);
        return data;
    }
}
