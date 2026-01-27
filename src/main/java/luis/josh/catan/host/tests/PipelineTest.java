package luis.josh.catan.host.tests;

import java.util.Map;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.Edge;
import luis.josh.catan.host.game.board.Vertex;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.harborassigner.DefaultHarborAssigner;
import luis.josh.catan.host.game.board.tile.harborassigner.HarborAssigner;
import luis.josh.catan.host.game.board.tile.numbertokenassigner.DefaultNumberTokenAssigner;
import luis.josh.catan.host.game.board.tile.numbertokenassigner.NumberTokenAssigner;
import luis.josh.catan.host.game.board.tile.tilecreator.DefaultTileCreator;
import luis.josh.catan.host.game.board.tile.tilecreator.TileCreator;
import luis.josh.catan.host.game.gamepieces.Robber;
import luis.josh.catan.host.game.gamepieces.Settlement;
import luis.josh.catan.host.game.player.Player;
import luis.josh.catan.host.game.actions.MoveRobber;
import luis.josh.catan.host.game.actions.RollDice;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class PipelineTest {
   
    public static void main(String[] args) {
        int[][] tilePattern = new int[][] {
            {0,1,1,1,0},
            {1,1,1,1,0},
            {1,1,1,1,1},
            {1,1,1,1,0},
            {0,1,1,1,0}
        };
        int[] numberTokens = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
        NumberTokenAssigner numberTokenAssigner = new DefaultNumberTokenAssigner();
        TileCreator tileCreator = new DefaultTileCreator(Map.of(
            Resource.STONE, 3,
            Resource.BRICK, 3,
            Resource.WHEAT, 4,
            Resource.LOGS, 4,
            Resource.SHEEP, 4,
            Resource.DESERT, 1
        ));
        HarborAssigner harborAssigner = new DefaultHarborAssigner(9,
            Map.of(
                Resource.STONE, 1,
                Resource.BRICK, 1,
                Resource.WHEAT, 1,
                Resource.LOGS, 1,
                Resource.SHEEP, 1,
                Resource.ANY, 4
            )
        );
        Board board = new Board(tilePattern, numberTokens, numberTokenAssigner, tileCreator, harborAssigner);
        System.out.println(board);
        Player player = new Player();
        Settlement settlement = new Settlement(player);
        board.tiles[0][3].vertices[1].setPlacedItem(settlement);

        RollDice rollDice = new RollDice(board);
        JSONObject jsonObject = new JSONObject();
        for(int i = 0; i < 20; i++) {
            System.out.println(rollDice.execute(jsonObject, player));
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
        MoveRobber moveRobber = new MoveRobber(board);
        JSONObject test = (JSONObject)JSONValue.parse(
            """
        {
            "sourceTile": {
                "x": 1,
                "y": 0
            },
            "targetTile": {
                "x": 2,
                "y": 0
            }
        }
            """
        );
        System.out.println(board.tiles[0][2].robber);
        System.out.println(moveRobber.execute(test, player));
        System.out.println(board.tiles[0][2].robber);
    }
}
