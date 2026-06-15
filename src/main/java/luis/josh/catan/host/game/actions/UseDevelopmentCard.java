package luis.josh.catan.host.game.actions;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.gamepieces.cards.developmentcards.DevelopmentCard;
import luis.josh.catan.host.game.player.Player;

public class UseDevelopmentCard implements Action{

    Map<String, DevelopmentCard> devCardMap;

    public UseDevelopmentCard(DevelopmentCard[] cards) {
        devCardMap = new HashMap<>();
        for(DevelopmentCard card: cards) {
            devCardMap.put(card.getName(), card);
        }
    }

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        String cardName = (String)data.get("card");
        DevelopmentCard card = devCardMap.get(cardName);
        if(card == null) {
            return new JSONObject[]{EventResponses.eventResponse(
                "devCardNotFound",
                "self",
                new JSONObject(Map.of("message", "Dev card " + cardName + " does not exist.")),
                400
            )};
        }
        if(!player.hasDevCard(card)) {
            return new JSONObject[]{EventResponses.eventResponse(
                "noDevCardAvailable",
                "self",
                new JSONObject(Map.of("message", "You do not own this dev card.")),
                400
            )};
        }
        JSONObject[] results = card.execute(data, player);
        if(results.length > 0) {
            int code = (int)results[0].get("code");
            if(code >= 200 && code < 300) {
                player.useDevCard(card);
            }
        }
        else{
            player.useDevCard(card);
        }
        return results;
    }

}
