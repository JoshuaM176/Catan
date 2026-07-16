
package luis.josh.catan.host.game.svp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Vertex;
import luis.josh.catan.host.game.player.Player;

public class LongestRoad extends SVP{

    private int maxRoads = 0;

    public LongestRoad(Player[] players, Consumer<JSONObject> messageQueue) {
        super(players, messageQueue);
        name = "longestRoad";
    }

    @Override
    public void performCheck() {
        int maxRoads = 0;
        Player maxPlayer = null;
        for(Player player : players) {
            int numRoads = getLongestRoad(player);
            if(numRoads > maxRoads) {
                maxRoads = numRoads;
                maxPlayer = player;
            }
        }
        if(maxRoads > this.maxRoads && maxRoads > 4) {
            this.maxRoads = maxRoads;
            setOwner(maxPlayer);
        }
    }

    private static int getLongestRoadRec(Player player, Vertex vertex) {
        return getLongestRoadRec(player, vertex, new HashSet<>());
    }

    private static int getLongestRoadRec(Player player, Vertex vertex, Set<Vertex> visited) {
        visited.add(vertex);
        Set<Vertex> connections = player.roadGraph.get(vertex);
        if(connections == null){
            return 0;
        }
        int max = 0;
        for(Vertex vtx: connections){
            if(visited.contains(vtx)){
                continue;
            }
            max = Math.max(getLongestRoadRec(player, vtx, visited)+1, max);
        }
        return max;
    }

    private static int getLongestRoad(Player player) {
        int max = 0;
        for(Vertex root: player.roadGraph.keySet()){
            max = Math.max(max, getLongestRoadRec(player, root));
        }
        System.out.println("LONGEST ROAD");
        System.out.println("PLAYER: " + player.playerNum());
        System.out.println("LENGTH: " + max);
        System.out.println("\n\n\n\n\n");
        return max;
    }
}
