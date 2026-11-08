package luis.josh.catan.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;

public class CommunicationManager {
    private Socket socket;
    private PrintWriter out;
    private BlockingQueue<String> messageQueue;
    protected static final Logger logger = ClientLogger.getLogger(CommunicationManager.class);

    public CommunicationManager(int port) throws UnknownHostException, IOException {
        socket = new Socket("localhost", port);
        out = new PrintWriter(socket.getOutputStream(), true);
        messageQueue = new LinkedBlockingQueue<>(10);
        new Thread(new ClientInHandler(socket, messageQueue)).start();
    }

    public void send(String data) throws IOException {
        out.println(data);
    }

    public String read() {
        return messageQueue.poll();
    }

    static class ClientInHandler implements Runnable {
        private Socket clientSocket;
        private BlockingQueue<String> queue;

        public ClientInHandler(Socket socket, BlockingQueue<String> queue) {
            this.clientSocket = socket;
            this.queue = queue;
        }   

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                while(true) {
                    String msg = in.readLine();
                    if(msg != null) {
                        queue.put(msg);
                        logger.info("Received from client: " + msg);
                    }
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
