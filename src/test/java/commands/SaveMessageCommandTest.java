package test.java.commands;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

import main.java.Message;
import main.java.commands.Command;
import main.java.commands.SaveMessageCommand;
import test.java.MessageRepositorySpy;

public class SaveMessageCommandTest extends CommandTest {

    private String input;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private MessageRepositorySpy messageRepository = new MessageRepositorySpy();
    
    @Test
    public void savesMessage() throws IOException {
       executeCommand("Donald\nHello, world!");
       Message message = messageRepository.getMessages().get(0);
       assertEquals("Donald", message.getUser());
       assertEquals("Hello, world!", message.getMessage());
    }
    
    @Test
    public void acknowledgesSave() throws IOException {
       String output = executeCommand("Donald\nHello, world!");
       assertEquals("OK\n", output.toString());
    }
    
    protected Command command() {
        return new SaveMessageCommand(messageRepository);
    }
    
    protected String input() {
        return input;
    }
    
    protected OutputStream output() {
        return output;
    }
    
}
