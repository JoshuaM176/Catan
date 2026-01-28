package luis.josh.catan.host.game.board;

import luis.josh.catan.host.game.board.resources.ResourceListener;
import luis.josh.catan.host.game.player.Player;

public interface VertexPlaceable extends ResourceListener{

    public void addHarbor(Harbor harbor);

    public Player getPlayer();

}
