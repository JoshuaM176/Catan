package luis.josh.catan.host.game.actionmanager;

import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;

import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.host.HostLogger;
import luis.josh.catan.host.game.actions.Action;

public class ActionManager {

    private Player[] players;
    private Map<String, Action> actionMap;
    private static final Logger logger = HostLogger.getLogger(ActionManager.class);

    public ActionManager(Player[] players, Map<String, Action> actionMap) {
        this.players = players;
        this.actionMap = actionMap;
    }

    public JSONObject[] executeAction(JSONObject data) {
        int playerNum = (int)data.get("player");
        Player player = players[playerNum];
        String actionName = (String)data.get("action");
        logger.info("Executing action {} from player {}", actionName, playerNum);
        Action action = actionMap.get(actionName);
        if(action == null) {
            logger.info("Failed to execute unknown action {}.", actionName);
            return new JSONObject[0];
        }
        JSONObject actionData = (JSONObject)data.get("data");
        return action.execute(actionData, player);
    }

}
