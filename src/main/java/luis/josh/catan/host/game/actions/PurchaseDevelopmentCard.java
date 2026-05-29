package luis.josh.catan.host.game.actions;

import java.util.Map;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.resources.Resource;
import luis.josh.catan.host.game.gamepieces.cards.CardDeck;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.DevelopmentCard;
import luis.josh.catan.host.game.player.Player;

public class PurchaseDevelopmentCard implements Action{

    CardDeck<DevelopmentCard> cardDeck;
    Map<Resource, Integer> resourceCost;

    public PurchaseDevelopmentCard(CardDeck<DevelopmentCard> cardDeck, Map<Resource, Integer> resourceCost) {
        this.cardDeck = cardDeck;
        this.resourceCost = resourceCost;
    }

  @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        if(!player.checkAndPurchase(resourceCost)){
            return new JSONObject[]{EventResponses.genericPurchaseFailed("Development Card")};
        }
        if(cardDeck.isEmpty()){
            return new JSONObject[]{EventResponses.eventResponse(
                "devCardPurchaseFailed",
                player,
                new JSONObject(Map.of("message", "No development cards remaining."))
            )};
        }
        player.addDevCard(cardDeck.drawCard());
        return new JSONObject[0];
    }
  
}
