package main.java.commands;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class Command {
    
    private final String commandType;

    protected Command(String commandType) {
        this.commandType = commandType;
    }
    
    public boolean respondsTo(String commandType) {
        return this.commandType.equals(commandType);
    }

    public abstract void execute(BufferedReader reader) throws IOException;
}
