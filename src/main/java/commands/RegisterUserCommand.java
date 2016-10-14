package main.java.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class RegisterUserCommand extends RecognizedCommand {

    private final List<String> users;

    public RegisterUserCommand(List<String> users) {
        super("1");
        this.users = users;
    }

    @Override
    public void execute(BufferedReader reader) throws IOException {
        users.add(reader.readLine());
    }

}
