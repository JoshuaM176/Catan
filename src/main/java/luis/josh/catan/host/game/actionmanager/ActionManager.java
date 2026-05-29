package luis.josh.catan.host.game.actionmanager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.json.simple.JSONObject;
import org.slf4j.Logger;

import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.util.JSONUtil;
import luis.josh.catan.host.HostLogger;
import luis.josh.catan.host.game.actions.Action;
import luis.josh.catan.host.game.actions.messages.EventResponses;

/**
 * Keeps track of available actions and handles executing action from input data.
 * @version 1.0
 */
public class ActionManager {

    private Player[] players;
    private Map<String, Action> actionMap;
    private boolean waitForTurn = false;
    private Supplier<Integer> turn;
    private static final Logger logger = HostLogger.getLogger(ActionManager.class);

    /**
     * @param players The list of players for the current game.
     * @param actionMap Map the action names to corresponding Action object.
     */
    public ActionManager(Player[] players, Map<String, Action> actionMap) {
        this.players = players;
        this.actionMap = new HashMap<>(actionMap);
        this.turn = () -> -1;
    }

    /**
     * @param players The list of players for the current game.
     * @param actions List of Action objects for the current game. Names will
     * automatically be set to className as camelCase.
     */
    public ActionManager(Player[] players, Action[] actions) {
        this.players = players;
        this.actionMap = new HashMap<>();
        for(Action action: actions) {
            addAction(action);
        }
    }

    /**
     * Sets waitForTurn property which prevents actions from executing if the player
     * number does not match the current turn.
     * @param turn Supplier for the current turn.
     * @return Itself
     */
    public ActionManager setWaitForTurn(Supplier<Integer> turn) {
        this.turn = turn;
        waitForTurn = false;
        return this;
    }

    /**
     * Add an action automatically setting name to the action's className as camelCase.
     * @param action Action to execute when the name is used.
     */
    public void addAction(Action action) {
        String name = action.getClass().getSimpleName();
        char firstLetter = name.charAt(0);
        String camelCaseName = String.valueOf(firstLetter).toLowerCase() + name.substring(1);
        addAction(camelCaseName, action);
    }

    /**
     * Adds an action to the map of available actions.
     * @param name Name that will be used to reference the action.
     * @param action Action to execute when that name is used.
     */
    public void addAction(String name, Action action) {
        actionMap.put(name, action);
    }

    /**
     * Executes the action from the given data. Data should be formatted as
     * {"action": "actionName", "player": playerNum, "data": {actionData}}
     * @param data The data containing information about the action to execute.
     * @return Array of EventResponses resulting from the executed action.
     */
    public JSONObject[] executeAction(JSONObject data) {
        int playerNum = (int)data.get("player");
        if(waitForTurn && (playerNum != turn.get().intValue())) {
            return new JSONObject[]{EventResponses.waitForTurn(playerNum)};
        }
        Player player = players[playerNum];
        String actionName = (String)data.get("action");
        logger.info("Executing action {} from player {}", actionName, playerNum);
        Action action = actionMap.get(actionName);
        if(action == null) {
            return new JSONObject[]{EventResponses.unavailableAction(playerNum)};
        }
        JSONObject actionData = (JSONObject)data.get("data");
        JSONObject[] results = action.execute(actionData, player);
        for(JSONObject result: results) {
            replaceSelf(result, playerNum);
        }
        return results;
    }

    /**
     * Replaces "self" in {"players": "self"} with [playerNum].
     * @param jsonObject The json object to replace self in.
     * @param playerNum The playerNum represented by self.
     */
    @SuppressWarnings("unchecked")
    private void replaceSelf(JSONObject jsonObject, int playerNum) {
        System.out.println(jsonObject);
        if("self".equals(jsonObject.get("players"))) {
            jsonObject.put("players", JSONUtil.ArrayToJSON(new Integer[]{playerNum}));
        }
    }

}
