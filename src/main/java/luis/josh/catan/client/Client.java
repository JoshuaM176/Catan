package luis.josh.catan.client;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Client {
    
    public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException {
        JFrame frame = new JFrame();
        frame.setSize(1600, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        CommunicationManager communicationManager = new CommunicationManager(Integer.parseInt(args[0]));
        communicationManager.send("start game");
    }
}
