package luis.josh.catan.host.game.player;

import luis.josh.catan.host.game.board.resources.ResourceListener;
import luis.josh.catan.host.game.gamepieces.cards.CardDeck;
import luis.josh.catan.host.game.gamepieces.cards.ResourceCard;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.DevelopmentCard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.Harbor;
import luis.josh.catan.host.game.board.Vertex;
import luis.josh.catan.host.game.board.resources.Resource;

public class Player implements ResourceListener{
    CardDeck<ResourceCard> resources = new CardDeck<>();
    CardDeck<DevelopmentCard> devCards = new CardDeck<>();
    public CardDeck<DevelopmentCard> usedDevCards = new CardDeck<>();
    public List<Harbor> harbors = new ArrayList<Harbor>();
    public Map<Vertex, Set<Vertex>> roadGraph = new HashMap<>();
    private int victoryPoints = 0;
    private Consumer<JSONObject> messageQueue;
    private int playerNum;

    /**
     * Constructor with no messageQueue or playerNum for testing purposes.
     */
    public Player() {
        this.messageQueue = e -> {};
        playerNum = 0;
    }

    /**
     * Construct player with the given messageQueue and assigned player number.
     * @param messageQueue Message queue to send data back to client.
     * @param playerNum The index for this player.
     */
    public Player(Consumer<JSONObject> messageQueue, int playerNum) {
        this.messageQueue = messageQueue;
        this.playerNum = playerNum;
    }

    /**
     * @return The index for this player.
     */
    public int playerNum() {
        return playerNum;
    }

    @Override
    public void addResource(Resource resource) {
        resources.addCard(new ResourceCard(resource));
        messageQueue.accept(
            EventResponses.eventResponse(
                "gainedResource",
                "all",
                new JSONObject(
                    Map.of(
                        "resource", resource.name(),
                        "amount", 1,
                        "player", playerNum
                    )
                )
            )
        );
    }

    /**
     * Add resources to this player's hand.
     * @param resource Type of resource to add.
     * @param amount Amount of the resource to add.
     */
    public void addResources(Resource resource, int amount) {
        resources.addCards(new ResourceCard(resource), amount);
        messageQueue.accept(
            EventResponses.eventResponse(
                "gainedResource",
                "all",
                new JSONObject(
                    Map.of(
                        "resource", resource.name(),
                        "amount", amount,
                        "player", playerNum
                    )
                )
            )
        );
    }

    /**
     * Add a development card to this player's hand.
     * @param card The type of card to add.
     */
    public void addDevCard(DevelopmentCard card) {
        devCards.addCard(card);
        messageQueue.accept(
            EventResponses.eventResponse(
                "gainedDevCard",
                "all",
                new JSONObject(
                    Map.of(
                        "card", card.getName(),
                        "player", playerNum
                    )
                )
            )
        );
    }

    /**
     * Spends a single resource and sends corresponding message.
     * @param resource The resource type to spend.
     */
    public void subtractResource(Resource resource) {
        resources.subtractCard(new ResourceCard(resource));
        messageQueue.accept(
            EventResponses.eventResponse(
                "spentResource",
                "all",
                new JSONObject(
                    Map.of(
                        "resource", resource.name(),
                        "amount", 1,
                        "player", playerNum
                    )
                )
            )
        );
    }

    /**
     * Spends multiple of a resource and sends corresponding message.
     * @param resource The resource type to spend.
     * @param amount Amount of that resource to spend.
     */
    public void subtractResource(Resource resource, int amount) {
        resources.subtractCards(new ResourceCard(resource), amount);
        messageQueue.accept(
            EventResponses.eventResponse(
                "spentResource",
                "all",
                new JSONObject(
                    Map.of(
                        "resource", resource.name(),
                        "amount", amount,
                        "player", playerNum
                    )
                )
            )
        );
    }

    /**
     * Return true if the user has the given dev card.
     * @param card The card to check for.
     * @return True if user has the card.
     */
    public boolean hasDevCard(DevelopmentCard card) {
        if(!devCards.hasCards(Map.of(card, 1))) {
            return false;
        }
        return true;
    }

    /**
     * Spends a single dev card and sends corresponding message.
     * @param resource The card type to use.
     */
    public boolean useDevCard(DevelopmentCard card) {
        devCards.subtractCard(card);
        usedDevCards.addCard(card);
        messageQueue.accept(
            EventResponses.eventResponse(
                "usedDevCard",
                "all",
                new JSONObject(
                    Map.of(
                        "card", card.getName(),
                        "player", playerNum
                    )
                )
            )
        );
        return true;
    }

    /**
     * Checks if the player has more than or equal to given amounts of each resource.
     * @param resources A map of each resource type to check and the amount of that resource to check for.
     * @return True if the player has enough resources.
     */
    private boolean hasResources(Map<Resource, Integer> resources) {
        Map<ResourceCard, Integer> resourceCards = new HashMap<>();
        for(Resource resource: resources.keySet()) {
            resourceCards.put(new ResourceCard(resource), resources.get(resource));
        }
        return this.resources.hasCards(resourceCards);
    }

    /**
     * Subtracts a random resource with probability proportional to amount of resources.
     * @return The random resource selected.
     */
    public Resource stealResource() {
        ResourceCard randomCard = resources.drawCard();
        if(randomCard == null) { return null; }
        messageQueue.accept(
            EventResponses.eventResponse(
                "spentResource",
                "all",
                new JSONObject(
                    Map.of(
                        "resource", randomCard.resource().name(),
                        "amount", 1,
                        "player", playerNum
                    )
                )
            )
        );
        return randomCard.resource();
    }

    /**
     * Subtracts all resources of a specific type and returns the amount.
     * @param resource
     * @return
     */
    public int stealAllResources(Resource resource) {
        int count = resources.cardCount(new ResourceCard(resource));
        subtractResource(resource, count);
        return count;
    }

    /**
     * @return The sum total of all resources owned by this player.
     */
    public int totalResources() {
        return resources.totalCards();
    }

    /**
     * Checks if player has enough resources to make a purchase, and subtracts those resources if true.
     * @param resources The map of resources to the amount to spend.
     * @return True if purchase was successful.
     */
    public boolean checkAndPurchase(Map<Resource, Integer> resources) {
        if(!hasResources(resources)) {
            return false;
        }
        for(Resource resource: resources.keySet()) {
            subtractResource(resource, resources.get(resource));
        }
        return true;
    }

    /**
     * Adds a single victory point to this player.
     */
    public void addVictoryPoint() {
        addVictoryPoint(1);
    }

    /**
     * Adds amount of victory points to player and sends message.
     * @param amount Number of victory points to add.
     */
    public void addVictoryPoint(int amount) {
        victoryPoints += amount;
        messageQueue.accept(
            EventResponses.eventResponse(
                "gainedVictoryPoint",
                "all",
                new JSONObject(
                    Map.of(
                        "amount", victoryPoints,
                        "player", playerNum
                    )
                )
            )
        );
    }

    /**
     * Subtracts a single victory point from this player.
     */
    public void subtractVictoryPoint() {
        subtractVictoryPoint(1);
    }

    /**
     * Subtracts amount of victory points from player and sends message.
     * @param amount Number of victory points to add.
     */
    public void subtractVictoryPoint(int amount) {
        victoryPoints += amount;
        messageQueue.accept(
            EventResponses.eventResponse(
                "lostVictoryPoint",
                "all",
                new JSONObject(
                    Map.of(
                        "amount", victoryPoints,
                        "player", playerNum
                    )
                )
            )
        );
    }

    /**
     * Add a connection from vertex1 to vertex2 and from vertex2 to vertex1 in this
     * player's roadGraph.
     * @param vertex1
     * @param vertex2
     */
    public void addBiConnection(Vertex vertex1, Vertex vertex2) {
        addConnection(vertex1, vertex2);
        addConnection(vertex2, vertex1);
    }

    /**
     * Adds a connection from vertex1 to vertex in this player's roadGraph.
     * @param vertex1
     * @param vertex2
     */
    private void addConnection(Vertex vertex1, Vertex vertex2) {
        Set<Vertex> v1Cons = roadGraph.get(vertex1);
        if(v1Cons == null) {
            v1Cons = new HashSet<>();
            roadGraph.put(vertex1, v1Cons);
        }
        v1Cons.add(vertex2);
    }

    @Override
    public String toString() {
        return String.format(
            """
            Dev Cards: %s
            Resources: %s
            Harbors: %s
            Victory Points: %d
            """,
            devCards.toString(),
            resources.toString(),
            harbors.toString(),
            victoryPoints
        );
    }
}
