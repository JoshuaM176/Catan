package luis.josh.catan.host.game.gamepieces;

import luis.josh.catan.host.game.board.VertexPlaceable;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.player.Player;

public class Settlement implements VertexPlaceable{

    Player player;

    public Settlement(Player player) {
        this.player = player;
    }

    @Override
    public void addResource(Resource resource) {
        player.addResource(resource);
    }
    
}
