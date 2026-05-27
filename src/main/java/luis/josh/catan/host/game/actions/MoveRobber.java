package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
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
        int sourceRow = (int)source.get("row");
        int sourceColumn = (int)source.get("col");
        int targetRow = (int)target.get("row");
        int targetColumn = (int)target.get("col");
        Player targetPlayer = players[(int)data.get("targetPlayer")];

        Tile sourceTile = board.tiles[sourceRow][sourceColumn];
        Tile targetTile = board.tiles[targetRow][targetColumn];
        if(sourceTile == null) { 
            return new JSONObject[]{EventResponses.moveRobberFailed()};
        }
        if(targetTile == null) {
            return new JSONObject[]{EventResponses.moveRobberFailed()};
        }
        if(sourceTile.robber == null) {
            return new JSONObject[]{EventResponses.moveRobberFailed()};
        }
        if(targetTile.robber != null) {
            return new JSONObject[]{EventResponses.moveRobberFailed()};
        }
        if(!targetTile.hasPlayer(targetPlayer) && targetPlayer != null) {
            return new JSONObject[]{EventResponses.moveRobberFailed()};
        }
        targetTile.robber = sourceTile.robber;
        sourceTile.robber = null;
        Resource stolenResource = null;
        if(targetPlayer != null) {
            stolenResource = targetPlayer.stealResource();
        }
        if(stolenResource != null) {
            player.addResource(stolenResource);
        }
        return new JSONObject[]{
            EventResponses.eventResponse(
               "movedRobber",
               "all",
               data
            )
        };
    }
}
