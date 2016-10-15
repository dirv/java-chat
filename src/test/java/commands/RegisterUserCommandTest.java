package test.java.commands;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.java.commands.Command;
import main.java.commands.RegisterUserCommand;

public class RegisterUserCommandTest {

    private String input;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final List<String> users = new ArrayList<String>();

    @Test
    public void addsNewName() throws IOException {
        input = "Donald\n";
        executeCommand();
        assertThat(users, hasItem("Donald"));
    }
    @Test
    public void acknowledgesAdd() throws IOException {
        input = "Donald\n";
        executeCommand();
        assertEquals("OK\n", output.toString());
    }
    
    @Test
    public void doesNotAddIfNoNameGiven() throws IOException {
        input = "";
        executeCommand();
        assertEquals(0, users.size());
        assertEquals("ERROR\n", output.toString());
    }
    
    @Test
    public void doesNotAddIfNameIsBlank() throws IOException {
        input = "\n";
        executeCommand();
        assertEquals(0, users.size());
        assertEquals("ERROR\n", output.toString());
    }
    
    @Test
    public void doesNotAddAlreadyRegisteredNames() throws IOException {
        input = "Donald\n";
        executeCommand();
        executeCommand();
        assertEquals(1, users.size());
        assertEquals("OK\nERROR\n", output.toString());
    }
    
    @Test
    public void trimsNames() throws IOException {
        input = " Donald \n";
        executeCommand();
        assertThat(users, hasItem("Donald"));
    }
    
    private void executeCommand() throws IOException {
        try(BufferedReader reader = reader()) {
            try(PrintWriter writer = writer()) {
                command().execute(reader, writer);
            }
        }
    }
    
    private Command command() {
        return new RegisterUserCommand(users);
    }
    
    private BufferedReader reader() {
        return new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(input.getBytes())));
    }

    private PrintWriter writer() {
        return new PrintWriter(new OutputStreamWriter(output));
    }
}
