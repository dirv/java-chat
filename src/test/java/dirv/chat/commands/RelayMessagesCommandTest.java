package dirv.chat.commands;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;

import org.junit.Test;

import dirv.chat.commands.Command;
import dirv.chat.commands.RelayMessagesCommand;
import dirv.chat.MessageRepositorySpy;

public class RelayMessagesCommandTest extends CommandTest {

    private MessageRepositorySpy messageRepository = new MessageRepositorySpy();
    
    @Test
    public void delegatesToMessageRepositoryForMessages() throws IOException {
        executeCommand("100\n");
        assertEquals(100L, messageRepository.getAskedForMessagesSince());
    }
    
    @Test
    public void printsOutReturnedMessages() throws IOException {
        messageRepository.add(200, "Donald", "Hello, world!");
        messageRepository.add(201, "Donald", "Hello?");
        String output = executeCommand("100");
        String[] lines = output.split("\n");
        int cur = 0;
        assertEquals("200", lines[cur++]);
        assertEquals("Donald", lines[cur++]);
        assertEquals("Hello, world!", lines[cur++]);
        assertEquals("201", lines[cur++]);
        assertEquals("Donald", lines[cur++]);
        assertEquals("Hello?", lines[cur++]);
        
    }

    @Override
    protected Command command() {
        return new RelayMessagesCommand(messageRepository);
    }

}
