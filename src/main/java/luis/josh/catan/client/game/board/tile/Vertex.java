package luis.josh.catan.client.game.board.tile;

import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.lang.Math;
import java.awt.Rectangle;

import luis.josh.catan.client.game.util.Coordinate;

public class Vertex {

    public VertexPlaceable placedItem = null;
    public Harbor harbor = null;
    public JButton button;
    public Coordinate tilePos;
    public int pos;

    public Vertex(Coordinate tilePos, int pos, Consumer<Vertex> onClick, JPanel jPanel) {
        this.pos = pos;
        this.tilePos = tilePos;
        button = new JButton("TEST");
        button.addActionListener(e -> onClick.accept(this));
        jPanel.add(button);
    }

    public void redraw(int size, Coordinate topLeft) {
        Coordinate coordinate = getCoordinate((int)(size * 1.5));
        coordinate = coordinate.add(topLeft);
        button.setBounds(getButtonBounds(coordinate, size));
    }

    private Rectangle getButtonBounds(Coordinate pos, int size) {
        size = size/5;
        return new Rectangle(
            pos.x - size/2,
            pos.y - size/2,
            size,
            size
        );
    }

    private Coordinate getCoordinate(int size) {
        if(pos == 0) {
            return new Coordinate(
                size / 2,
                0
            );
        }
        if(pos == 3) {
            return new Coordinate(
                size / 2,
                size
            );
        }
        switch(pos) {
            case 1: {
                return new Coordinate(
                    size,
                    size * 1 / 3
                );
            }
            case 2: {
                return new Coordinate(
                    size,
                    size * 2 / 3
                );
            }
            case 4: {
                return new Coordinate(
                    0,
                    size * 2 / 3
                );
            }
            case 5: {
                return new Coordinate(
                    0,
                    size * 1 / 3
                );
            }
        }
        return null;
    }

}
