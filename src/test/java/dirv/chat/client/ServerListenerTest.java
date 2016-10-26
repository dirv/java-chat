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
    public void startsAGameOfHangmanIfUserRequestsIt() {
        user = "bot";
        Message hangmanMessage = new Message(1, "user", "@bot hangman!");
        setMessagesToReceive(hangmanMessage);
        serverListener().run();
        List<String> messagesSent = messageSender.getMessagesSent();
        assertEquals(1, messagesSent.size());
        assertEquals("_ _ _ _ _ _ (8 lives left)", messagesSent.get(0));
    }
    
    @Test
    public void doesNotStartAGameOfHangmanIfUsernameIsNotPresent() {
        Message hangmanMessage = new Message(1, "user", "hangman!");
        setMessagesToReceive(hangmanMessage);
        serverListener().run();
        List<String> messagesSent = messageSender.getMessagesSent();
        assertEquals(0, messagesSent.size());
    }
    
    @Test
    public void playsCorrectHangmanMove() {
        user = "bot";
        Message hangmanMessage = new Message(1, "user", "@bot hangman!");
        Message hangmanGuess = new Message(1, "user", "@bot a");
        setMessagesToReceive(hangmanMessage, hangmanGuess);
        serverListener().run();
        List<String> messagesSent = messageSender.getMessagesSent();
        assertEquals(2, messagesSent.size());
        assertEquals("_ _ _ A _ _ (8 lives left)", messagesSent.get(1));
    }
    
    @Test
    public void doesNotPlayMoveIfNotInAHangmanGame() {
        user = "bot";
        Message hangmanGuess = new Message(1, "user", "@bot a");
        setMessagesToReceive(hangmanGuess);
        serverListener().run();
        List<String> messagesSent = messageSender.getMessagesSent();
        assertEquals(0, messagesSent.size());
    }
    
    @Test
    public void doesNotStartANewGameIfOneIsInProgress() {
        user = "bot";
        Message hangmanGuess = new Message(1, "user", "@bot a");
        Message hangmanMessage = new Message(1, "user", "@bot hangman!");
        setMessagesToReceive(hangmanMessage, hangmanGuess, hangmanMessage);
        serverListener().run();
        List<String> messagesSent = messageSender.getMessagesSent();
        assertEquals(3, messagesSent.size());
        assertEquals("_ _ _ A _ _ (8 lives left)", messagesSent.get(2));
    }
    

    private void setMessagesToReceive(Message... messages) {
        messageSender = new MessageSenderStub(Arrays.asList(messages));
    }

    private ServerListener serverListener() {
        return new ServerListener(messageSender, display, user);
    }
}
