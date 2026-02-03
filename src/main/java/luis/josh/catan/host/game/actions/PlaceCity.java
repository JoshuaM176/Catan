package luis.josh.catan.host.game.actions;

import java.util.Map;

import org.json.simple.JSONObject;

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
    public JSONObject execute(JSONObject data, Player player) {
        JSONObject location = (JSONObject)data.get("tile");
        int row = (int)(long)location.get("row");
        int col = (int)(long)location.get("col");
        int vertex = (int)(long)location.get("vertex");

        Tile tile = board.tiles[row][col];
        if(tile == null) {
            return null; //TODO
        }
        VertexPlaceable placedItem = tile.vertices[vertex].placedItem;
        if(placedItem == null) {
            return null; // TODO
        }
        if(player != placedItem.getPlayer()) {
            return null; // TODO
        }
        if(!(placedItem instanceof Settlement)) {
            return null; // TODO
        }
        if(!player.checkAndPurchase(resourceCost)) {
            return null; // TODO
        }
        tile.vertices[vertex].setPlacedItem(new City(player));
        data.put("event","placeCity");
        return data;
    }
    
}
