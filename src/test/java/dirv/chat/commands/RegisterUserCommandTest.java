package dirv.chat.test.commands;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dirv.chat.commands.Command;
import dirv.chat.commands.RegisterUserCommand;

public class RegisterUserCommandTest extends CommandTest {

    private final List<String> users = new ArrayList<String>();

    @Test
    public void addsNewName() throws IOException {
        executeCommand("Donald\n");
        assertThat(users, hasItem("Donald"));
    }
    @Test
    public void acknowledgesAdd() throws IOException {
        String output = executeCommand("Donald\n");
        assertEquals("OK\n", output.toString());
    }
    
    @Test
    public void doesNotAddIfNoNameGiven() throws IOException {
        String output = executeCommand("");
        assertEquals(0, users.size());
        assertEquals("ERROR\n", output.toString());
    }
    
    @Test
    public void doesNotAddIfNameIsBlank() throws IOException {
        String output = executeCommand("\n");
        assertEquals(0, users.size());
        assertEquals("ERROR\n", output.toString());
    }
    
    @Test
    public void doesNotAddAlreadyRegisteredNames() throws IOException {
        executeCommand("Donald\n");
        String output = executeCommand("Donald\n");
        assertEquals(1, users.size());
        assertEquals("ERROR\n", output.toString());
    }
    
    @Test
    public void trimsNames() throws IOException {
        executeCommand(" Donald \n");
        assertThat(users, hasItem("Donald"));
    }
    
    protected Command command() {
        return new RegisterUserCommand(users);
    }
}
