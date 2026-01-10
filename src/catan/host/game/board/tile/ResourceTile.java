package catan.host.game.board.tile;

import catan.host.game.board.resources.Resource;
import catan.host.game.dice.DiceRollListener;

public class ResourceTile extends Tile implements DiceRollListener {

    public int numberToken;

    public ResourceTile(Resource resource) {
        super(resource);
    }

    public void assignNumberToken(int numberToken) {
        this.numberToken = numberToken;
        // TODO
    }

    @Override
    public void NumberRolled(int rolledNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'NumberRolled'");
    }

    public String toString() {
        return resource.toString() + " :: " + numberToken;
    }
}
