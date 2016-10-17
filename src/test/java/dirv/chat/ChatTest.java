package dirv.chat;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class ChatTest {

    private boolean wasRun = false;
    private boolean serverWasBuilt = false;
    private boolean clientWasBuilt = false;
    private int serverPort = -1;
    private String clientAddress;
    private int clientPort;
    private String clientUsername;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    private Runnable runnableSpy = new Runnable() {
        public void run() {
            wasRun = true;
        }
    };

    private ServerFactory serverFactory = new ServerFactory() {
        @Override
        public Runnable build(int port) {
            serverWasBuilt = true;
            serverPort = port;
            return runnableSpy;
        }
    };
    
    private ClientFactory clientFactory = new ClientFactory() {
        @Override
        public Runnable build(String address, int port, String username) {
            clientWasBuilt = true;
            clientAddress = address;
            clientPort = port;
            clientUsername = username;
            return runnableSpy;
        }
    };
    
    private Chat chat = new Chat(serverFactory, clientFactory, new PrintStream(out));

    @Test
    public void startsServerOnDefaultPort() {
        runWith("server");
        assertTrue(serverWasBuilt);
        assertEquals(3000, serverPort);
        assertTrue(wasRun);
    }
    
    @Test
    public void startsServerOnGivenPort() {
        runWith("server", "1234");
        assertTrue(serverWasBuilt);
        assertEquals(1234, serverPort);
    }

    @Test
    public void startsClient() {
        runWith("client", "address", "1234", "username");
        assertTrue(clientWasBuilt);
        assertEquals("address", clientAddress);
        assertEquals(1234, clientPort);
        assertEquals("username", clientUsername);
    }
    
    @Test
    public void badParamsShowsUsage() {
        runWith("unknown");
        assertThat(out.toString(), containsString("Usage"));
    }
        
    private void runWith(String... args) {
        chat.runWith(args);
    }
}
