package catan.host.game.board.tile;

import catan.host.game.board.Edge;
import catan.host.game.board.Vertex;
import catan.host.game.board.resources.Resource;
import catan.host.game.dice.DiceRollListener;
import catan.host.game.gamepieces.Robber;

public class Tile implements DiceRollListener{
    
    public Resource resource;
    public Vertex[] vertices = new Vertex[6];
    public Edge[] edges = new Edge[6];
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

}
