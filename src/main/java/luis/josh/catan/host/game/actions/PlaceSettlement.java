package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
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
        int start = (int)data.get("start");
        JSONObject location = (JSONObject)data.get("tile");
        int row = (int)location.get("row");
        int col = (int)location.get("col");
        int vertex = (int)location.get("vertex");

        Tile tile = board.tiles[row][col];
        if(tile == null) {
            JSONObject message = new JSONObject(
                Map.of("message", "Cannot build in the ocean.")
            );
            return new JSONObject[]{EventResponses.eventResponse("placeSettlementFailed", "self", message)};
        }
        if(!board.isValidPlacement(row, col, vertex)) {
            JSONObject message = new JSONObject(
                Map.of("message", "Cannot place within 1 distance of an existing settlement.")
            );
            return new JSONObject[]{EventResponses.eventResponse("placeSettlementFailed", "self", message)};
        }
        if(start == 0) {
            if(!tile.vertices[vertex].isConnected(player)) {
                JSONObject message = new JSONObject(
                    Map.of("message", "No roads connected.")
                );
                return new JSONObject[]{EventResponses.eventResponse("placeSettlementFailed", "self", message)};
            }
            if(!player.checkAndPurchase(resourceCost)) {
                return new JSONObject[]{EventResponses.genericPurchaseFailed("Settlement")};
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
        Action.addPlayer(data, player.playerNum());
        return new JSONObject[]{
            EventResponses.eventResponse(
                "placedSettlement",
                "all",
                data
            )
        };
    }
}