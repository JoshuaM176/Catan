package luis.josh.catan.client;

import javax.swing.JFrame;

import luis.josh.catan.client.game.board.tile.Tile;
import luis.josh.catan.host.game.board.resources.Resource;

public class Client {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1600, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
