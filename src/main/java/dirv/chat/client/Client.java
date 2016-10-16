package dirv.chat.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import dirv.chat.Message;

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
        try(Socket socket = openSocket();
            PrintWriter printWriter = printWriter(socket);
            BufferedReader reader = bufferedReader(socket)) {
            printWriter.println("1");
            printWriter.println(user);
            return checkResponse(reader);
        }
    }

    public boolean sendMessage(String message) throws IOException {
        try(Socket socket = openSocket();
            PrintWriter printWriter = printWriter(socket);
            BufferedReader reader = bufferedReader(socket)) {
            printWriter.println("2");
            printWriter.println(user);
            printWriter.println(message);
            return checkResponse(reader);
        }
    }

    public List<Message> retrieveMessagesSince(long timestamp) throws IOException {
        try(Socket socket = openSocket();
            PrintWriter printWriter = printWriter(socket);
            BufferedReader reader = bufferedReader(socket)) {
            printWriter.println("3");
            printWriter.println(timestamp);
            return readMessages(reader);
        }
    }

    private Socket openSocket() {
        return socketFactory.createSocket(serverAddress, port);
    }

    private boolean checkResponse(BufferedReader reader) throws IOException {
        return "OK".equals(reader.readLine());
    }

    private List<Message> readMessages(BufferedReader reader) throws IOException {
        String timestamp;
        List<Message> messages = new ArrayList<>();
        while((timestamp = reader.readLine()) != null) {
            String user = reader.readLine();
            String message = reader.readLine();
            messages.add(
                new Message(Long.parseLong(timestamp),
                    user,
                    message));
        }
        return messages;
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
