package luis.josh.catan.host.game.actions.messages;

import java.util.Map;

import org.json.simple.JSONObject;

public class EventResponses {

    public static JSONObject eventResponse(String event, String players, JSONObject data) {
        JSONObject rtn = new JSONObject(
            Map.of(
                "event", event,
                "players", players,
                "data", data
            )
        );
        return rtn;
    }

    public static JSONObject rolledDice(int numberRolled) {
        JSONObject data = new JSONObject(
            Map.of(
                "data", Map.of("numberRolled", numberRolled)
            )
        );
        return eventResponse("rolledDice", "all", data);
    }


    public static JSONObject discardHalf() {
        return eventResponse("discardedHalf", "self", new JSONObject());
    }

    // Trigger Events
    public static JSONObject moveRobberTrigger() {
        return eventResponse(
            "moveRobber",
            "self",
            new JSONObject()
        );
    }

    public static JSONObject discardHalfTrigger() {
        return eventResponse(
            "discardHalf",
            "all",
            new JSONObject()
        );
    }

    // Error Messages
    public static JSONObject purchaseFailed(JSONObject data) {
        return eventResponse("purchaseFailed", "self", data);
    }

    public static JSONObject genericPurchaseFailed(String item) {
        JSONObject data = new JSONObject(
            Map.of("message","Not enough resources for " + item + ".")
        );
        return purchaseFailed(data);
    }

    public static JSONObject moveRobberFailed() {
        JSONObject data = new JSONObject(
            Map.of("message", "Invalid location.")
        );
        return eventResponse("moveRobberFailed", "self", data);
    }

    public static JSONObject roadConnectionFailure() {
        JSONObject data = new JSONObject(
            Map.of(
                "message", "Road must be connected to existing roads."
            )
        );
        return eventResponse("placeRoadFailed", "self", data);
    }

    public static JSONObject firstRoadConnectionFailure() {
        JSONObject data = new JSONObject(
            Map.of(
                "message", "Road must be connected to the new settlement."
            )
        );
        return eventResponse("placeRoadFailed", "self", data);
    }
}
