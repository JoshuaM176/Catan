package luis.josh.catan.host.game.board;

import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.board.resources.ResourceListener;
import luis.josh.catan.host.game.player.Player;

import java.util.Map;
import java.util.HashMap;

public class Vertex implements ResourceListener{

    public VertexPlaceable placedItem = null;
    public Harbor harbor = null;
    private Map<Player, Integer> connections = new HashMap<Player, Integer>();

    public void setPlacedItem(VertexPlaceable item) {
        placedItem = item;
        if(harbor != null) {
            item.addHarbor(harbor);
        }
    }

    public boolean hasPlacedItem() {
        if(placedItem != null) {
            return true;
        }
        return false;
    }

    public boolean hasPlacedItem(Player player) {
        if(placedItem != null) {
            if(placedItem.getPlayer() == player) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addResource(Resource resource) {
        if(placedItem != null) {
            placedItem.addResource(resource);
        }
    }

    public boolean isConnected(Player player) {
        if(connections.containsKey(player)) {
            if(connections.get(player) > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isConnected() {
        for(int conn : connections.values()) {
            if(conn > 0) { return true; }
        }
        return false;
    }

    public void addConnection(Player player) {
        if(connections.containsKey(player)) {
            connections.put(player, connections.get(player) + 1);
        }
        else {
            connections.put(player, 1);
        }
    }

    public void removeConnection(Player player) {
        connections.put(player, connections.get(player) -1);
    }

}
