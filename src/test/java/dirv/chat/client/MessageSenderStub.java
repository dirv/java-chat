package dirv.chat.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dirv.chat.Message;

public class MessageSenderStub extends MessageSender {

    private boolean retrieveMessagesWasCalled = false;
    private long lastTimestamp = -1;
    private List<Message> messagesToReturn;
    private final List<String> messagesSent = new ArrayList<>();
    private boolean wasRegistered;

    public MessageSenderStub() {
        this(Collections.emptyList());
    }
    
    public MessageSenderStub(List<Message> messagesToReturn) {
        super(null, "", 0, "");
        this.messagesToReturn = messagesToReturn;
    }
    
    @Override
    public boolean register() throws IOException {
        this.wasRegistered = true;
        return true;
    }
    @Override
    public boolean sendMessage(String message) throws IOException {
       messagesSent.add(message); 
       return true;
    }
    
    public List<String> getMessagesSent() {
        return messagesSent;
    }
    
    @Override
    public List<Message> retrieveMessagesSince(long timestamp) throws IOException {
        this.retrieveMessagesWasCalled = true;
        this.lastTimestamp = timestamp;
        return messagesToReturn;
    }
    
    public long getLastTimestamp() {
        return lastTimestamp;
    }
    
    public boolean getRetrieveMessagesWasCalled() {
        return retrieveMessagesWasCalled;
    }

    public boolean getWasRegistered() {
        return wasRegistered;
    }
}
