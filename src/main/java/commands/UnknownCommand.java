package main.java.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class UnknownCommand implements Command {

    @Override
    public boolean respondsTo(String commandType) {
        return true;
    }

    @Override
    public void execute(BufferedReader reader) throws IOException {
    }
}
