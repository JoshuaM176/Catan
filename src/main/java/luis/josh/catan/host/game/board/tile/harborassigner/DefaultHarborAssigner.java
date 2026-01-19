package luis.josh.catan.host.game.board.tile.harborassigner;

import luis.josh.catan.host.game.board.Harbor;
import luis.josh.catan.host.game.board.tile.Tile;
import java.util.ArrayList;
import java.util.List;

public class DefaultHarborAssigner implements HarborAssigner{
    
    int numHarbors;
    List<Integer> coasts = new ArrayList<Integer>();
    List<Tile> coastTiles = new ArrayList<Tile>();

    public DefaultHarborAssigner(int NumHarbors) {
        this.numHarbors = NumHarbors;
    }

    public void assignHarbors(Tile[][] tiles) {

        if(numHarbors == 0) {
            return;
        }

        for(int step = 0; step < tiles.length / 2 + 1; step++) {
            for(int x = step; x < tiles[0].length - step; x++) {
                int y = step;
                if(tiles[y][x] != null) {
                    appendCoast(tiles[y][x], 3);
                }
            }
            for(int y = step + 1; y < tiles.length - step; y++) {
                int x = tiles.length - step - 1;
                if(tiles[y][x] != null) {
                    appendCoast(tiles[y][x], 5);
                }
                else if(x > 0) {
                    if(tiles[y][x-1] != null) {
                        appendCoast(tiles[y][x-1], 5);
                    }
                }
            }
            for(int x = tiles[0].length - step - 2; x > step - 1; x--) {
                int y = tiles.length - step - 1;
                if(tiles[y][x] != null) {
                    appendCoast(tiles[y][x], 0);
                }
            }
            for(int y = tiles.length - step - 2; y > step; y--) {
                int x = step;
                if(tiles[y][x] != null) {
                    appendCoast(tiles[y][x], 2);
                }
            }
        }
        System.out.println(coasts.size());
        double spaceBetweenHarbors = (double)coasts.size() / numHarbors;
        for(int i = 0; i < coasts.size(); i += spaceBetweenHarbors) {
            int index = (int)Math.floor(i);
            assignHarbor(coastTiles.get(index), coasts.get(index));
        }

    }

    private void appendCoast(Tile tile, int startingEdge) {
        if(coastTiles.contains(tile)) {
            return;
        }
        for(int i = startingEdge; i < 6; i++) {
            if(tile.edges[i].coast) {
                coasts.add(i);
                coastTiles.add(tile);
            }
        }
        for(int i = 0; i < startingEdge; i++) {
            if(tile.edges[i].coast) {
                coasts.add(i);
                coastTiles.add(tile);
            }
        }
    }

    private void assignHarbor(Tile tile, int edge) {
        Harbor harbor = new Harbor();
        if(edge == 5) {
            tile.vertices[0].harbor = harbor;
            tile.vertices[5].harbor = harbor;
        }
        else {
            tile.vertices[edge].harbor = harbor;
            tile.vertices[edge+1].harbor = harbor;
        }
    }

}
