package luis.josh.catan.client.tests;

import java.util.function.Consumer;

import javax.swing.JFrame;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import luis.josh.catan.client.game.board.Board;
import luis.josh.catan.client.game.board.tile.Tile;
import luis.josh.catan.client.game.board.tile.Vertex;

public class CreateBoard {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1600, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        JSONObject data = (JSONObject)JSONValue.parse(
            """
{
	"board": [
		[
			{
			},
            {
				"resource": "BRICK",
				"numberToken": 1
            },
			{
				"resource": "STONE",
				"numberToken": 2
			}
		],
		[
			{
				"resource": "WHEAT",
				"numberToken": 1
			},
            {
                "resource": "LOGS",
                "numberToken": 1
            },
			{
				"resource": "STONE",
				"numberToken": 2
			}
		]
	]
}               
            """ 
        );
				Consumer<Tile> tileOnClick = tile -> {
					System.out.println(tile.resource);
				};
				Consumer<Vertex> vertexOnClick = vertex -> {
					System.out.println("HELLO");
				};
        Board board = new Board(data, tileOnClick, vertexOnClick);
        board.jPanel.setLocation(200, 200);
        frame.add(board.jPanel);
        frame.setVisible(true);
				board.redraw(100);
    } 
}
