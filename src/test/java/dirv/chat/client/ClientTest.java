package dirv.chat.client;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

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
    
    private Client buildClient() {
        return new Client(socketFactory, serverAddress, serverPort, "Donald");
    }
}
