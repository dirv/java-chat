package dirv.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private final SocketFactory socketFactory;
    private final String serverAddress;
    private final String user;
    private int port;
    
    public Client(SocketFactory socketFactory, String serverAddress, int port, String user) {
        this.socketFactory = socketFactory;
        this.serverAddress = serverAddress;
        this.user = user;
        this.port = port;
    }

    public boolean register() throws IOException {
        try(Socket socket = socketFactory.createSocket(serverAddress, port);
            PrintWriter printWriter = printWriter(socket);
            BufferedReader reader = bufferedReader(socket)) {
            printWriter.println("1");
            printWriter.println(user);
            return checkResponse(reader);
        }
    }

    public boolean sendMessage(String message) throws IOException {
        try(Socket socket = socketFactory.createSocket(serverAddress, port);
            PrintWriter printWriter = printWriter(socket);
            BufferedReader reader = bufferedReader(socket)) {
            printWriter.println("2");
            printWriter.println(user);
            printWriter.println(message);
            return checkResponse(reader);
        }
    }
        
    private boolean checkResponse(BufferedReader reader) throws IOException {
        return "OK".equals(reader.readLine());
    }

    private static BufferedReader bufferedReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    private static PrintWriter printWriter(Socket socket) throws IOException {
        return new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())));
    }

}
