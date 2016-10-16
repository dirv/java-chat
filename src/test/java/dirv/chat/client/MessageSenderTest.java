package dirv.chat.client;

import static org.junit.Assert.*;
import static dirv.chat.client.Examples.*;
import static dirv.chat.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import dirv.chat.Message;

public class MessageSenderTest {
    
    private SocketFactoryStub socketFactory = new SocketFactoryStub();
    private final String serverAddress = "1.2.3.4";
    private final int serverPort = 3000;

    @Test
    public void opensSocketWithGivenIpAndPort() throws IOException {
        buildMessageSender().register();
        assertEquals(serverAddress, socketFactory.getPassedAddress());
        assertEquals(serverPort, socketFactory.getPassedPort());
    }
    
    @Test
    public void registersUser() throws IOException {
        buildMessageSender().register();
        assertEquals("1\nDonald\n", socketFactory.getLastSocket().getOutput());
    }
    
    @Test
    public void closesSocketAfterRegistering() throws IOException {
        buildMessageSender().register();
        assertTrue(socketFactory.getLastSocket().getWasClosed());
    }
    
    @Test
    public void returnsTrueIfRegisteredUser() throws IOException {
        socketFactory.setResponse("OK\n");
        boolean successfullyRegistered = buildMessageSender().register();
        assertTrue(successfullyRegistered);
    }
    
    @Test
    public void returnsFalseIfDidNotRegisterUser() throws IOException {
        socketFactory.setResponse("ERROR\n");
        boolean successfullyRegistered = buildMessageSender().register();
        assertFalse(successfullyRegistered);
    }
    
    @Test
    public void sendsMessageToServer() throws IOException {
        buildMessageSender().sendMessage("Message");
        assertEquals("2\nDonald\nMessage\n", socketFactory.getLastSocket().getOutput());
    }
    
    @Test
    public void closesSocketAfterSending() throws IOException {
        buildMessageSender().sendMessage("Message");
        assertTrue(socketFactory.getLastSocket().getWasClosed());;
    }
    
    @Test
    public void returnsTrueIfSentMessage() throws IOException {
        socketFactory.setResponse("OK\n");
        boolean successfullySent = buildMessageSender().sendMessage("Message");
        assertTrue(successfullySent);
    }

    @Test
    public void returnsFalseIfDidNotSendMessage() throws IOException {
        socketFactory.setResponse("ERROR\n");
        boolean successfullySent = buildMessageSender().sendMessage("Message");
        assertFalse(successfullySent);
    }
    
    @Test
    public void retrievesMessagesSinceTimestamp() throws IOException {
        buildMessageSender().retrieveMessagesSince(123);
        assertEquals("3\n123\n", socketFactory.getLastSocket().getOutput());
    }
    
    @Test
    public void closesSocketAfterRetrieving() throws IOException {
        buildMessageSender().retrieveMessagesSince(123);
        assertTrue(socketFactory.getLastSocket().getWasClosed());
    }
    
    @Test
    public void returnsListOfRetrievedMessages() throws IOException {
        socketFactory.setResponse(MESSAGE1.asResponseString() + MESSAGE2.asResponseString());
        List<Message> receivedMessages = buildMessageSender().retrieveMessagesSince(123);
        assertEquals(2, receivedMessages.size());
        assertMessageEquals(MESSAGE1, receivedMessages.get(0));
        assertMessageEquals(MESSAGE2, receivedMessages.get(1));
    }
    
    private MessageSender buildMessageSender() {
        return new MessageSender(socketFactory, serverAddress, serverPort, "Donald");
    }
}
