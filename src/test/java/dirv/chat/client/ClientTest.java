package dirv.chat.client;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ClientTest {

    @Test
    public void schedulesListenerWhenStarted() {
        ServerListener serverListener = new ServerListener(null, null);
        ScheduledExecutorServiceSpy executor = new ScheduledExecutorServiceSpy();
        MessageSenderStub messageSender = new MessageSenderStub();
        
        Client client = new Client(executor, serverListener, messageSender);
        client.start();
        assertEquals(serverListener, executor.getScheduledCommand());
        assertEquals(0, executor.getScheduledInitialDelay());
        assertEquals(3, executor.getScheduledDelay());
        assertEquals(TimeUnit.SECONDS, executor.getScheduledTimeUnit());
    }
}
