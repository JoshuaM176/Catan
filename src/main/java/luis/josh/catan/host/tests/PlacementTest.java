package luis.josh.catan.host.tests;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import luis.josh.catan.host.game.actions.PlaceRoad;
import luis.josh.catan.host.game.actions.PlaceSettlement;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.player.Player;

public class PlacementTest {

    public static void main(String[] args) {
        Board board = GenerateTestBoard.generateTestBoard();
        Player player1 = new Player();
        Player player2 = new Player();
        PlaceSettlement placeSettlement = new PlaceSettlement(board);
        PlaceRoad placeRoad = new PlaceRoad(board);
        JSONObject data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 0,
                    "col": 0,
                    "vertex": 1
                },
                "start": 1
            }
                """
        );
        System.out.println(placeSettlement.execute(data, player1));
        data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 1,
                    "col": 0,
                    "edge": 5
                },
                "start": 1
            }
                """
        );
        System.out.println(placeRoad.execute(data, player1));
        data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 2,
                    "col": 0,
                    "vertex": 0
                },
                "start": 1
            }
                """
        );
        System.out.println(placeSettlement.execute(data, player2));
        data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 1,
                    "col": 0,
                    "edge": 4
                },
                "start": 1
            }
                """
        );
        System.out.println(placeRoad.execute(data, player2));
    }

}
