package dirv.chat.server.commands;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dirv.chat.Message;
import dirv.chat.server.MessageRepositorySpy;
import dirv.chat.server.commands.Command;
import dirv.chat.server.commands.SaveMessageCommand;

public class SaveMessageCommandTest extends CommandTest {

    private String input;
    private final List<String> users = new ArrayList<String>();
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private MessageRepositorySpy messageRepository = new MessageRepositorySpy();
    
    @Test
    public void savesMessage() throws IOException {
        users.add("Donald");
        executeCommand("Donald", "13", "Hello, world!");
        Message message = messageRepository.getMessages().get(0);
        assertEquals("Donald", message.getUser());
        assertEquals("Hello, world!", message.getMessage());
    }
    
    @Test
    public void acknowledgesSave() throws IOException {
        users.add("Donald");
        String output = executeCommand("Donald", "13", "Hello, world!");
        assertEquals("OK\n", output.toString());
    }
    
    @Test
    public void doNotSaveIfUserIsNotRegistered() throws IOException {
        String output = executeCommand("Donald", "13", "Hello, world!");
        assertEquals("ERROR\n", output.toString());
        assertEquals(0, messageRepository.getMessages().size());
    }
    
    @Test
    public void canSaveMultilineMessages() throws IOException {
        users.add("Donald");
        executeCommand("Donald", "12", "Hello", "world!");
        Message message = messageRepository.getMessages().get(0);
        assertEquals("Hello" + System.lineSeparator() + "world!", message.getMessage());
    }
    
    protected Command command() {
        return new SaveMessageCommand(messageRepository, users);
    }
    
    protected String input() {
        return input;
    }
    
    protected OutputStream output() {
        return output;
    }
    
}
