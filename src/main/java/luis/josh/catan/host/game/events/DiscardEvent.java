package luis.josh.catan.host.game.events;

import java.util.Map;
import java.util.function.Consumer;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actionmanager.ActionManager;
import luis.josh.catan.host.game.actions.Action;
import luis.josh.catan.host.game.actions.Discard;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.events.messages.ActionResponses;
import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.util.JSONUtil;

public class DiscardEvent implements Event {

    private ActionManager actionManager;
    private boolean[] finished;
    private int cardLimit;
    private double discardPercent;

    public DiscardEvent(JSONObject data) {
        JSONObject eventData = (JSONObject)data.get("data");
        cardLimit = (int)eventData.get("cardLimit");
        discardPercent = (double)eventData.get("discardPercent");
    }

    @Override
    public void initialize(Board board, Player[] players, Consumer<JSONObject> messageQueue) {
        actionManager = new ActionManager(players, new Action[]{new Discard()});
        finished = new boolean[players.length];
        for(int i = 0; i < players.length; i++) {
            Player player = players[i];
            if(player.totalResources() <= cardLimit) {
                finished[i] = true;
                continue;
            }
            finished[i] = false;
            messageQueue.accept(ActionResponses.actionResponse(
                "discard",
                JSONUtil.ArrayToJSON(new Integer[]{i}),
                new JSONObject(Map.of("numCards", (int)Math.floor(player.totalResources()*discardPercent)))
            ));

        }
    }

    @Override
    public JSONObject[] acceptData(JSONObject data) {
        JSONObject[] results = actionManager.executeAction(data);
        int playerNum = (int)data.get("player");
        if(results.length > 0) {
            JSONObject result = results[0];
            String eventName = (String)result.get("event");
            if(eventName.equals("discarded")) {
                finished[playerNum] = true;
            }
        }
        return results;
    }

    @Override
    public boolean isFinished() {
        for(boolean playerFinished: finished) {
            if(!playerFinished) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "discardEvent";
    }


}
