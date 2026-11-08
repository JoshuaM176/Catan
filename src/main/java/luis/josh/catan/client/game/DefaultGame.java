package luis.josh.catan.client.game;

import luis.josh.catan.client.game.board.Board;
import luis.josh.catan.client.game.events.CreateBoard;
import luis.josh.catan.client.game.events.Event;

public class DefaultGame extends Game{

    @Override
    protected Event[] generateEvents(Board board) {
        return new Event[]{
            new CreateBoard(this, (x) -> {System.out.println(x);}, (x) -> {}, (x) -> {})
        };
    }
    
}
