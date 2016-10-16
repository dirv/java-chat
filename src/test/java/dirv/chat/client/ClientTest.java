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
        Client client = new Client(socketFactory, serverAddress, serverPort, "Donald");
        client.register();
        assertEquals(serverAddress, socketFactory.getPassedAddress());
        assertEquals(serverPort, socketFactory.getPassedPort());
    }
    
    @Test
    public void registersUser() throws IOException {
        Client client = new Client(socketFactory, serverAddress, serverPort, "Donald");
        client.register();
        assertEquals("1\nDonald\n", socketFactory.getLastSocket().getOutput());
    }
    
    @Test
    public void closesSocketAfterRegistering() throws IOException {
        Client client = new Client(socketFactory, serverAddress, serverPort, "Donald");
        client.register();
        assertTrue(socketFactory.getLastSocket().getWasClosed());
    }
    
    @Test
    public void returnsTrueIfServerRespondedOk() throws IOException {
        socketFactory.setResponse("OK\n");
        Client client = new Client(socketFactory, serverAddress, serverPort, "Donald");
        boolean successfullyRegistered = client.register();
        assertTrue(successfullyRegistered);
    }
    
    @Test
    public void returnsFalseIfServerDidNotRespondOk() throws IOException {
        socketFactory.setResponse("ERROR\n");
        Client client = new Client(socketFactory, serverAddress, serverPort, "Donald");
        boolean successfullyRegistered = client.register();
        assertFalse(successfullyRegistered);
    }
}
