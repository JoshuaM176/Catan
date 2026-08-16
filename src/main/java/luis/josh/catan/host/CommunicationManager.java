package luis.josh.catan.host;

import luis.josh.catan.host.game.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;

public class CommunicationManager {

    private ServerSocket server;
    private Socket[] playerSockets;
    private BlockingQueue<String>[] playerQueues;
    private BlockingQueue<String> messageQueue;
    private int connected = 0;
    final public int port;
    protected static final Logger logger = HostLogger.getLogger(Game.class);

    public CommunicationManager(int maxPlayers) throws IOException{
        messageQueue = new LinkedBlockingQueue<>(10);
        playerSockets = new Socket[maxPlayers];
        playerQueues = new LinkedBlockingQueue[maxPlayers];
        server = new ServerSocket(0);
        server.setSoTimeout(1000);
        port = server.getLocalPort();
        logger.info("Server started. Listening on port: {}", port);
        startup(maxPlayers);
    }

    private void startup(int maxPlayers) throws IOException {
        List<String> msgBuff = new ArrayList<>();
        while(true) {
            if(connected < maxPlayers) {
                logger.info("Accepting connections");
                try{
                    playerSockets[connected] = server.accept();
                    playerQueues[connected] = new LinkedBlockingQueue<>(5);
                    new Thread(new ClientHandler(playerSockets[connected], messageQueue, playerQueues[connected])).start();
                    connected += 1;
                    logger.info("Recieved connection, player count {}", connected);
                }
                catch (SocketTimeoutException e) {}
            }
            messageQueue.drainTo(msgBuff);
            for(String msg: msgBuff) {
                if(msg.equals("start game")) {
                    return;
                }
            }
            msgBuff.clear();
        }
    }

    public void send(JSONObject data) {
        if(data.get("players") == null) {
            logger.warn("Skipping message because players is null");
            logger.warn(data.toJSONString());
            return;
        }
        try {
            String players = (String)data.get("players");
            if(players.equals("all")) {
                for(int i = 0; i < connected; i++) {
                    playerQueues[i].put(data.toJSONString());
                }
            }
            return;
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        try{
            for(Object player : (JSONArray)data.get("players")) {
                int playerNum = (int)player;
                playerQueues[playerNum].put(data.toJSONString());
            }
            return;
        }
        catch (Exception e){
            logger.debug(e.getMessage());
        }
        logger.warn("Failed to cast players, unable to send message: {}", data.toJSONString());
    }

    public int numConnections() {
        return connected;
    }

    public String read() {
        return messageQueue.poll();
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BlockingQueue<String> inboundQueue;
        private BlockingQueue<String> outboundQueue;

        public ClientHandler(Socket socket, BlockingQueue<String> inboundQueue, BlockingQueue<String> outboundQueue) {
            this.clientSocket = socket;
            this.inboundQueue = inboundQueue;
            this.outboundQueue = outboundQueue;
        }   

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            ) {
                String line;
                while ((line = in.readLine()) != null) {
                    inboundQueue.put(line);
                    logger.info("Received from client: " + line);
                }
                while ((line = outboundQueue.poll()) != null) {
                    out.println(line);
                    logger.info("Sent to client: " + line);
                }
            } catch (IOException | InterruptedException e) {
                logger.warn("Error with client socket: " + e.getMessage());
            } finally {
                try {
                    logger.info("Closing client");
                    clientSocket.close();
                } catch (IOException e) {
                    logger.warn("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }

}
