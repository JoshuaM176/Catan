package luis.josh.catan.host.game.gamepieces.cards.developmentcards;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.player.Player;

public class Knight extends DevelopmentCard{

  @Override
  public JSONObject[] execute(JSONObject data, Player player) {
    player.addKnight();
    return new JSONObject[]{EventResponses.moveRobberTrigger()};
  }

  @Override
  public String getName() {
    return "knight";
  }
  
}
