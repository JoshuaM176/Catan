package luis.josh.catan.host.game.board.tile.harborassigner;

import luis.josh.catan.host.game.board.Harbor;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DefaultHarborAssigner implements HarborAssigner{
    
    int numHarbors;
    List<Integer> coasts = new ArrayList<Integer>();
    List<Tile> coastTiles = new ArrayList<Tile>();

    private List<Resource> resources = new ArrayList<Resource>();

    public DefaultHarborAssigner(int NumHarbors, Map<Resource, Integer> resources) {
        this.numHarbors = NumHarbors;
        for(Resource resource : resources.keySet()) {
            for(int i = 0; i < resources.get(resource); i++) {
                this.resources.add(resource);
            }
        }
    }

    public void assignHarbors(Tile[][] tiles) {

        if(numHarbors == 0) {
            return;
        }

        int[][] tilePattern = new int[tiles.length][tiles[0].length];
        for(int row = 0; row < tiles.length; row++) {
            for(int col = 0; col < tiles[0].length; col++) {
                if(tiles[row][col] == null) {
                    tilePattern[row][col] = 0;
                }
                else {
                    tilePattern[row][col] = 1;
                }
            }
        }

        for(int step = 0; step < tiles.length / 2 + 1; step++) {
            for(int x = step; x < tiles[0].length - step; x++) {
                int y = step;
                if(tilePattern[y][x] == 1) {
                    tilePattern[y][x] = 0;
                    appendCoast(tiles[y][x], 3);
                }
            }
            for(int y = step + 1; y < tiles.length - step; y++) {
                int x = tiles.length - step - 1;
                if(tilePattern[y][x] == 1) {
                    tilePattern[y][x] = 0;
                    appendCoast(tiles[y][x], 5);
                }
                else if(x > 0) {
                    if(tilePattern[y][x-1] == 1) {
                        tilePattern[y][x-1] = 0;
                        appendCoast(tiles[y][x-1], 5);
                    }
                }
            }
            for(int x = tiles[0].length - step - 2; x > step - 1; x--) {
                int y = tiles.length - step - 1;
                if(tilePattern[y][x] == 1) {
                    tilePattern[y][x] = 0;
                    appendCoast(tiles[y][x], 0);
                }
            }
            for(int y = tiles.length - step - 2; y > step; y--) {
                int x = step;
                if(tilePattern[y][x] == 1) {
                    tilePattern[y][x] = 0;
                    appendCoast(tiles[y][x], 2);
                }
            }
        }

        double spaceBetweenHarbors = (double)coasts.size() / numHarbors;
        for(double i = spaceBetweenHarbors; i < coasts.size(); i += spaceBetweenHarbors) {
            int index = (int)Math.floor(i);
            assignHarbor(coastTiles.get(index), coasts.get(index));
        }

    }

    private void appendCoast(Tile tile, int startingEdge) {
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
    public int harborsAssigned = 0;
    private void assignHarbor(Tile tile, int edge) {
        harborsAssigned+=1;
        Random random = new Random();
        Resource resource = resources.remove(Math.abs(random.nextInt()) % resources.size());
        Harbor harbor = new Harbor(resource);
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
