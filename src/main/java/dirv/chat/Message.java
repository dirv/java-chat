package dirv.chat;

import java.util.Arrays;
import java.util.List;

public class Message {

    private final long timestamp;
    private final String user;
    private final String message;

    public Message(long timestamp, String user, String message) {
        this.timestamp = timestamp;
        this.user = user;
        this.message = message;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public String getUser() {
        return user;
    }
    
    public String getMessage() {
        return message;
    }

    public String asResponseString() {
        return timestamp + System.lineSeparator() +
            user + System.lineSeparator() +
            message + System.lineSeparator();
    }

    public boolean isFor(String user) {
        return getMessage().startsWith("@" + user);
    }

    public String getUserMessage() {
        if(getMessage().startsWith("@")) {
            int indexOfSpace = message.indexOf(" ");
            if (indexOfSpace == -1) {
                return "";
            }
            return message.substring(indexOfSpace + 1);
        } else {
            return getMessage();
        }
    }
}
