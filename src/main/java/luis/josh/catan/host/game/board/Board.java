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

    public boolean isValidPlacement(int row, int column, int vertex) {
        Tile tile = tiles[row][column];
        if(tile.vertices[vertex].placedItem != null) {
            return false;
        }
        int offset = (row % 2 == 0) ? 0 : 1;
        if(vertex == 0) {
            if(hasNeighbor(row - 1, column - 1 + offset, 2)) { return false; }
            if(hasNeighbor(row - 1, column, 4)) { return false; }
        }
        if(vertex == 1) {
            if(hasNeighbor(row - 1, column + offset, 3)) { return false; }
            if(hasNeighbor(row, column + 1, 5)) { return false; }
        }
        if(vertex == 2) {
            if(hasNeighbor(row + 1, column + offset, 0)) { return false; }
            if(hasNeighbor(row, column + 1, 4)) { return false; }
        }
        if(vertex == 3) {
            if(hasNeighbor(row + 1, column - 1 + offset, 1)) { return false; }
            if(hasNeighbor(row + 1, column + offset, 5)) { return false; }
        }
        if(vertex == 4) {
            if(hasNeighbor(row + 1, column - 1 + offset, 0)) { return false; }
            if(hasNeighbor(row, column - 1, 2)) { return false; }
        }
        if(vertex == 5) {
            if(hasNeighbor(row, column - 1, 1)) { return false; }
            if(hasNeighbor(row - 1, column - 1 + offset, 3)) { return false; }
        }
        if(hasNeighbor(row, column, vertex)) { return false; }
        return true;
    }

    private boolean hasNeighbor(int row, int col, int vertex) {
        if(row < 0 || row >= tiles.length || col < 0 || col > tiles[0].length) {
            return false;
        }
        Tile tile = tiles[row][col];
        if(tile == null) { return false; }
        if(vertex == 0) {
            if(tile.vertices[1].placedItem != null) { return true; }
            if(tile.vertices[5].placedItem != null) { return true; }
            return false;
        }
        if(vertex == 5) {
            if(tile.vertices[0].placedItem != null) { return true; }
            if(tile.vertices[4].placedItem != null) { return true;}
            return false;
        }
        if(tile.vertices[vertex-1].placedItem != null) { return true; }
        if(tile.vertices[vertex+1].placedItem != null) { return true; }
        return false;
    }

    public String toString() {
        String string = "";
        for(Tile[] tileArray : tiles) {
            string += Arrays.toString(tileArray) + "\n";
        }
        return string;
    }
}

