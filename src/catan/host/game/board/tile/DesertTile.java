package catan.host.game.board.tile;

import catan.host.game.board.resources.Resource;

public class DesertTile extends Tile{

    public DesertTile() {
        super(Resource.DESERT);
        hasRobber = true;
    }
}
