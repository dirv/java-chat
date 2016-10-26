package dirv.chat.client;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BotRunnerTest {

    private MessageSenderStub messageSender = new MessageSenderStub(Arrays.asList());
    private BotFactorySpy botFactory = new BotFactorySpy();
    private BotRunner botRunner = new BotRunner(messageSender, botFactory);

    @Test
    public void startsAGameOfHangman() {
        botRunner.respondToMessage("hangman!");
        List<String> messagesSent = messageSender.getMessagesSent();
        assertTrue(botFactory.getBot().getStateWasCalled());
        assertEquals(1, messagesSent.size());
        assertEquals("initial state", messagesSent.get(0));
    }

    @Test
    public void playsCorrectHangmanMove() {
        botRunner.respondToMessage("hangman!");
        botRunner.respondToMessage("a");
        List<String> messagesSent = messageSender.getMessagesSent();
        assertEquals(2, messagesSent.size());
        assertEquals("called with a", messagesSent.get(1));
    }

    @Test
    public void doesNotPlayMoveIfNotInAHangmanGame() {
        botRunner.respondToMessage("a");
        List<String> messagesSent = messageSender.getMessagesSent();
        assertEquals(0, messagesSent.size());
    }

    @Test
    public void doesNotStartANewGameIfOneIsInProgress() {
        botRunner.respondToMessage("hangman!");
        botRunner.respondToMessage("a");
        botRunner.respondToMessage("hangman!");
        List<String> messagesSent = messageSender.getMessagesSent();
        assertEquals(3, messagesSent.size());
        assertEquals("called with hangman!", messagesSent.get(2));
    }
    
    @Test
    public void sendsMessageToCurrentBot() {
        botRunner.respondToMessage("hangman!");
        botRunner.respondToMessage("a");
        assertTrue(botFactory.getBot().getWasCalled());
        assertEquals("a", botFactory.getBot().getReceivedMessage());
    }
}
