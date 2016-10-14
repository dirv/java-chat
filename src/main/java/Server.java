package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    private final ServerSocketFactory serverSocketFactory;
    private final List<String> users;
    private final MessageRepository messageRepository;

    public Server(ServerSocketFactory serverSocketFactory, List<String> users, MessageRepository messageRepository) {
        this.serverSocketFactory = serverSocketFactory;
        this.users = users;
        this.messageRepository = messageRepository;
    }

    public void startListening() {
        try {
            ServerSocket serverSocket = serverSocketFactory.buildServerSocket(3000);
            Socket socket = serverSocket.accept();
            if (socket != null) {
                try(BufferedReader bufferedReader
                        = new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()))) {
                    String messageType = bufferedReader.readLine();
                    if (messageType != null) {
                        String user = bufferedReader.readLine();
                        if (messageType.equals("1")) {
                            users.add(user);
                        }
                        if (messageType.equals("2")) {
                            String message = bufferedReader.readLine();
                            messageRepository.receiveMessage(user,  message);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }
}
