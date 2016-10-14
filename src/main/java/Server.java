package main.java;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private final ServerSocketFactory serverSocketFactory;

    public Server(ServerSocketFactory serverSocketFactory) {
        this.serverSocketFactory = serverSocketFactory;
    }

    public void startListening() {
        try {
            ServerSocket serverSocket = serverSocketFactory.buildServerSocket(3000);
            serverSocket.accept();
        } catch (IOException ex)  {
            
        }
    }
}
