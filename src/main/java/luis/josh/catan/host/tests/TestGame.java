package luis.josh.catan.host.tests;

import java.util.function.Consumer;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.DefaultGame;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.DevelopmentCard;
import luis.josh.catan.host.game.player.Player;

public class TestGame extends DefaultGame{

    public TestGame(Consumer<JSONObject> messageQueue, int players) {
        super(messageQueue, players);
    }

    public Player[] players() {
        return players;
    }

    public void addResource(Resource resource, int amount, int playerNum) {
        players[playerNum].addResources(resource, amount);
    }

    public void addDevCard(DevelopmentCard card, int playerNum) {
        players[playerNum].addDevCard(card);
    }
    
}
