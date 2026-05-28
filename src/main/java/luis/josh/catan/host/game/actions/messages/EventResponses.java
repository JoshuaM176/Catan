package luis.josh.catan.host.game.actions.messages;

import java.util.Map;

import org.json.simple.JSONObject;

import luis.josh.catan.util.JSONUtil;

public class EventResponses {

    public static JSONObject eventResponse(String event, Object players, JSONObject data) {
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


    public static JSONObject discard(JSONObject data) {
        return eventResponse("discarded", "self", data);
    }

    // Trigger Events
    public static JSONObject moveRobberTrigger() {
        return eventResponse(
            "moveRobberTrigger",
            "none",
            new JSONObject()
        );
    }

    public static JSONObject discardTrigger(int cardLimit, double discardPercent) {
        JSONObject data = new JSONObject(Map.of(
            "cardLimit", cardLimit,
            "discardPercent", discardPercent
        ));
        return eventResponse(
            "discardTrigger",
            "none",
            data
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

    public static JSONObject waitForTurn(int player) {
        JSONObject data = new JSONObject(
            Map.of(
                "message", "Please wait for your turn."
            )
        );
        return eventResponse("waitForTurn", JSONUtil.ArrayToJSON(new Integer[]{player}), data);
    }

    public static JSONObject unavailableAction() {
        JSONObject data = new JSONObject(
            Map.of(
                "message", "Action unavailable."
            )
        );
        return eventResponse("actionUnavailable", "self", data);
    }

    public static JSONObject unavailableAction(int playerNum) {
        JSONObject data = new JSONObject(
            Map.of(
                "message", "Action unavailable."
            )
        );
        return eventResponse("actionUnavailable", JSONUtil.ArrayToJSON(new Integer[]{playerNum}), data);
    }
}
