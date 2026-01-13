package catan.host.game.gamepieces;

import catan.host.game.board.VertexPlaceable;
import catan.host.game.board.resources.Resource;
import catan.host.game.player.Player;

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
    
}
