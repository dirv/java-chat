package dirv.chat.client;

import static org.junit.Assert.*;
import static dirv.chat.client.Examples.*;
import static dirv.chat.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import dirv.chat.Message;

public class ServerListenerTest {

    private MessageSenderStub messageSender = new MessageSenderStub();
    private DisplayStub display = new DisplayStub();
    private BotRunnerSpy botRunner = new BotRunnerSpy();
    private String user;

    @Test
    public void implementsRunnable() {
        assertTrue(Runnable.class.isAssignableFrom(ServerListener.class));
    }
    
    @Test
    public void startsCountingFromZeroMessages() {
        serverListener().run();
        assertTrue(messageSender.getRetrieveMessagesWasCalled());
        assertEquals(0, messageSender.getLastTimestamp());
    }
    
    @Test
    public void reportsExceptionsOnException() {
        IOException exception = new IOException();
        messageSender = new MessageSenderStub() {
            @Override
            public List<Message> retrieveMessagesSince(long timestamp) throws IOException {
                throw exception;
            }
        };
        serverListener().run();
        assertEquals(exception, display.getLastException());
    }
    
    @Test
    public void displaysEachMessageWhenRetrieved() {
        setMessagesToReceive(MESSAGE1, MESSAGE2);
        serverListener().run();
        assertEquals(2, display.getMessagesShown().size());
        assertMessageEquals(MESSAGE1, display.getMessagesShown().get(0));
        assertMessageEquals(MESSAGE2, display.getMessagesShown().get(1));
    }
    
    @Test
    public void subsequentCallsUseLatestTimestampSeen() {
        setMessagesToReceive(MESSAGE1);
        ServerListener serverListener = serverListener();
        serverListener.run();
        serverListener.run();
        assertEquals(MESSAGE1.getTimestamp(), messageSender.getLastTimestamp());
    }
    
    @Test
    public void forwardsRequestToBotRunnerIfUserMessage() {
        user = "bot";
        Message hangmanMessage = new Message(1, "user", "@bot hangman!");
        setMessagesToReceive(hangmanMessage);
        serverListener().run();
        assertTrue(botRunner.getWasCalled());
        assertEquals("hangman!", botRunner.getMessageReceived());
    }
    
    @Test
    public void doesNotForwardRequestToBotRunnerIfNotUserMessage() {
        setMessagesToReceive(MESSAGE1);
        serverListener().run();
        assertFalse(botRunner.getWasCalled());
    }

    private void setMessagesToReceive(Message... messages) {
        messageSender = new MessageSenderStub(Arrays.asList(messages));
    }

    private ServerListener serverListener() {
        return new ServerListener(messageSender, display, user, botRunner);
    }
}
