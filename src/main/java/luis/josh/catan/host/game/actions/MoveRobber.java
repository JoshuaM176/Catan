package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.tile.Tile;
import luis.josh.catan.host.game.player.Player;

public class MoveRobber implements Action {

    Board board;

    public MoveRobber(Board board) {
        this.board = board;
    }

    @Override
    public JSONObject execute(JSONObject data, Player player) {
        JSONObject source = (JSONObject)data.get("sourceTile");
        JSONObject target = (JSONObject)data.get("targetTile");
        int sourceRow = (int)(long)source.get("row");
        int sourceColumn = (int)(long)source.get("col");
        int targetRow = (int)(long)target.get("row");
        int targetColumn = (int)(long)target.get("col");

        Tile sourceTile = board.tiles[sourceRow][sourceColumn];
        Tile targetTile = board.tiles[targetRow][targetColumn];
        if(sourceTile.robber == null) {
            return null; //TODO
        }
        if(targetTile.robber != null) {
            return null; //TODO
        }
        targetTile.robber = sourceTile.robber;
        sourceTile.robber = null;
        data.put("event","moveRobber");
        return data;
    }
    
}
