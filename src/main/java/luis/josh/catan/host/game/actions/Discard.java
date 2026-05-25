package luis.josh.catan.host.game.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.player.Player;

public class Discard implements Action{

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        List<JSONObject> resources = (JSONArray)data.get("resources");
        Map<Resource, Integer> discardedResources = new HashMap<>();
        for(JSONObject resource_map: resources) {
            Resource resource = Resource.valueOf((String)resource_map.get("resource"));
            int count = (int)resource_map.get("count");
            discardedResources.put(resource, count);
        }
        if(!player.checkAndPurchase(discardedResources)) {
            JSONObject message = (JSONObject)JSONValue.parse("""
                    {"message": "Failed to discard, not enough resources."}
                    """);
            return new JSONObject[]{EventResponses.purchaseFailed(message)};
        }
        return new JSONObject[]{EventResponses.discardHalf()};
    }
    
}
