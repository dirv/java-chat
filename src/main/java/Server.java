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

    public Server(ServerSocketFactory serverSocketFactory, List<String> users) {
        this.serverSocketFactory = serverSocketFactory;
        this.users = users;
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
                    bufferedReader.readLine();
                    users.add(bufferedReader.readLine());
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }
}
