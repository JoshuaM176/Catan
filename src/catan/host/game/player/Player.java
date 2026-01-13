package catan.host.game.player;

import catan.host.game.board.resources.ResourceListener;

import java.util.HashMap;
import java.util.Map;

import catan.host.game.board.resources.Resource;

public class Player implements ResourceListener{
    Map<Resource, Integer> resources = new HashMap<Resource, Integer>(); // Card Resources

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
