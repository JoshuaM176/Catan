package luis.josh.catan.host.game.player;

import luis.josh.catan.host.game.board.resources.ResourceListener;
import luis.josh.catan.host.game.gamepieces.developmentcards.DevelopmentCard;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import luis.josh.catan.host.game.board.Harbor;
import luis.josh.catan.host.game.board.resources.Resource;

public class Player implements ResourceListener{
    Map<Resource, Integer> resources = new HashMap<Resource, Integer>(); // Card Resources
    List<DevelopmentCard> developmentCards = new ArrayList<DevelopmentCard>();
    public List<Harbor> harbors = new ArrayList<Harbor>();

    @Override
    public void addResource(Resource resource) {
        if(resources.containsKey(resource)) {
            resources.put(resource, resources.get(resource)+1);
        }
        else {
            resources.put(resource, 1);
        }
    }

    private boolean hasResources(Map<Resource, Integer> resources) {
        for(Resource resource: resources.keySet()) {
            if(this.resources.get(resource) == null) {
                return false;
            }
            if(this.resources.get(resource) < resources.get(resource)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAndPurchase(Map<Resource, Integer> resources) {
        if(!hasResources(resources)) {
            return false;
        }
        for(Resource resource: resources.keySet()) {
            this.resources.put(resource, this.resources.get(resource) - resources.get(resource));
        }
        return true;
    }

    public String toString() {
        String string = "";
        for(Resource resource: resources.keySet()) {
            string += resource.name() + " :: " + resources.get(resource) + " ";
        }
        return string;
    }
}
