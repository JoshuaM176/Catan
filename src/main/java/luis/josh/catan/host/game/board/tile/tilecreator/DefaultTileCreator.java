package luis.josh.catan.host.game.board.tile.tilecreator;

import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.tile.DesertTile;
import luis.josh.catan.host.game.board.tile.ResourceTile;
import luis.josh.catan.host.game.board.tile.Tile;
import luis.josh.catan.host.game.dice.Dice;

import java.util.Map;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class DefaultTileCreator implements TileCreator{

    private List<Resource> resources = new ArrayList<Resource>();

    public DefaultTileCreator(Map<Resource, Integer> resources) {
        for(Resource resource : resources.keySet()) {
            for(int i = 0; i < resources.get(resource); i++) {
                this.resources.add(resource);
            }
        }
    }

    @Override
    public Tile[][] createTiles(int[][] tilePattern, Dice dice) {
        Tile[][] tiles = new Tile[tilePattern.length][tilePattern[0].length];
        Random random = new Random();
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[0].length; j++) {
                if(tilePattern[i][j] == 1) {
                    Resource resource = resources.remove(Math.abs(random.nextInt()) % resources.size());
                    if(resource == Resource.DESERT) {
                        tiles[i][j] = new DesertTile();
                    }
                    else{
                        tiles[i][j] = new ResourceTile(resource);
                    }
                    TileCreator.addVertices(tiles, tiles[i][j], i, j);
                    TileCreator.addEdges(tiles, tiles[i][j], i, j);
                    dice.addListener(tiles[i][j]);
                }
                else {
                    tiles[i][j] = null;
                }
            }
        }
        return tiles;
    }

}
