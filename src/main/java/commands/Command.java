package main.java.commands;

import java.io.BufferedReader;
import java.io.IOException;

public interface Command {
    public boolean respondsTo(String commandType);
    public void execute(BufferedReader reader) throws IOException;
}
