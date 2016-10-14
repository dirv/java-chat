package main.java;

import java.io.IOException;

public class Server {

    private final ServerSocketFactory serverSocketFactory;

    public Server(ServerSocketFactory serverSocketFactory) {
        this.serverSocketFactory = serverSocketFactory;
    }

    public void startListening() {
        try {
            serverSocketFactory.buildServerSocket(3000);
        } catch (IOException ex)  {
            
        }
        
    }

}
