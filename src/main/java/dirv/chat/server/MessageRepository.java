package dirv.chat.server;

import java.util.stream.Stream;

import dirv.chat.Message;

public interface MessageRepository {

    public void receiveMessage(String user, String message);
    public Stream<Message> getMessagesSince(long timestamp);
}
