package luis.josh.catan.host.game.gamepieces.cards.developmentcards;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.player.Player;

public class RoadBuilding extends DevelopmentCard{

    private int numRoads;

    public RoadBuilding() {
        numRoads = 2;
    }

    public RoadBuilding(int numRoads) {
        this.numRoads = numRoads;
    }

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        return new JSONObject[]{
            EventResponses.roadBuildingTrigger(numRoads)
        };
    }

    @Override
    public String getName() {
        return "roadBuilding" + numRoads;
    }
    
}
