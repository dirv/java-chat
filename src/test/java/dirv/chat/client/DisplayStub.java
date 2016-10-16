package dirv.chat.client;

import java.util.ArrayList;
import java.util.List;

import dirv.chat.Message;

public class DisplayStub extends Display {
    
    private List<Message> messagesShown = new ArrayList<>();
    private Exception lastException;
    
    @Override
    public void exception(Exception exception) {
        this.lastException = exception;
    }
    
    @Override
    public void message(Message message) {
        this.messagesShown.add(message);
    }
    
    public Exception getLastException() {
        return lastException;
    }
    
    public List<Message> getMessagesShown() {
        return messagesShown;
    }

}
