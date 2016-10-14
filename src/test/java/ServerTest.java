package test.java;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import main.java.Server;

public class ServerTest {

    private ServerSocketFactoryStub serverSocketFactory = new ServerSocketFactoryStub();

    @Test
    public void listensOnPort3000() throws IOException {
        Server server = new Server(serverSocketFactory);
        server.startListening();
        assertEquals(3000, serverSocketFactory.getPort());
    }
    
    @Test
    public void acceptsASocket() throws IOException {
        SocketSpy client = new SocketSpy();
        serverSocketFactory.addClient(client);
        Server server = new Server(serverSocketFactory);
        server.startListening();
        assertEquals(1, serverSocketFactory.acceptedSockets.size());
        assertEquals(client, serverSocketFactory.acceptedSockets.get(0));
    }
}
