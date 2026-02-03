package luis.josh.catan.host.game.board.tile;

import luis.josh.catan.host.game.board.Edge;
import luis.josh.catan.host.game.board.Vertex;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.resources.ResourceListener;
import luis.josh.catan.host.game.dice.DiceRollListener;
import luis.josh.catan.host.game.gamepieces.Robber;

public abstract class Tile implements DiceRollListener{
    
    public Resource resource;
    public Vertex[] vertices = new Vertex[6]; // Top is 0
    public Edge[] edges = new Edge[6]; // Top right is 0
    public Robber robber = null;

    public Tile(Resource resource) {
        this.resource = resource;
    }

    public String toString() {
        return resource.toString();
    }

    @Override
    public void NumberRolled(int rolledNumber) {
    }

    public void addResource(ResourceListener resourceListener) {
        resourceListener.addResource(resource);
    }

}
