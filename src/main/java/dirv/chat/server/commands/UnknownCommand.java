package dirv.chat.server.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UnknownCommand implements Command {

    @Override
    public boolean respondsTo(String commandType) {
        return true;
    }

    @Override
    public void execute(BufferedReader reader, PrintWriter printWriter) throws IOException {
    }
}
