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

public class MessageSender {

    private final SocketFactory socketFactory;
    private final String serverAddress;
    private final String user;
    private int port;
    
    public MessageSender(SocketFactory socketFactory, String serverAddress, int port, String user) {
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
            printWriter.flush();
            return checkResponse(reader);
        }
    }

    public boolean sendMessage(String message) throws IOException {
        try(Socket socket = openSocket();
            PrintWriter printWriter = printWriter(socket);
            BufferedReader reader = bufferedReader(socket)) {
            printWriter.println("2");
            printWriter.println(user);
            printWriter.println(message.length());
            printWriter.println(message);
            printWriter.flush();
            return checkResponse(reader);
        }
    }

    public List<Message> retrieveMessagesSince(long timestamp) throws IOException {
        try(Socket socket = openSocket();
            PrintWriter printWriter = printWriter(socket);
            BufferedReader reader = bufferedReader(socket)) {
            printWriter.println("3");
            printWriter.println(timestamp);
            printWriter.flush();
            return readMessages(reader);
        }
    }

    private Socket openSocket() throws IOException {
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
            int length = Integer.parseInt(reader.readLine());
            String message = readStringOfLength(length, reader);
            reader.read();
            messages.add(
                new Message(Long.parseLong(timestamp),
                    user,
                    message));
        }
        return messages;
    }
    
    private String readStringOfLength(int length, BufferedReader reader) throws IOException {
        char[] bytes = new char[length];
        reader.read(bytes, 0, length);
        return new String(bytes);
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
