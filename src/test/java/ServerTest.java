package test.java;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.Message;
import main.java.Server;

public class ServerTest {

    private ServerSocketFactoryStub serverSocketFactory = new ServerSocketFactoryStub();
    private List<String> users = new ArrayList<String>();
    private MessageRepositorySpy messageRepository = new MessageRepositorySpy();

    @Test
    public void listensOnPort3000() throws IOException {
        startListening();
        assertEquals(3000, serverSocketFactory.getPort());
    }
    
    @Test
    public void acceptsASocket() throws IOException {
        SocketStub client = new SocketStub();
        serverSocketFactory.addClient(client);
        startListening();
        assertEquals(1, serverSocketFactory.acceptedSockets.size());
        assertEquals(client, serverSocketFactory.acceptedSockets.get(0));
    }
    
    @Test
    public void connectsANewClient() throws IOException {
        SocketStub client = new SocketStub("1\nDonald\n");
        serverSocketFactory.addClient(client);
        startListening();
        assertThat(users, hasItem("Donald"));
    }
    
    @Test
    public void receiveMessages() throws IOException {
        users.add("Donald");
        SocketStub client = new SocketStub("2\nDonald\nHello, world!\n");
        serverSocketFactory.addClient(client);
        startListening();
        assertNotNull(messageRepository.getLastMessage());
        assertEquals("Donald", messageRepository.getLastMessage().getUser());
        assertEquals("Hello, world!", messageRepository.getLastMessage().getMessage());
    }
    
    private void startListening() {
        Server server = new Server(serverSocketFactory, users, messageRepository);
        server.startListening();
    }
}
