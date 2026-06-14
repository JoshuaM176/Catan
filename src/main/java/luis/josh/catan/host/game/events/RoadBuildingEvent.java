package luis.josh.catan.host.game.events;

import java.util.Map;
import java.util.function.Consumer;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actionmanager.ActionManager;
import luis.josh.catan.host.game.actions.Action;
import luis.josh.catan.host.game.actions.PlaceRoad;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.events.messages.ActionResponses;
import luis.josh.catan.host.game.player.Player;

public class RoadBuildingEvent implements Event{

    private ActionManager actionManager;
    private Consumer<JSONObject> messageQueue;
    private int numRoads;
    private int roadsPlaced = 0;
    private int turn;

    public RoadBuildingEvent(JSONObject data, int turn) {
        numRoads = (int)data.get("numRoads");
        this.turn = turn;
    }

    @Override
    public void initialize(Board board, Player[] players, Consumer<JSONObject> messageQueue) {
        actionManager = new ActionManager(players, new Action[]{new PlaceRoad(board, Map.of())}).setWaitForTurn(() -> turn);
        this.messageQueue = messageQueue;
    }

    @Override
    public JSONObject[] acceptData(JSONObject data) {
        JSONObject[] results = actionManager.executeAction(data);
        String result = (String)results[0].get("event");
        if(result.equals("placedRoad")) {
            roadsPlaced++;
            if(roadsPlaced < numRoads) {
                messageQueue.accept(ActionResponses.actionResponse(
                    "placeRoad",
                    turn,
                    new JSONObject()
                ));
            }
        }
        return results;
    }

    @Override
    public boolean isFinished() {
        return roadsPlaced == numRoads;
    }

    @Override
    public String getName() {
        return "roadBuildingEvent";
    }
    
}
