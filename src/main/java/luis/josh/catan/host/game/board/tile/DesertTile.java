package luis.josh.catan.host.game.board.tile;

import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.resources.ResourceListener;

public class DesertTile extends Tile{

    public DesertTile() {
        super(Resource.DESERT);
    }

    @Override
    public void addResource(ResourceListener resourceListener) {}

}
