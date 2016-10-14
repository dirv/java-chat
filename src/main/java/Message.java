package main.java;

public class Message {

    private final String user;
    private final String message;

    public Message(String user, String message) {
        this.user = user;
        this.message = message;
    }
    
    public String getUser() {
        return user;
    }
    
    public String getMessage() {
        return message;
    }
}
