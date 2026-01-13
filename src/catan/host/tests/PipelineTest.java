package catan.host.tests;

import java.util.Map;

import catan.host.game.board.Board;
import catan.host.game.board.resources.Resource;
import catan.host.game.board.tile.numbertokenassigner.DefaultNumberTokenAssigner;
import catan.host.game.board.tile.numbertokenassigner.NumberTokenAssigner;
import catan.host.game.board.tile.tilecreator.DefaultTileCreator;
import catan.host.game.board.tile.tilecreator.TileCreator;
import catan.host.game.gamepieces.City;
import catan.host.game.player.Player;

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
        Board board = new Board(tilePattern, numberTokens, numberTokenAssigner, tileCreator);
        System.out.println(board);
        Player player = new Player();
        City city = new City(player);
        board.tiles[0][1].vertices[1].setPlacedItem(city);
        for(int i = 0; i < 20; i++) {
            System.out.println(board.rollDice());
        }
        System.out.println(player);
    }
}
