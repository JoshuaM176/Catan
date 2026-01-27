package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.tile.Tile;
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
        int column = (int)(long)location.get("column");
        int vertex = (int)(long)location.get("vertex");
        if(!isValidPlacement(row, column, vertex)) {
            return null; //TODO
        }

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
    private boolean isValidPlacement(int row, int column, int vertex) {
        Tile tile = board.tiles[row][column];
        if(tile.vertices[vertex].placedItem != null) {
            return false;
        }
        int offset = (row % 2 == 0) ? 0 : 1;
        if(vertex == 0) {
            if(!checkTileVertices(row - 1, column - 1 + offset, 2)) { return false; }
            if(!checkTileVertices(row - 1, column, 4)) { return false; }
        }
        if(vertex == 1) {
            if(!checkTileVertices(row -1, column + offset, 3)) { return false; }
            if(!checkTileVertices(row, column + 1, 5)) { return false; }
        }
        if(vertex == 2) {
            if(!checkTileVertices(row + 1, column + offset, 0)) { return false; }
            if(!checkTileVertices(row, column + 1, 4)) { return false; }
        }
        if(vertex == 3) {
            if(!checkTileVertices(row + 1, column - 1 + offset, 1)) { return false; }
            if(!checkTileVertices(row + 1, column + offset, 5)) { return false; }
        }
        if(vertex == 4) {
            if(!checkTileVertices(row + 1, column - 1 + offset, 0)) { return false; }
            if(!checkTileVertices(row, column - 1, 2)) { return false; }
        }
        if(vertex == 5) {
            if(!checkTileVertices(row, column - 1, 1)) { return false; }
            if(!checkTileVertices(row - 1, column - 1 + offset, 3)) { return false; }
        }
        if(!checkTileVertices(row, column, vertex)) { return false; }
        return true;
    }

    private boolean checkTileVertices(int row, int col, int vertex) {
        if(row < 0 || row >= board.tiles.length || col < 0 || col > board.tiles[0].length) {
            return true;
        }
        Tile tile = board.tiles[row][col];
        if(tile == null) { return true; }
        if(vertex == 0) {
            if(tile.vertices[1].placedItem != null) { return false; }
            if(tile.vertices[5].placedItem != null) { return false; }
            return true;
        }
        if(vertex == 5) {
            if(tile.vertices[0].placedItem != null) { return false; }
            if(tile.vertices[4].placedItem != null) { return false;}
            return true;
        }
        if(tile.vertices[vertex-1].placedItem != null) { return false; }
        if(tile.vertices[vertex+1].placedItem != null) { return false; }
        return true;
    }

}
