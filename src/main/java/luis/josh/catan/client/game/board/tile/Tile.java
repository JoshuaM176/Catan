package luis.josh.catan.client.game.board.tile;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.function.Consumer;

import luis.josh.catan.client.game.util.Coordinate;
import luis.josh.catan.host.game.board.resources.Resource;

public class Tile{

    public int numberToken;
    public Resource resource;
    private Vertex[] vertices = new Vertex[6];
    private Coordinate pos;
    public JLabel image;
    public JButton button;
    private ImageIcon imageIcon;
    

    public Tile(Resource resource, int numberToken, Coordinate pos, Consumer<Tile> onClick, Consumer<Vertex> vertexOnClick, JPanel jPanel) {
        this.pos = pos;
        imageIcon = new ImageIcon("/home/joshuam/Repositories/Games/Catan2/src/main/java/luis/josh/catan/client/tests/hexagon.png");
        image = new JLabel(imageIcon);
        button = new JButton(resource.name());
        button.addActionListener(e -> onClick.accept(this));
        for(int i = 0; i < 6; i++) {
            vertices[i] = new Vertex(pos, i, vertexOnClick, jPanel);
        }
        this.numberToken = numberToken;
        this.resource = resource;
        jPanel.add(image);
        jPanel.add(button);
    }

    private Rectangle getImageBounds(int size) {
        int offset = (pos.row % 2 == 0) ? 0 : 1;
        int x = (int)(pos.col * size + offset * (size * 0.5));
        int y = (int)(pos.row * size * 2 / 3);
        int width = (int)(size);
        int height = (int)(size);
        return new Rectangle(x, y, width, height);
    }

    private Rectangle getButtonBounds(int size) {
        int offset = (pos.row % 2 == 0) ? 0 : 1;
        int x = (int)(pos.col * (size) + offset * (size * 0.5) + (size * 0.25));
        int y = (int)(pos.row * (size * 2 / 3) + (size * 0.25));
        int width = (int)(size * 0.50);
        int height = (int)(size * 0.50);
        return new Rectangle(x, y, width, height);
    }

    public void redraw(int size) {
        imageIcon = new ImageIcon(
            imageIcon.getImage().getScaledInstance((int)(size), (int)(size), Image.SCALE_SMOOTH)
        );
        image.setIcon(imageIcon);
        Rectangle imageBounds = getImageBounds(size);
        image.setBounds(imageBounds);
        button.setBounds(getButtonBounds(size));
        for(int i = 0; i < 6; i++) {
            vertices[i].redraw(
                size, 
                new Coordinate(
                    imageBounds.x,
                    imageBounds.y
                ));
        }
    }
}
