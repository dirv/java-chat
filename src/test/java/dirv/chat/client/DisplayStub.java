package dirv.chat.client;

import java.util.ArrayList;
import java.util.List;

import dirv.chat.Message;

public class DisplayStub extends Display {
    
    public DisplayStub() {
        super(null);
    }

    private List<Message> messagesShown = new ArrayList<>();
    private Exception lastException;
    private String lastError;
    
    @Override
    public void exception(Exception exception) {
        this.lastException = exception;
    }
    
    @Override
    public void error(String error) {
        this.lastError = error;
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
    
    public String getLastError() {
        return lastError;
    }

}
