package test.java;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.Server;

public class ServerTest {

    private ServerSocketFactoryStub serverSocketFactory = new ServerSocketFactoryStub();
    private List<String> users = new ArrayList<String>();

    @Test
    public void listensOnPort3000() throws IOException {
        Server server = new Server(serverSocketFactory, users);
        server.startListening();
        assertEquals(3000, serverSocketFactory.getPort());
    }
    
    @Test
    public void acceptsASocket() throws IOException {
        SocketStub client = new SocketStub();
        serverSocketFactory.addClient(client);
        Server server = new Server(serverSocketFactory, users);
        server.startListening();
        assertEquals(1, serverSocketFactory.acceptedSockets.size());
        assertEquals(client, serverSocketFactory.acceptedSockets.get(0));
    }
    
    @Test
    public void connectsANewClient() throws IOException {
        SocketStub client = new SocketStub("1\nDonald\n");
        serverSocketFactory.addClient(client);
        Server server = new Server(serverSocketFactory, users);
        server.startListening();
        assertThat(users, hasItem("Donald"));
    }
}
