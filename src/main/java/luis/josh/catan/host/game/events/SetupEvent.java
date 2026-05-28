package luis.josh.catan.host.game.events;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actionmanager.ActionManager;
import luis.josh.catan.host.game.actions.Action;
import luis.josh.catan.host.game.actions.PlaceRoad;
import luis.josh.catan.host.game.actions.PlaceSettlement;
import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.player.Player;

public class SetupEvent implements Event{

    private Runnable prevTurn;
    private Runnable nextTurn;
    private Supplier<Integer> turn;
    private ActionManager settlementActionManager;
    private ActionManager roadActionManager;
    private boolean placedSettlement = false;
    private int numPlayers;

    public SetupEvent(Runnable prevTurn, Runnable nextTurn, Supplier<Integer> turn) {
        this.prevTurn = prevTurn;
        this.nextTurn = nextTurn;
        this.turn = turn;
    }

    @Override
    public void initialize(Board board, Player[] players, Consumer<JSONObject> messageQueue) {
        settlementActionManager = new ActionManager(players, new Action[]{new PlaceSettlement(board, null)}).setWaitForTurn(turn);
        roadActionManager = new ActionManager(players, new Action[]{new PlaceRoad(board, null)}).setWaitForTurn(turn);
        numPlayers = players.length;
    }

    @Override
    public JSONObject[] acceptData(JSONObject data) {
        int playerNum = (int)data.get("player");
        if(playerNum != turn.get()) {
            return new JSONObject[]{EventResponses.waitForTurn(playerNum)};
        }
        if(!placedSettlement) {
            return settlementActionManager.executeAction(data);
        }
        return roadActionManager.executeAction(data);
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFinished'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

  
}
