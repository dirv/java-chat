package dirv.chat.server.commands;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import dirv.chat.server.commands.Command;

public abstract class CommandTest {
    protected abstract Command command();

    protected String executeCommand(String input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try(BufferedReader reader = reader(input)) {
            try(PrintWriter writer = writer(output)) {
                command().execute(reader, writer);
            }
        }
        return output.toString();
    }

    private BufferedReader reader(String input) {
        return new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(input.getBytes())));
    }

    private PrintWriter writer(ByteArrayOutputStream output) {
        return new PrintWriter(new OutputStreamWriter(output));
    }
}
