package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.tile.Tile;
import luis.josh.catan.host.game.gamepieces.Settlement;
import luis.josh.catan.host.game.player.Player;

// start, 1 for first settlement, 2 for second settlement, 0 for regular placement

public class PlaceSettlement implements Action{

    Board board;

    public PlaceSettlement(Board board) {
        this.board = board;
    }

    @Override
    public JSONObject execute(JSONObject data, Player player) {
        int start = (int)(long)data.get("start");
        JSONObject location = (JSONObject)data.get("tile");
        int row = (int)(long)location.get("row");
        int col = (int)(long)location.get("col");
        int vertex = (int)(long)location.get("vertex");
        Tile tile = board.tiles[row][col];
        if(tile == null) {
            return null; //TODO
        }
        if(!board.isValidPlacement(row, col, vertex)) {
            return null; //TODO
        }
        if(start == 0) {
            if(!tile.vertices[vertex].isConnected(player)) {
                return null; // TODO
            }
        }
        tile.vertices[vertex].setPlacedItem(new Settlement(player));
        data.put("event", "placeSettlement");
        return data;
    }
}