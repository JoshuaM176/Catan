package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.Tile;
import luis.josh.catan.host.game.gamepieces.Settlement;
import luis.josh.catan.host.game.player.Player;
import java.util.Map;

// start, 1 for first settlement, 2 for second settlement, 0 for regular placement

public class PlaceSettlement implements Action{

    private Board board;
    private Map<Resource, Integer> resourceCost;

    public PlaceSettlement(Board board, Map<Resource, Integer> resourceCost) {
        this.board = board;
        this.resourceCost = resourceCost;
    }

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        int start = (int)(long)data.get("start");
        JSONObject location = (JSONObject)data.get("tile");
        int row = (int)(long)location.get("row");
        int col = (int)(long)location.get("col");
        int vertex = (int)(long)location.get("vertex");

        Tile tile = board.tiles[row][col];
        if(tile == null) {
            return new JSONObject[0]; //TODO
        }
        if(!board.isValidPlacement(row, col, vertex)) {
            return new JSONObject[0]; //TODO
        }
        if(start == 0) {
            if(!tile.vertices[vertex].isConnected(player)) {
                return new JSONObject[0]; // TODO
            }
            if(!player.checkAndPurchase(resourceCost)) {
                return new JSONObject[0]; // TODO
            }
        }
        if(start == 2) {
            Tile[] tiles = board.getNeighborTiles(row, col, vertex);
            for(Tile rsrcTile : tiles) {
                if(rsrcTile != null) {
                    rsrcTile.addResource(player);
                }
            }
        }
        tile.vertices[vertex].setPlacedItem(new Settlement(player));

        data.put("event", "placedSettlement");
        data.put("players", "all");
        return new JSONObject[]{data};
    }
}