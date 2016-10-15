package main.java.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface Command {
    public boolean respondsTo(String commandType);
    public void execute(BufferedReader reader, PrintWriter printWriter) throws IOException;
}
