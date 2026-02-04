package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.actions.messages.ActionResponses;
import luis.josh.catan.host.game.actions.messages.EventResponses;
import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.player.Player;

public class RollDice implements Action{
    
    Board board;

    public RollDice(Board board) {
        this.board = board;
    }

    @Override
    public JSONObject[] execute(JSONObject data, Player player) {
        int result = board.rollDice();
        if(result == 7) {
            return new JSONObject[]{EventResponses.rolledDice(result), ActionResponses.moveRobber(), ActionResponses.discardHalf()};
        }
        return new JSONObject[]{EventResponses.rolledDice(result)};
    }

}
