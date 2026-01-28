package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.tile.Tile;
import luis.josh.catan.host.game.gamepieces.Road;
import luis.josh.catan.host.game.player.Player;

// start, 1 for first settlement, 2 for second settlement, 0 for regular placement

public class PlaceRoad implements Action{

    Board board;

    public PlaceRoad(Board board) {
        this.board = board;
    }

    @Override
    public JSONObject execute(JSONObject data, Player player) {
        int start = (int)(long)data.get("start");
        JSONObject location = (JSONObject)data.get("tile");
        int row = (int)(long)location.get("row");
        int col = (int)(long)location.get("col");
        int edge = (int)(long)location.get("edge");
        Tile tile = board.tiles[row][col];
        if(tile == null) {
            return null; // TODO
        }
        if(tile.edges[edge].placedItem != null) { return null; } // TODO
        if(start == 0) {
            if(edge == 5) {
                if(!tile.vertices[5].isConnected(player) && !tile.vertices[0].isConnected(player)) {
                    return null; // TODO
                }
            }
            else if(!tile.vertices[edge].isConnected(player) && !tile.vertices[edge+1].isConnected(player)) {
                return null; // TODO
            }
        }
        if(start == 1 || start == 2) {
            if(edge == 5) {
                if(tile.vertices[5].isConnected() && tile.vertices[5].placedItem != null) {
                    return null; // TODO
                }
                if(tile.vertices[0].isConnected() && tile.vertices[0].placedItem != null) {
                    return null; // TODO
                }
                if(!tile.vertices[5].hasPlacedItem(player) && !tile.vertices[0].hasPlacedItem(player)) {
                    return null; // TODO
                }
            }
            else {
                if(tile.vertices[edge].isConnected() && tile.vertices[edge].placedItem != null) {
                    return null; // TODO
                }
                if(tile.vertices[edge+1].isConnected() && tile.vertices[edge+1].placedItem != null) {
                    return null; // TODO
                }
                if(!tile.vertices[edge].hasPlacedItem(player) && !tile.vertices[edge+1].hasPlacedItem(player)) {
                    return null; // TODO
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
        data.put("event","placeRoad");
        return data;
    }
}
