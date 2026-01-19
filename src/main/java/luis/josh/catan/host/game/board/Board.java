package luis.josh.catan.host.game.board;

import java.util.Arrays;

import luis.josh.catan.host.game.board.tile.Tile;
import luis.josh.catan.host.game.board.tile.harborassigner.HarborAssigner;
import luis.josh.catan.host.game.board.tile.numbertokenassigner.NumberTokenAssigner;
import luis.josh.catan.host.game.board.tile.tilecreator.TileCreator;
import luis.josh.catan.host.game.dice.Dice;

public class Board {
    
    public Tile[][] tiles;
    private Dice dice = new Dice();

    public Board(int[][] tilePattern, int[] numberTokens, NumberTokenAssigner numberTokenAssigner, TileCreator tileCreator, HarborAssigner harborAssigner) {
        tiles = tileCreator.createTiles(tilePattern, dice);
        numberTokenAssigner.assignNumberTokens(tiles, tilePattern, numberTokens);
        harborAssigner.assignHarbors(tiles);
    }

    public int rollDice() {
        return dice.rollDice();
    }

    public String toString() {
        String string = "";
        for(Tile[] tileArray : tiles) {
            string += Arrays.toString(tileArray) + "\n";
        }
        return string;
    }
}

