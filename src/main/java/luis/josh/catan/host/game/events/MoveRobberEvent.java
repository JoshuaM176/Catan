package luis.josh.catan.host.game.events;

import luis.josh.catan.host.game.board.Board;

import java.util.Map;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actionmanager.ActionManager;
import luis.josh.catan.host.game.actions.MoveRobber;
import luis.josh.catan.host.game.player.Player;

public class MoveRobberEvent implements Event{

    private ActionManager actionManager;
    private boolean finished = false;

    public void initialize(Board board, Player[] players) {
        actionManager = new ActionManager(players, Map.of("moveRobber", new MoveRobber(board, players)));
    }

    public JSONObject[] acceptData(JSONObject data) {
        JSONObject[] results = actionManager.executeAction(data);
        if(results.length > 0) {
            JSONObject result = results[0];
            String eventName = (String)result.get("event");
            if(eventName.equals("movedRobber")) {
                finished = true;
            }
        }
        return results;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getName() {
        return "moveRobberEvent";
    }
}
