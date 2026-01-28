package luis.josh.catan.host.tests;

import java.util.Map;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.harborassigner.DefaultHarborAssigner;
import luis.josh.catan.host.game.board.tile.harborassigner.HarborAssigner;
import luis.josh.catan.host.game.board.tile.numbertokenassigner.DefaultNumberTokenAssigner;
import luis.josh.catan.host.game.board.tile.numbertokenassigner.NumberTokenAssigner;
import luis.josh.catan.host.game.board.tile.tilecreator.DefaultTileCreator;
import luis.josh.catan.host.game.board.tile.tilecreator.TileCreator;

public class GenerateTestBoard {
    public static Board generateTestBoard() {
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
        return board;
    }
}
