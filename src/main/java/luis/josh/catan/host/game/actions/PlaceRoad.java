package luis.josh.catan.host.game.actions;

import java.util.Map;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.Tile;
import luis.josh.catan.host.game.gamepieces.Road;
import luis.josh.catan.host.game.player.Player;

// start, 1 for first settlement, 2 for second settlement, 0 for regular placement

public class PlaceRoad implements Action{

    private Board board;
    private Map<Resource, Integer> resourceCost;

    public PlaceRoad(Board board, Map<Resource, Integer> resourceCost) {
        this.board = board;
        this.resourceCost = resourceCost;
    }

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        int start = (int)data.get("start");
        JSONObject location = (JSONObject)data.get("tile");
        int row = (int)location.get("row");
        int col = (int)location.get("col");
        int edge = (int)location.get("edge");

        Tile tile = board.tiles[row][col];
        if(tile == null) {
            JSONObject message = new JSONObject(
                Map.of("message", "Cannot build in the ocean.")
            );
            return new JSONObject[]{EventResponses.eventResponse("placeRoadFailed", "self", message)};
        }
        if(tile.edges[edge].placedItem != null) {
            JSONObject message = new JSONObject(
                Map.of("message", "Road already exists here.")
            );
            return new JSONObject[]{EventResponses.eventResponse("placeRoadFailed", "self", message)};
        }
        if(start == 0) {
            if(edge == 5) {
                if(!tile.vertices[5].isConnected(player) && !tile.vertices[0].isConnected(player)) {
                    return new JSONObject[]{EventResponses.roadConnectionFailure()};
                }
            }
            else if(!tile.vertices[edge].isConnected(player) && !tile.vertices[edge+1].isConnected(player)) {
                return new JSONObject[]{EventResponses.roadConnectionFailure()};
            }
            if(!player.checkAndPurchase(resourceCost)) {
                return new JSONObject[]{EventResponses.genericPurchaseFailed("Road")};
            }
        }
        if(start == 1 || start == 2) {
            if(edge == 5) {
                if(tile.vertices[5].isConnected() && tile.vertices[5].placedItem != null) {
                    return new JSONObject[]{EventResponses.firstRoadConnectionFailure()};
                }
                if(tile.vertices[0].isConnected() && tile.vertices[0].placedItem != null) {
                    return new JSONObject[]{EventResponses.firstRoadConnectionFailure()};
                }
                if(!tile.vertices[5].hasPlacedItem(player) && !tile.vertices[0].hasPlacedItem(player)) {
                    return new JSONObject[]{EventResponses.firstRoadConnectionFailure()};
                }
            }
            else {
                if(tile.vertices[edge].isConnected() && tile.vertices[edge].placedItem != null) {
                    return new JSONObject[]{EventResponses.firstRoadConnectionFailure()};
                }
                if(tile.vertices[edge+1].isConnected() && tile.vertices[edge+1].placedItem != null) {
                    return new JSONObject[]{EventResponses.firstRoadConnectionFailure()};
                }
                if(!tile.vertices[edge].hasPlacedItem(player) && !tile.vertices[edge+1].hasPlacedItem(player)) {
                    return new JSONObject[]{EventResponses.firstRoadConnectionFailure()};
                }
            }
        }
        tile.edges[edge].setPlacedItem(new Road(player));
        if(edge == 5) {
            tile.vertices[5].addConnection(player);
            tile.vertices[0].addConnection(player);
        }
        else {
            tile.vertices[edge].addConnection(player);
            tile.vertices[edge+1].addConnection(player);
        }
        Action.addPlayer(data, player.playerNum());
        return new JSONObject[]{
            EventResponses.eventResponse(
               "placedRoad",
               "all",
               data
            )
        };
    }
}
