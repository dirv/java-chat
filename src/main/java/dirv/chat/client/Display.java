package dirv.chat.client;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import dirv.chat.Message;

public class Display {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final PrintStream out;
    
    public Display(PrintStream out) {
        this.out = out;
    }

    public void exception(Exception exception) {
        out.println("An internal error occurred: " + exception.getMessage());
    }
    
    public void message(Message message) {
        String timestamp = formatter.format(new Date(message.getTimestamp()));
        String fullString = String.format("[%s] [%s] %s", timestamp, message.getUser(), message.getMessage());
        out.println(fullString);
    }

    public void error(String error) {
        out.println("Error: " + error);
    }
}
