package luis.josh.catan.host.game.svp;

import java.util.function.Consumer;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.gamepieces.cards.developmentcards.Knight;
import luis.josh.catan.host.game.player.Player;

public class LargestArmy extends SVP{

    private int maxCards = 0;

    public LargestArmy(Player[] players, Consumer<JSONObject> messageQueue) {
        super(players, messageQueue);
        name = "largestArmy";
    }

    @Override
    public void performCheck() {
        int maxCards = 0;
        Player maxPlayer = null;
        for(Player player : players) {
            int numCards = player.usedDevCards.cardCount(new Knight());
            if(numCards > maxCards) {
                maxCards = numCards;
                maxPlayer = player;
            }
        }
        if(maxCards > this.maxCards && maxCards > 2) {
            this.maxCards = maxCards;
            setOwner(maxPlayer);
        }
    }
    
}
