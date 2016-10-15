package main.java;

import java.util.stream.Stream;

public interface MessageRepository {

    public void receiveMessage(String user, String message);
    public Stream<Message> getMessagesSince(long timestamp);
}
