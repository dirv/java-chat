package test.java;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketSpy extends ServerSocket {

    public final int port;

    public ServerSocketSpy(int port) throws IOException {
        this.port = port;
    }
    
    @Override
    public Socket accept() {
        return null;
    }
}
