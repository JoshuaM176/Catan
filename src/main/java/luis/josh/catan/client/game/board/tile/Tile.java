package luis.josh.catan.client.game.board.tile;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

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
    

    public Tile(Resource resource, int numberToken, Coordinate pos, Consumer<Tile> onClick, Consumer<Vertex> vertexOnClick) {
        this.pos = pos;
        imageIcon = new ImageIcon("/home/joshuam/Repositories/Games/Catan2/src/main/java/luis/josh/catan/client/tests/hexagon.png");
        image = new JLabel(imageIcon);
        button = new JButton(resource.name());
        button.addActionListener(e -> onClick.accept(this));
        for(int i = 0; i < 6; i++) {
            vertices[i] = new Vertex(pos, i);
        }
        
        this.numberToken = numberToken;
        this.resource = resource;
    }

    private Rectangle getImageBounds(int size) {
        int offset = (pos.row % 2 == 0) ? 0 : 1;
        int x = (int)(pos.col * (size * 1.5) + offset * (size * 0.75));
        int y = (int)(pos.row * size);
        int width = size * 2;
        int height = size * 2;
        return new Rectangle(x, y, width, height);
    }

    private Rectangle getButtonBounds(int size) {
        int offset = (pos.row % 2 == 0) ? 0 : 1;
        int x = (int)(pos.col * (size * 1.5) + offset * (size * 0.75) + (size * 0.5));
        int y = (int)(pos.row * (size * 1) + (size * 0.5));
        int width = size;
        int height = size;
        return new Rectangle(x, y, width, height);
    }

    public void redraw(int size) {
        imageIcon = new ImageIcon(
            imageIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH)
        );
        image.setIcon(imageIcon);
        image.setBounds(getImageBounds(size));
        button.setBounds(getButtonBounds(size));
    }
}
