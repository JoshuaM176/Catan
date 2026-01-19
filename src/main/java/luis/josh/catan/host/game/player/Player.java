package luis.josh.catan.host.game.player;

import luis.josh.catan.host.game.board.resources.ResourceListener;
import luis.josh.catan.host.game.gamepieces.developmentcards.DevelopmentCard;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import luis.josh.catan.host.game.board.resources.Resource;

public class Player implements ResourceListener{
    Map<Resource, Integer> resources = new HashMap<Resource, Integer>(); // Card Resources
    List<DevelopmentCard> developmentCards = new ArrayList<DevelopmentCard>();

    @Override
    public void addResource(Resource resource) {
        if(resources.containsKey(resource)) {
            resources.put(resource, resources.get(resource)+1);
        }
        else {
            resources.put(resource, 1);
        }
    }

    public String toString() {
        String string = "";
        for(Resource resource: resources.keySet()) {
            string += resource.name() + " :: " + resources.get(resource);
        }
        return string;
    }
}
