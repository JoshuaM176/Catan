package luis.josh.catan.host.game.gamepieces;

import luis.josh.catan.host.game.board.Harbor;
import luis.josh.catan.host.game.board.VertexPlaceable;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.player.Player;

public class City implements VertexPlaceable{

    Player player;

    public City(Player player) {
        this.player = player;
    }

    @Override
    public void addResource(Resource resource) {
        player.addResource(resource);
        player.addResource(resource);
    }

    @Override
    public void addHarbor(Harbor harbor) {
    }

    public Player getPlayer() {
        return player;
    }
}
