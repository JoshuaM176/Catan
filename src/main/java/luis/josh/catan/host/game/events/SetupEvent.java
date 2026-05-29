package luis.josh.catan.host.game.events;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actionmanager.ActionManager;
import luis.josh.catan.host.game.actions.Action;
import luis.josh.catan.host.game.actions.PlaceRoad;
import luis.josh.catan.host.game.actions.PlaceSettlement;
import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.events.messages.ActionResponses;
import luis.josh.catan.host.game.player.Player;

/**
 * Event that handles the first two settlement/road placements.
 */
public class SetupEvent implements Event{

    private Runnable prevTurn;
    private Runnable nextTurn;
    private Supplier<Integer> turn;
    private ActionManager settlementActionManager;
    private ActionManager roadActionManager;
    private boolean placedSettlement = false;
    private TurnHandler turnHandler;
    private Consumer<JSONObject> messageQueue;

    /**
     * @param prevTurn Runnable to switch to the previous turn.
     * @param nextTurn Runnable to switch to the next turn.
     * @param turn Supplier of the turn.
     */
    public SetupEvent(Runnable prevTurn, Runnable nextTurn, Supplier<Integer> turn) {
        this.prevTurn = prevTurn;
        this.nextTurn = nextTurn;
        this.turn = turn;
    }

    @Override
    public void initialize(Board board, Player[] players, Consumer<JSONObject> messageQueue) {
        settlementActionManager = new ActionManager(players, new Action[]{new PlaceSettlement(board, null)}).setWaitForTurn(turn);
        roadActionManager = new ActionManager(players, new Action[]{new PlaceRoad(board, null)}).setWaitForTurn(turn);
        turnHandler = new TurnHandler(players.length);
        this.messageQueue = messageQueue;
        sendAction();
    }

    @Override
    public JSONObject[] acceptData(JSONObject data) {
        int playerNum = (int)data.get("player");
        if(playerNum != turn.get()) {
            return new JSONObject[]{EventResponses.waitForTurn(playerNum)};
        }
        JSONObject[] results;
        if(placedSettlement) {
            results = roadActionManager.executeAction(data);
        }
        else{
            results = settlementActionManager.executeAction(data);
        }
        if(results.length>0) {
            String result = (String)results[0].get("event");
            if(result.equals("placedSettlement")) {
                placedSettlement = true;
                sendAction();
            }
            if(result.equals("placedRoad")) {
                placedSettlement = false;
                turnHandler.next();
                sendAction();
            }
        }
        return results;
    }

    @Override
    public boolean isFinished() {
        return turnHandler.isFinished();
    }

    @Override
    public String getName() {
        return "setupEvent";
    }

    /**
     * Notifies player of required action.
     */
    public void sendAction() {
        String actionName;
        if(!placedSettlement){
            actionName = "placeStartingSettlement";
        }
        else{
            actionName = "placeStartingRoad";
        }
        messageQueue.accept(ActionResponses.actionResponse(
            actionName,
            turn.get(),
            new  JSONObject()
        ));
    }

    private class TurnHandler {
        int numTurnsPassed = 0;
        int numPlayers;

        TurnHandler(int numPlayers) {
            this.numPlayers = numPlayers;
        }

        void next() {
            numTurnsPassed++;
            if(numTurnsPassed < numPlayers) {
                nextTurn.run();
                return;
            }
            if(numTurnsPassed == numPlayers) {
                return;
            }
            if(numTurnsPassed < numPlayers*2) {
                prevTurn.run();
                return;
            }
        }

        boolean isFinished() {
            return numTurnsPassed == numPlayers*2;
        }
    }
  
}
