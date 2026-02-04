package luis.josh.catan.host.tests;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.Edge;
import luis.josh.catan.host.game.board.Vertex;
import luis.josh.catan.host.game.gamepieces.Robber;
import luis.josh.catan.host.game.gamepieces.Settlement;
import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.host.game.actions.MoveRobber;
import luis.josh.catan.host.game.actions.RollDice;

import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class PipelineTest {
   
    public static void main(String[] args) {
        Board board = GenerateTestBoard.generateTestBoard();
        System.out.println(board);
        Player player = new Player();
        Settlement settlement = new Settlement(player);
        board.tiles[0][3].vertices[1].setPlacedItem(settlement);

        RollDice rollDice = new RollDice(board);
        JSONObject jsonObject = new JSONObject();
        for(int i = 0; i < 20; i++) {
            System.out.println(Arrays.toString(rollDice.execute(jsonObject, player)));
        }
        System.out.println(player);

        for(Edge edge : board.tiles[0][1].edges) {
            System.out.println(edge.coast);
        }
        System.out.println();
        for(Vertex vertex : board.tiles[0][1].vertices) {
            System.out.println(vertex.harbor);
        }
        System.out.println();
        for(Vertex vertex : board.tiles[1][0].vertices) {
            System.out.println(vertex.harbor);
        }

        System.out.println(player.harbors);

        board.tiles[0][1].robber = new Robber();
        MoveRobber moveRobber = new MoveRobber(board, new Player[]{player});
        JSONObject test = (JSONObject)JSONValue.parse(
            """
        {
            "sourceTile": {
                "row": 0,
                "col": 1
            },
            "targetTile": {
                "row": 0,
                "col": 3
            },
            "targetPlayer": 0
        }
            """
        );
        System.out.println(board.tiles[0][2].robber);
        System.out.println(Arrays.toString(moveRobber.execute(test, player)));
        System.out.println(board.tiles[0][2].robber);
    }
}
