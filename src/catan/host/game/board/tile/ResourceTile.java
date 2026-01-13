package catan.host.game.board.tile;

import catan.host.game.board.Vertex;
import catan.host.game.board.resources.Resource;

public class ResourceTile extends Tile {

    public int numberToken;

    public ResourceTile(Resource resource) {
        super(resource);
    }

    public void assignNumberToken(int numberToken) {
        this.numberToken = numberToken;
    }

    @Override
    public void NumberRolled(int rolledNumber) {
        if(numberToken == rolledNumber && robber == null) {
            for(Vertex vertex : vertices) {
                vertex.addResource(resource);
            }
        }
    }

    public String toString() {
        return resource.toString() + " :: " + numberToken;
    }
}
