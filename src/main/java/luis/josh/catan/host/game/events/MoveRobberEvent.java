package luis.josh.catan.host.game.events;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.events.messages.ActionResponses;

import java.util.Map;
import java.util.function.Consumer;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actionmanager.ActionManager;
import luis.josh.catan.host.game.actions.MoveRobber;
import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.util.JSONUtil;

public class MoveRobberEvent implements Event{

    private ActionManager actionManager;
    private boolean finished = false;
    private int turn;

    public MoveRobberEvent(JSONObject data) {
        turn = (int)data.get("turn");
    }

    public void initialize(Board board, Player[] players, Consumer<JSONObject> messageQueue) {
        actionManager = new ActionManager(players, Map.of("moveRobber", new MoveRobber(board, players))).setWaitForTurn(() -> turn);
        messageQueue.accept(ActionResponses.actionResponse(
            "moveRobber",
            JSONUtil.ArrayToJSON(new Integer[]{turn}),
            new JSONObject()
            ));
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
