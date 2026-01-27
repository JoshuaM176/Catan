package luis.josh.catan.host.game.actions;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.player.Player;

public class RollDice implements Action{
    
    Board board;

    public RollDice(Board board) {
        this.board = board;
    }

    @Override
    public JSONObject execute(JSONObject data, Player player) {
        int result = board.rollDice();
        String jsonString = """
        {
            "event": "diceRolled",
            "data": {
                "numberRolled": %d
            }
        }           
        """;
        jsonString = String.format(jsonString, result);
        return (JSONObject)JSONValue.parse(jsonString);
    }

}
