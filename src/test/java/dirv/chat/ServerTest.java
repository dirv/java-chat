package dirv.chat.test;

import org.junit.Test;

import dirv.chat.Message;
import dirv.chat.Server;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        SocketStub client = sendClientMessage("");
        startListening();
        assertEquals(1, serverSocketFactory.acceptedSockets.size());
        assertEquals(client, serverSocketFactory.acceptedSockets.get(0));
    }
    
    @Test
    public void connectsANewClient() throws IOException {
        sendClientMessage("1\nDonald\n");
        startListening();
        assertThat(users, hasItem("Donald"));
    }
    
    @Test
    public void receiveMessages() throws IOException {
        users.add("Donald");
        sendClientMessage("2\nDonald\nHello, world!\n");
        startListening();
        assertEquals(1, messageRepository.getMessages().size());
        assertEquals("Donald", message(0).getUser());
        assertEquals("Hello, world!", message(0).getMessage());
    }
    
    @Test
    public void doNotSendAMessageOnUserRegistration() throws IOException {
        sendClientMessage("1\nDonald\n");
        startListening();
        assertEquals(0, messageRepository.getMessages().size());
    }
    
    @Test
    public void doNotAddAUserOnMessageSend() {
        sendClientMessage("2\nDonald\nHello, world!");
        startListening();
        assertEquals(0, users.size());
    }
    
    @Test
    public void ignoresUnrecognizedCommands() {
        sendClientMessage("x\n");
        startListening();
    }
    
    @Test
    public void sendMessageAcknowledgement() {
        SocketStub client = sendClientMessage("1\nDonald\n");
        startListening();
        assertEquals("OK\n", client.getOutput());
    }

    @Test
    public void sendMessagesFromTimestamp() {
        addMessage(100, "Donald", "Hello, world!");
        addMessage(200, "Donald", "Hello?");
        SocketStub client = sendClientMessage("3\n2\n");
        startListening();
        assertEquals(
        "100\n" +
        "Donald\n" +
        "Hello, world!\n" +
        "200\n" +
        "Donald\n" +
        "Hello?\n",
                client.getOutput());
    }
    
    @Test
    public void handlesMultipleClients() {
        sendClientMessage("1\nDonald\n");
        sendClientMessage("2\nDonald\nHello, world!\n");
        SocketStub lastClient = sendClientMessage("3\n0\n");
        startListening();
        assertEquals(
                "-1\n" +
                "Donald\n" + 
                "Hello, world!\n", lastClient.getOutput());
        
    }
    
    private void addMessage(long timestamp, String name, String message) {
        messageRepository.add(timestamp, name, message);
        
    }
    private SocketStub sendClientMessage(String message) {
        SocketStub client = new SocketStub(message);
        serverSocketFactory.addClient(client);
        return client;
    }

    private void startListening() {
        Server server = new Server(serverSocketFactory, users, messageRepository);
        server.listen();
    }

    private Message message(int number) {
        return messageRepository.getMessages().get(number);
    }
}
