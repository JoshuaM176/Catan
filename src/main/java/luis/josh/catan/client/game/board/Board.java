package luis.josh.catan.client.game.board;

import java.awt.Dimension;
import java.util.function.Consumer;

import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import luis.josh.catan.client.game.board.tile.Tile;
import luis.josh.catan.client.game.board.tile.Vertex;
import luis.josh.catan.client.game.util.Coordinate;
import luis.josh.catan.host.game.board.resources.Resource;

public class Board {

    Tile[][] tiles;
    public JPanel jPanel;
    
    public Board(JSONObject data, Consumer<Tile> tileOnClick, Consumer<Vertex> vertexOnClick) {
        jPanel = new JPanel();
        jPanel.setLayout(null);
        JSONArray tileMatrix = (JSONArray)data.get("board");
        int nrows = tileMatrix.size();
        int ncols = ((JSONArray)tileMatrix.get(0)).size();
        tiles = new Tile[nrows][ncols];
        for(int row = 0; row < nrows; row++) {
            JSONArray rowArray = (JSONArray)tileMatrix.get(row);
            for(int col = 0; col < ncols; col++) {
                JSONObject tileData = (JSONObject)rowArray.get(col);
                if(tileData.get("resource") == null) {
                    tiles[row][col] = null;
                }
                else{
                    Tile tile = new Tile(
                        Resource.valueOf((String)tileData.get("resource")),
                        (int)(long)tileData.get("numberToken"),
                        new Coordinate(col, row),
                        tileOnClick,
                        vertexOnClick
                    );
                    tiles[row][col] = tile;
                    jPanel.add(tile.image);
                    jPanel.add(tile.button);
                }
            }
        }
    }

    public void redraw(int size) {
        jPanel.setSize(getJPanelSize(size));
        for(int row = 0; row < tiles.length; row++) {
            for(int col = 0; col < tiles[0].length; col++) {
                Tile tile = tiles[row][col];
                if(tile != null) {
                    tile.redraw(size);
                }
            }
        }
    }

    private Dimension getJPanelSize(int size) {
        return new Dimension(
            (int)((size * 1.5) * tiles[0].length + (size * 1.25)),
            (int)(size * (tiles.length + 1))
        );
    }
}
