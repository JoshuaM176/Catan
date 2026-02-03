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

    public boolean isValidPlacement(int row, int col, int vertex) {
        Tile tile = tiles[row][col];
        if(tile.vertices[vertex].placedItem != null) {
            return false;
        }
        if(vertex == 0) {
            Tile[] tiles = getNeighborTiles(row, col, vertex);
            if(hasNeighbor(tiles[1], 2)) { return false; }
            if(hasNeighbor(tiles[2], 4)) { return false; }
        }
        if(vertex == 1) {
            Tile[] tiles = getNeighborTiles(row, col, vertex);
            if(hasNeighbor(tiles[1], 3)) { return false; }
            if(hasNeighbor(tiles[2], 5)) { return false; }
        }
        if(vertex == 2) {
            Tile[] tiles = getNeighborTiles(row, col, vertex);
            if(hasNeighbor(tiles[1], 0)) { return false; }
            if(hasNeighbor(tiles[2], 4)) { return false; }
        }
        if(vertex == 3) {
            Tile[] tiles = getNeighborTiles(row, col, vertex);
            if(hasNeighbor(tiles[1], 1)) { return false; }
            if(hasNeighbor(tiles[2], 5)) { return false; }
        }
        if(vertex == 4) {
            Tile[] tiles = getNeighborTiles(row, col, vertex);
            if(hasNeighbor(tiles[1], 0)) { return false; }
            if(hasNeighbor(tiles[2], 2)) { return false; }
        }
        if(vertex == 5) {
            Tile[] tiles = getNeighborTiles(row, col, vertex);
            if(hasNeighbor(tiles[1], 1)) { return false; }
            if(hasNeighbor(tiles[2], 3)) { return false; }
        }
        if(hasNeighbor(getTile(row, col), vertex)) { return false; }
        return true;
    }

    public Tile[] getNeighborTiles(int row, int col, int vertex) {
        int offset = (row % 2 == 0) ? 0 : 1;
        Tile[] tiles = new Tile[3];
        tiles[0] = getTile(row, col);
        if(vertex == 0) {
            tiles[1] = getTile(row - 1, col -1 + offset);
            tiles[2] = getTile(row -1, col);
        }
        if(vertex == 1) {
            tiles[1] = getTile(row - 1, col + offset);
            tiles[2] = getTile(row, col + 1);
        }
        if(vertex == 2) {
            tiles[1] = getTile(row + 1, col + offset);
            tiles[2] = getTile(row + 1, col + 1);
        }
        if(vertex == 3) {
            tiles[1] = getTile(row + 1, col - 1 + offset);
            tiles[2] = getTile(row + 1, col + offset);
        }
        if(vertex == 4) {
            tiles[1] = getTile(row + 1, col - 1 + offset);
            tiles[2] = getTile(row, col - 1);
        }
        if(vertex == 5) {
            tiles[1] = getTile(row, col - 1);
            tiles[2] = getTile(row - 1, col - 1 + offset);
        }
        return tiles;
    }

    private Tile getTile(int row, int col) {
        if(row < 0 || row >= tiles.length || col < 0 || col >= tiles[0].length) {
            return null;
        }
        return tiles[row][col];
    }

    private boolean hasNeighbor(Tile tile, int vertex) {
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

