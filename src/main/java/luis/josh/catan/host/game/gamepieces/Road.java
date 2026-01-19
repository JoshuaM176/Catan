package luis.josh.catan.host.game.gamepieces;

import luis.josh.catan.host.game.board.EdgePlaceable;
import luis.josh.catan.host.game.player.Player;

public class Road implements EdgePlaceable{

    Player player;

    public Road(Player player) {
        this.player = player;
    }
    
}
