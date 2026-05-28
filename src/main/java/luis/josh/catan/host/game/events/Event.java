package luis.josh.catan.host.game.events;

import org.json.simple.JSONObject;

import luis.josh.catan.host.game.board.Board;
import luis.josh.catan.host.game.player.Player;

public interface Event {

    public void initialize(Board board, Player[] players);
  
    public JSONObject[] acceptData(JSONObject data);

    public boolean isFinished();

    public String getName();
    
}
