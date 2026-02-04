package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.Tile;
import luis.josh.catan.host.game.player.Player;

public class MoveRobber implements Action {

    Board board;
    Player[] players;

    public MoveRobber(Board board, Player[] players) {
        this.board = board;
        this.players = players;
    }

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        JSONObject source = (JSONObject)data.get("sourceTile");
        JSONObject target = (JSONObject)data.get("targetTile");
        int sourceRow = (int)(long)source.get("row");
        int sourceColumn = (int)(long)source.get("col");
        int targetRow = (int)(long)target.get("row");
        int targetColumn = (int)(long)target.get("col");
        Player targetPlayer = players[(int)(long)data.get("targetPlayer")];

        Tile sourceTile = board.tiles[sourceRow][sourceColumn];
        Tile targetTile = board.tiles[targetRow][targetColumn];
        if(sourceTile == null) { 
            return new JSONObject[0]; //TODO
        }
        if(targetTile == null) {
            return new JSONObject[0]; //TODO
        }
        if(sourceTile.robber == null) {
            return new JSONObject[0]; //TODO
        }
        if(targetTile.robber != null) {
            return new JSONObject[0]; //TODO
        }
        if(!targetTile.hasPlayer(targetPlayer)) {
            return new JSONObject[0]; //TODO
        }
        targetTile.robber = sourceTile.robber;
        sourceTile.robber = null;
        Resource stolenResource = targetPlayer.stealResource();
        if(stolenResource != null) {
            player.addResource(stolenResource);
        }
        data.put("event","movedRobber");
        data.put("players", "all");
        return new JSONObject[]{data};
    }
    
}
