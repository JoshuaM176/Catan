package catan.host.game;

import catan.host.game.Tile;
import catan.host.game.Vertex;
import catan.host.game.Resource;
import java.util.ArrayList;

public class Board {
    
    private Vertex[][] vertices = new Vertex[6][11];
    private Tile[][] tiles = new Tile[5][5];

    public Board() {
        ArrayList<Resource> resources = new ArrayList<>();
        addResources(resources, Resource.STONE, 3);
        addResources(resources, Resource.BRICK, 3);
        addResources(resources, Resource.WHEAT, 4);
        addResources(resources, Resource.LOGS, 4);
        addResources(resources, Resource.SHEEP, 4);
        addResources(resources, Resource.DESERT, 1);
    }

    private void addResources(ArrayList<Resource> resources, Resource resource, int count) {
        for( int i = 0; i < count; i++ ) {
            resources.add(resource);
        }
    }
}

