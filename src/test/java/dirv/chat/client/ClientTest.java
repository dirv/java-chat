package dirv.chat.client;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ClientTest {

    private final ServerListener serverListener = new ServerListener(null, null);
    private final ScheduledExecutorServiceSpy executor = new ScheduledExecutorServiceSpy();
    private MessageSenderStub messageSender = new MessageSenderStub();
    private final DisplayStub display = new DisplayStub();

    @Test
    public void schedulesListenerWhenStarted() {
        client("").start();
        assertEquals(serverListener, executor.getScheduledCommand());
        assertEquals(0, executor.getScheduledInitialDelay());
        assertEquals(3, executor.getScheduledDelay());
        assertEquals(TimeUnit.SECONDS, executor.getScheduledTimeUnit());
    }
    
    @Test
    public void registersUsersBeforeSending() {
        client("").start();
        assertTrue(messageSender.getWasRegistered());
    }
    
    @Test
    public void doesNotSendAnyMessagesIfRegistrationFailed() {
        setErrorOnRegistration();
        client("Hello?\n").start();
        assertEquals(0, messageSender.getMessagesSent().size());
    }

    @Test
    public void writesErrorMessageIfRegistrationFailed() {
        setErrorOnRegistration();
        client("").start();
        assertEquals("user registration failed", display.getLastError());
    }

    @Test
    public void sendsMessage() {
        client("Hello, world!\n").start();
        assertEquals(1, messageSender.getMessagesSent().size());
        assertEquals("Hello, world!", messageSender.getMessagesSent().get(0));
    }
    
    @Test
    public void displaysExceptionOnError() {
        IOException exception = new IOException();
        InputStream input = new InputStream() {
            @Override
            public int read() throws IOException {
                throw exception;
            }
        };
        client(input).start();
        assertEquals(exception, display.getLastException());
    }
    
    @Test
    public void sendMultipleMessages() {
        client("A\nB\nC\n").start();
        assertEquals(3, messageSender.getMessagesSent().size());
        assertEquals("A", messageSender.getMessagesSent().get(0));
        assertEquals("B", messageSender.getMessagesSent().get(1));
        assertEquals("C", messageSender.getMessagesSent().get(2));
    }
    
    @Test
    public void displaysMessageSendErrors() {
        setErrorOnMessageSend(); 
        client("A").start();
        assertEquals("server rejected message", display.getLastError());
    }
    
    private void setErrorOnRegistration() {
        messageSender = new MessageSenderStub() {
            @Override
            public boolean register() throws IOException {
                return false;
            }
        };
    }
    
    private void setErrorOnMessageSend() {
        messageSender = new MessageSenderStub() {
            @Override
            public boolean sendMessage(String message) throws IOException {
                return false;
            }
        };
    }
    
    private Client client(String input) {
        return client(new ByteArrayInputStream(input.getBytes()));
    }
    
    private Client client(InputStream input) {
        return new Client(executor, serverListener, messageSender, display, input);
    }
}
