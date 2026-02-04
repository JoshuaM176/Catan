package luis.josh.catan.host.tests;

import java.util.Arrays;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import luis.josh.catan.host.game.actions.PlaceCity;
import luis.josh.catan.host.game.actions.PlaceRoad;
import luis.josh.catan.host.game.actions.PlaceSettlement;
import luis.josh.catan.host.game.actions.RollDice;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.player.Player;

public class PlacementTest {

    public static void main(String[] args) {
        Board board = GenerateTestBoard.generateTestBoard();
        Player player1 = new Player();
        Player player2 = new Player();
        PlaceSettlement placeSettlement = new PlaceSettlement(board, Map.of(Resource.LOGS, 1));
        PlaceRoad placeRoad = new PlaceRoad(board, Map.of(Resource.LOGS, 1));
        PlaceCity placeCity = new PlaceCity(board, Map.of(Resource.STONE, 1));
        RollDice rollDice = new RollDice(board);
        JSONObject data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 1,
                    "col": 1,
                    "vertex": 1
                },
                "start": 1
            }
                """
        );
        System.out.println(Arrays.toString(placeSettlement.execute(data, player1)));
        data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 1,
                    "col": 1,
                    "edge": 1
                },
                "start": 1
            }
                """
        );
        System.out.println(Arrays.toString(placeRoad.execute(data, player1)));
        data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 2,
                    "col": 4,
                    "vertex": 5
                },
                "start": 2
            }
                """
        );
        System.out.println(Arrays.toString(placeSettlement.execute(data, player1)));
        data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 2,
                    "col": 4,
                    "edge": 5
                },
                "start": 2
            }
                """
        );
        System.out.println(Arrays.toString(placeRoad.execute(data, player1)));
        data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 2,
                    "col": 4,
                    "edge": 0
                },
                "start": 0
            }
                """
        );
        System.out.println(player1);
        System.out.println(Arrays.toString(placeRoad.execute(data, player1)));
        for(int i = 0; i < 5; i++) {
            System.out.println(Arrays.toString(rollDice.execute(null, player1)));
        }
        data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 2,
                    "col": 4,
                    "vertex": 1
                },
                "start": 0
            }
                """
        );
        System.out.println(Arrays.toString(placeSettlement.execute(data, player1)));
        System.out.println(player1);
        data = (JSONObject)JSONValue.parse(
                """
            {
                "tile": {
                    "row": 2,
                    "col": 4,
                    "vertex": 1
                }
            }
                """
        );
        System.out.println(Arrays.toString(placeCity.execute(data, player1)));
        System.out.println(player1);
        System.out.println(board);
        System.out.println(player1);
    }

}
