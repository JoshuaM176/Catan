package luis.josh.catan.host.game.actions;

import java.util.Map;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.VertexPlaceable;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.Tile;
import luis.josh.catan.host.game.gamepieces.City;
import luis.josh.catan.host.game.gamepieces.Settlement;
import luis.josh.catan.host.game.player.Player;

public class PlaceCity implements Action{

    private Board board;
    private Map<Resource, Integer> resourceCost;

    public PlaceCity(Board board, Map<Resource, Integer> resourceCost) {
        this.board = board;
        this.resourceCost = resourceCost;
    }

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        JSONObject location = (JSONObject)data.get("tile");
        int row = (int)location.get("row");
        int col = (int)location.get("col");
        int vertex = (int)location.get("vertex");

        Tile tile = board.tiles[row][col];
        if(tile == null) {
            JSONObject message = new JSONObject(
                Map.of("message", "Cannot build in the ocean.")
            );
            return new JSONObject[]{EventResponses.eventResponse("placeCityFailed", "self", message, 400)};
        }
        VertexPlaceable placedItem = tile.vertices[vertex].placedItem;
        if(placedItem == null) {
            JSONObject message = new JSONObject(
                Map.of("message", "City requires settlement to build on.")
            );
            return new JSONObject[]{EventResponses.eventResponse("placeCityFailed", "self", message, 400)};
        }
        if(player != placedItem.getPlayer()) {
            JSONObject message = new JSONObject(
                Map.of("message", "Cannot build on another player's property.")
            );
            return new JSONObject[]{EventResponses.eventResponse("placeCityFailed", "self", message, 400)};
        }
        if(!(placedItem instanceof Settlement)) {
            JSONObject message = new JSONObject(
                Map.of("message", "City requires settlement to build on.")
            );
            return new JSONObject[]{EventResponses.eventResponse("placeCityFailed", "self", message, 400)};
        }
        if(!player.checkAndPurchase(resourceCost)) {
            return new JSONObject[]{EventResponses.genericPurchaseFailed("City")};
        }
        tile.vertices[vertex].setPlacedItem(new City(player));
        player.addVictoryPoint();
        Action.addPlayer(data, player.playerNum());
        return new JSONObject[]{
            EventResponses.eventResponse(
               "placedCity",
               "all",
               data
            )
        };
    }

}
