package dirv.chat.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import dirv.chat.Message;

public class Display {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void exception(Exception exception) {
        System.out.println("An internal error occurred: " + exception.getMessage());
    }
    
    public void message(Message message) {
        String timestamp = formatter.format(new Date(message.getTimestamp()));
        String fullString = String.format("[%s] [%s] %s", timestamp, message.getUser(), message.getMessage());
        System.out.println(fullString);
    }
}
