package luis.josh.catan.host.game.svp;

import java.util.Map;
import java.util.function.Consumer;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.player.Player;

public abstract class SVP {

    protected Player[] players;
    private Player ownedBy;
    protected int victoryPoints = 2;
    protected String name;
    private Consumer<JSONObject> messageQueue;

    public SVP(Player[] players, Consumer<JSONObject> messageQueue) {
        this.players = players;
    }

    public abstract void performCheck();

    protected void setOwner(Player player) {
        if(ownedBy != null) {
            ownedBy.subtractVictoryPoint(victoryPoints);
        }
        ownedBy = player;
        player.addVictoryPoint(victoryPoints);
        messageQueue.accept(EventResponses.eventResponse(
            "specialVictoryPointGained",
            "all",
            new JSONObject(Map.of(
                "cardName", name,
                "player", player.playerNum()
            )),
            200
        ));
    }

}
