package dirv.chat.client;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import dirv.chat.Message;

public class ClientTest {
    
    private SocketFactoryStub socketFactory = new SocketFactoryStub();
    private final String serverAddress = "1.2.3.4";
    private final int serverPort = 3000;

    @Test
    public void opensSocketWithGivenIpAndPort() throws IOException {
        buildClient().register();
        assertEquals(serverAddress, socketFactory.getPassedAddress());
        assertEquals(serverPort, socketFactory.getPassedPort());
    }
    
    @Test
    public void registersUser() throws IOException {
        buildClient().register();
        assertEquals("1\nDonald\n", socketFactory.getLastSocket().getOutput());
    }
    
    @Test
    public void closesSocketAfterRegistering() throws IOException {
        buildClient().register();
        assertTrue(socketFactory.getLastSocket().getWasClosed());
    }
    
    @Test
    public void returnsTrueIfRegisteredUser() throws IOException {
        socketFactory.setResponse("OK\n");
        boolean successfullyRegistered = buildClient().register();
        assertTrue(successfullyRegistered);
    }
    
    @Test
    public void returnsFalseIfDidNotRegisterUser() throws IOException {
        socketFactory.setResponse("ERROR\n");
        boolean successfullyRegistered = buildClient().register();
        assertFalse(successfullyRegistered);
    }
    
    @Test
    public void sendsMessageToServer() throws IOException {
        buildClient().sendMessage("Message");
        assertEquals("2\nDonald\nMessage\n", socketFactory.getLastSocket().getOutput());
    }
    
    @Test
    public void closesSocketAfterSending() throws IOException {
        buildClient().sendMessage("Message");
        assertTrue(socketFactory.getLastSocket().getWasClosed());;
    }
    
    @Test
    public void returnsTrueIfSentMessage() throws IOException {
        socketFactory.setResponse("OK\n");
        boolean successfullySent = buildClient().sendMessage("Message");
        assertTrue(successfullySent);
    }

    @Test
    public void returnsFalseIfDidNotSendMessage() throws IOException {
        socketFactory.setResponse("ERROR\n");
        boolean successfullySent = buildClient().sendMessage("Message");
        assertFalse(successfullySent);
    }
    
    @Test
    public void retrievesMessagesSinceTimestamp() throws IOException {
        buildClient().retrieveMessagesSince(123);
        assertEquals("3\n123\n", socketFactory.getLastSocket().getOutput());
    }
    
    @Test
    public void closesSocketAfterRetrieving() throws IOException {
        buildClient().retrieveMessagesSince(123);
        assertTrue(socketFactory.getLastSocket().getWasClosed());
    }
    
    @Test
    public void returnsListOfRetrievedMessages() throws IOException {
        Message message1 = new Message(200, "Donald", "Hello, world!");
        Message message2 = new Message(300, "Donald", "Hello?");
        socketFactory.setResponse(message1.asResponseString() + message2.asResponseString());
        List<Message> receivedMessages = buildClient().retrieveMessagesSince(123);
        assertEquals(2, receivedMessages.size());
        assertEquals(message1.getTimestamp(), receivedMessages.get(0).getTimestamp());
        assertEquals(message1.getUser(), receivedMessages.get(0).getUser());
        assertEquals(message1.getMessage(), receivedMessages.get(0).getMessage());
        assertEquals(message2.getTimestamp(), receivedMessages.get(1).getTimestamp());
        assertEquals(message2.getUser(), receivedMessages.get(1).getUser());
        assertEquals(message2.getMessage(), receivedMessages.get(1).getMessage());
    }
    
    private Client buildClient() {
        return new Client(socketFactory, serverAddress, serverPort, "Donald");
    }
}
