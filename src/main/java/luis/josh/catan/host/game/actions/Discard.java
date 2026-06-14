package luis.josh.catan.host.game.actions;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.util.JSONUtil;

public class Discard implements Action{

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        JSONArray resources = (JSONArray)data.get("resources");
        Map<Resource, Integer> discardedResources = JSONUtil.resourceMapFromJSON(resources);
        if(!player.checkAndPurchase(discardedResources)) {
            JSONObject message = new JSONObject(
                Map.of("message", "Failed to discard, not enough resources.")
            );
            return new JSONObject[]{EventResponses.purchaseFailed(message)};
        }
        return new JSONObject[]{EventResponses.discard(data)};
    }
    
}
