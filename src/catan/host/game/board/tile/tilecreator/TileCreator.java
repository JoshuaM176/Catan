package catan.host.game.board.tile.tilecreator;

import catan.host.game.board.Edge;
import catan.host.game.board.Vertex;
import catan.host.game.board.tile.Tile;
import catan.host.game.dice.Dice;

public interface TileCreator {

    public Tile[][] createTiles(int[][] tilePattern, Dice dice);

    public static void addVertices(Tile[][] tiles, Tile tile, int row, int col) {
        for(int i = 0; i < 6; i++) {
            tile.vertices[i] = new Vertex();
        }
        if(col > 0) {
            Tile tile2 = tiles[row][col-1];
            matchTileVertices(tile, tile2, 4, 5, 2, 1);
        }
        boolean isEven = row % 2 == 0;
        if(isEven && row > 0) {
            if(col > 0) {
                Tile tile2 = tiles[row-1][col-1];
                matchTileVertices(tile, tile2, 0, 5, 2, 3);
            }
            Tile tile2 = tiles[row-1][col];
            matchTileVertices(tile, tile2, 0, 1, 4, 3);
        }
        else if(row > 0) {
            Tile tile2 = tiles[row-1][col];
            matchTileVertices(tile, tile2, 0, 5, 2, 3);
            if(col < tiles.length - 1) {
                tile2 = tiles[row-1][col+1];
                matchTileVertices(tile, tile2, 0, 1, 4, 3);
            }
        }
    };

    public static void addEdges(Tile[][] tiles, Tile tile, int row, int col) {
        for(int i = 0; i < 6; i++) {
            tile.edges[i] = new Edge();
        }
        if(col > 0) {
            Tile tile2 = tiles[row][col-1];
            matchTileEdges(tile, tile2, 4, 1);
        }
        boolean isEven = row % 2 == 0;
        if(isEven && row > 0) {
            if(col > 0) {
                Tile tile2 = tiles[row-1][col-1];
                matchTileEdges(tile, tile2, 5, 2);
            }
            Tile tile2 = tiles[row-1][col];
            matchTileEdges(tile, tile2, 0, 3);
        }
        else if(row > 0) {
            Tile tile2 = tiles[row-1][col];
            matchTileEdges(tile, tile2, 5, 2);
            if(col < tiles.length - 1) {
                tile2 = tiles[row-1][col+1];
                matchTileEdges(tile, tile2, 0, 3);
            }
        }
    };

    private static void matchTileVertices(Tile newTile, Tile oldTile, int newV1, int newV2, int oldV1, int oldV2) {
        if(oldTile != null) {
            newTile.vertices[newV1] = oldTile.vertices[oldV1];
            newTile.vertices[newV2] = oldTile.vertices[oldV2];
        }
    }

    private static void matchTileEdges(Tile newTile, Tile oldTile, int newEdge, int oldEdge) {
        if(oldTile != null) {
            newTile.edges[newEdge] = oldTile.edges[oldEdge];
        }
    }
}
