package catan.host.game.board;

import java.util.Arrays;

import catan.host.game.board.tile.Tile;
import catan.host.game.board.tile.numbertokenassigner.NumberTokenAssigner;
import catan.host.game.board.tile.tilecreator.TileCreator;
import catan.host.game.dice.Dice;

public class Board {
    
    public Tile[][] tiles;
    private Dice dice = new Dice();

    public Board(int[][] tilePattern, int[] numberTokens, NumberTokenAssigner numberTokenAssigner, TileCreator tileCreator) {
        tiles = tileCreator.createTiles(tilePattern, dice);
        numberTokenAssigner.assignNumberTokens(tiles, tilePattern, numberTokens);
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

