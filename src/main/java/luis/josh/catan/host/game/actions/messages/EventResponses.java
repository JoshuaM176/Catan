package luis.josh.catan.host.game.actions.messages;

import java.util.Map;

import org.json.simple.JSONObject;

import luis.josh.catan.util.JSONUtil;

public class EventResponses {

    public static JSONObject eventResponse(String event, int playerNum, JSONObject data) {
        return eventResponse(event, JSONUtil.ArrayToJSON(new Integer[]{playerNum}), data, 200);
    }

    public static JSONObject eventResponse(String event, int playerNum, JSONObject data, int code) {
        return eventResponse(event, JSONUtil.ArrayToJSON(new Integer[]{playerNum}), data, code);
    }

    public static JSONObject eventResponse(String event, Object players, JSONObject data) {
        return eventResponse(event, players, data, 200);
    }

    public static JSONObject eventResponse(String event, Object players, JSONObject data, int code) {
        JSONObject rtn = new JSONObject(
            Map.of(
                "event", event,
                "players", players,
                "data", data,
                "code", code
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

    public static JSONObject usedDevelopmentCard(String card) {
        return eventResponse("usedDevelopmentCard", "self", new JSONObject(Map.of("card", card)));
    }

    // Trigger Events
    public static JSONObject moveRobberTrigger() {
        return eventResponse(
            "moveRobberTrigger",
            "none",
            new JSONObject()
        );
    }

    public static JSONObject roadBuildingTrigger(int numRoads) {
        return eventResponse(
            "roadBuildingTrigger",
            "none",
            new JSONObject(Map.of("numRoads", numRoads))
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
        return eventResponse("purchaseFailed", "self", data, 400);
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
        return eventResponse("moveRobberFailed", "self", data, 400);
    }

    public static JSONObject roadConnectionFailure() {
        JSONObject data = new JSONObject(
            Map.of(
                "message", "Road must be connected to existing roads."
            )
        );
        return eventResponse("placeRoadFailed", "self", data, 400);
    }

    public static JSONObject firstRoadConnectionFailure() {
        JSONObject data = new JSONObject(
            Map.of(
                "message", "Road must be connected to the new settlement."
            )
        );
        return eventResponse("placeRoadFailed", "self", data, 400);
    }

    public static JSONObject waitForTurn(int player) {
        JSONObject data = new JSONObject(
            Map.of(
                "message", "Please wait for your turn."
            )
        );
        return eventResponse("waitForTurn", player, data, 400);
    }

    public static JSONObject unavailableAction() {
        JSONObject data = new JSONObject(
            Map.of(
                "message", "Action unavailable."
            )
        );
        return eventResponse("actionUnavailable", "self", data, 400);
    }

    public static JSONObject unavailableAction(int playerNum) {
        JSONObject data = new JSONObject(
            Map.of(
                "message", "Action unavailable."
            )
        );
        return eventResponse("actionUnavailable", playerNum, data, 400);
    }
}
