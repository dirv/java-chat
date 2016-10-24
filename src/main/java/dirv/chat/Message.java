package dirv.chat;

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
}
