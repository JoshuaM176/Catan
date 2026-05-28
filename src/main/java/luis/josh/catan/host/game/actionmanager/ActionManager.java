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

public class ActionManager {

    private Player[] players;
    private Map<String, Action> actionMap;
    private boolean waitForTurn = false;
    private Supplier<Integer> turn;
    private static final Logger logger = HostLogger.getLogger(ActionManager.class);

    public ActionManager(Player[] players, Map<String, Action> actionMap) {
        this.players = players;
        this.actionMap = new HashMap<>(actionMap);
        this.turn = () -> -1;
    }

    public ActionManager(Player[] players, Action[] actions) {
        this.players = players;
        for(Action action: actions) {
            addAction(action);
        }
    }

    public ActionManager setWaitForTurn(Supplier<Integer> turn) {
        this.turn = turn;
        waitForTurn = false;
        return this;
    }

    public void addAction(Action action) {
        String name = action.getClass().getSimpleName();
        char firstLetter = name.charAt(0);
        String camelCaseName = String.valueOf(firstLetter).toLowerCase() + name.substring(1);
        addAction(camelCaseName, action);
    }

    public void addAction(String name, Action action) {
        actionMap.put(name, action);
    }

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

    @SuppressWarnings("unchecked")
    private void replaceSelf(JSONObject jsonObject, int playerNum) {
        if(((String)jsonObject.get("players")).equals("self")) {
            jsonObject.put("players", JSONUtil.ArrayToJSON(new Integer[]{playerNum}));
        }
    }

}
