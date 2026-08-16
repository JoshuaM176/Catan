package luis.josh.catan.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommunicationManager {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public CommunicationManager(int port) throws UnknownHostException, IOException {
        socket = new Socket("localhost", port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String data) throws IOException {
        out.println(data);
    }

    public String read() throws IOException {
        return in.readLine();
    }
}
