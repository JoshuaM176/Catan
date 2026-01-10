package catan.host.game.board.tile;

import catan.host.game.board.Vertex;
import catan.host.game.board.resources.Resource;

public class Tile{
    
    public Resource resource;
    public Vertex[] vertices = new Vertex[6];
    public boolean hasRobber = false;

    public Tile(Resource resource) {
        this.resource = resource;
    }

    public String toString() {
        return resource.toString();
    }

}
