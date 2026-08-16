package luis.josh.catan.host;

import java.io.IOException;
import java.util.function.Consumer;

import org.json.simple.JSONObject;
import org.slf4j.Logger;

import luis.josh.catan.host.game.DefaultGame;
import luis.josh.catan.host.game.Game;

public class Host {
    public static void main(String[] args) throws IOException {
        Logger logger = HostLogger.getLogger(Host.class);
        CommunicationManager communicationManager = new CommunicationManager(4);
        int numPlayers = communicationManager.numConnections();
        Consumer<JSONObject> messageQueue = (data) -> {
            logger.info("Sent message: {}", data);
        };
        logger.info("Starting game!");
        Game game = new DefaultGame(messageQueue, numPlayers);
    }
}