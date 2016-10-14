package test.java;

import main.java.Message;
import main.java.MessageRepository;

public class MessageRepositorySpy implements MessageRepository {

    private Message lastMessage;

    @Override
    public void receiveMessage(String user, String message) {
        lastMessage = new Message(user, message);
    }
    
    public Message getLastMessage() {
        return lastMessage;
    }

}
