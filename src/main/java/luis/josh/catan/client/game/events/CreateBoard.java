package luis.josh.catan.client.game.events;

import java.util.function.Consumer;

import org.json.simple.JSONObject;

import luis.josh.catan.client.game.Game;
import luis.josh.catan.client.game.board.Board;
import luis.josh.catan.client.game.board.tile.Edge;
import luis.josh.catan.client.game.board.tile.Tile;
import luis.josh.catan.client.game.board.tile.Vertex;

public class CreateBoard implements Event{

    private Game game;
    private Consumer<Tile> tileOnClick;
    private Consumer<Vertex> vertexOnClick;
    private Consumer<Edge> edgeOnClick;

    public CreateBoard(Game game, Consumer<Tile> tileOnClick, Consumer<Vertex> vertexOnClick, Consumer<Edge> edgeOnClick) {
        this.game = game;
        this.tileOnClick = tileOnClick;
        this.vertexOnClick = vertexOnClick;
        this.edgeOnClick = edgeOnClick;
    }

    @Override
    public void execute(JSONObject data) {
        Board board = new Board(data, tileOnClick, vertexOnClick, edgeOnClick);
        game.board = board;
        game.board.jPanel.setLocation(200, 50);
        game.frame.add(board.jPanel);
        game.board.redraw(200);
    }
    
}
