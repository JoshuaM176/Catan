package luis.josh.catan.host.game.player;

import luis.josh.catan.host.game.board.resources.ResourceListener;
import luis.josh.catan.host.game.gamepieces.developmentcards.DevelopmentCard;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.Harbor;
import luis.josh.catan.host.game.board.resources.Resource;

public class Player implements ResourceListener{
    Map<Resource, Integer> resources = new HashMap<Resource, Integer>(); // Card Resources
    List<DevelopmentCard> developmentCards = new ArrayList<DevelopmentCard>();
    public List<Harbor> harbors = new ArrayList<Harbor>();
    private Consumer<JSONObject> messageQueue;
    private int playerNum;

    // For testing
    public Player() {
        this.messageQueue = e -> {};
        playerNum = 0;
    }

    public Player(Consumer<JSONObject> messageQueue, int playerNum) {
        this.messageQueue = messageQueue;
        this.playerNum = playerNum;
    }

    public int playerNum() {
        return playerNum;
    }

    @Override
    public void addResource(Resource resource) {
        if(resources.containsKey(resource)) {
            resources.put(resource, resources.get(resource)+1);
        }
        else {
            resources.put(resource, 1);
        }
        messageQueue.accept(
            EventResponses.eventResponse(
                "gainedResource",
                "all",
                new JSONObject(
                    Map.of(
                        "resource", resource.name(),
                        "player", playerNum
                    )
                )
            )
        );
    }

    public void subtractResource(Resource resource) {
        resources.put(resource, resources.get(resource)-1);
        messageQueue.accept(
            EventResponses.eventResponse(
                "spentResource",
                "all",
                new JSONObject(
                    Map.of(
                        "resource", resource.name(),
                        "player", playerNum
                    )
                )
            )
        );
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

    public Resource stealResource() {
        Random random = new Random();
        int totalResources = totalResources();
        if(totalResources > 0){
            int randomInt = random.nextInt()%totalResources + 1;
            for(Resource resource: resources.keySet()) {
                randomInt -= resources.get(resource);
                if(randomInt <= 0) {
                    subtractResource(resource);
                    return resource;
                }
            }
        }
        return null;
    }

    public int totalResources() {
        int totalResources = 0;
        for(int count: resources.values()) {
            totalResources += count;
        }
        return totalResources;
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
