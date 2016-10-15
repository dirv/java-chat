package test.java.commands;

import static org.junit.Assert.*;

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

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final List<String> users = new ArrayList<String>();

    @Test
    public void acknowledgesAdd() throws IOException {
        try(BufferedReader reader = reader()) {
            try(PrintWriter writer = writer()) {
                command().execute(reader, writer);
            }
        }
        assertEquals("OK\n", output.toString());
    }
    
    private Command command() {
        return new RegisterUserCommand(users);
    }
    
    private BufferedReader reader() {
        return new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream("".getBytes())));
    }

    private PrintWriter writer() {
        return new PrintWriter(new OutputStreamWriter(output));
    }
}
