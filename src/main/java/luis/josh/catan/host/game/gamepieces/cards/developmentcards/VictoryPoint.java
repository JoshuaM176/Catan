package luis.josh.catan.host.game.gamepieces.cards.developmentcards;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.player.Player;

public class VictoryPoint extends DevelopmentCard{

    private int points;

    public VictoryPoint() {
        points = 1;
    }

    public VictoryPoint(int points) {
        this.points = points;
    }

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        player.addVictoryPoint();
        return new JSONObject[]{};
    }

    @Override
    public String getName() {
        return "victoryPoint" + points;
    }
  
}
