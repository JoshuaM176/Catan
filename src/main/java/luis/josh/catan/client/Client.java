package luis.josh.catan.client;

import javax.swing.JFrame;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import luis.josh.catan.client.game.DefaultGame;

public class Client {
    
    public static void main(String[] args) throws Exception {
        CommunicationManager communicationManager = new CommunicationManager(Integer.parseInt(args[0]));
        communicationManager.send("start game");
        DefaultGame game = new DefaultGame();
        while(true) {
            String msg = communicationManager.read();
            if(msg != null) {
                JSONObject jsonObject = (JSONObject)JSONValue.parse(msg);
                if(jsonObject == null) {
                    String err = "Failed to parse: " + msg;
                    throw new Exception(err);
                }
                game.acceptData(jsonObject);
            }
        }
    }
}
