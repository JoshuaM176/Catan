package luis.josh.catan.host.game.gamepieces.cards.developmentcards;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.util.JSONUtil;

public class YearOfPlenty extends DevelopmentCard{

    private int numResources;

    public YearOfPlenty() {
        numResources = 2;
    }

    public YearOfPlenty(int numResources) {
        this.numResources = numResources;
    }

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        JSONArray resources = (JSONArray)data.get("resources");
        Map<Resource, Integer> resourceMap = JSONUtil.resourceMapFromJSON(resources);
        int total = 0;
        for(Integer count : resourceMap.values()) {
            total += count;
        }
        if(total != this.numResources) {
            return new JSONObject[] {EventResponses.eventResponse(
                "developmentCard",
                "self",
                new JSONObject(Map.of(
                "message", "Incorrect number of resources chosen.")),
                400
            )};
        }
        for(Resource resource : resourceMap.keySet()) {
            player.addResources(resource, resourceMap.get(resource));
        }
        return new JSONObject[]{};
    }

    @Override
    public String getName() {
        return "yearOfPlenty" + numResources;
    }

}
