package test.java;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Test;

import main.java.Server;
import main.java.ServerSocketFactory;

public class ServerTest {

    private ServerSocketSpy serverSocketSpy;
    private ServerSocketFactory serverSocketFactory = new ServerSocketFactory() {
        @Override
        public ServerSocket buildServerSocket(int port) throws IOException {
            return serverSocketSpy = new ServerSocketSpy(port);
        }
        
    };

    @Test
    public void listensOnPort3000() throws IOException {
        Server server = new Server(serverSocketFactory);
        server.startListening();
        assertNotNull(serverSocketSpy);
        assertEquals(3000, serverSocketSpy.port);
    }
}
