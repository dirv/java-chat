package dirv.chat.client;

public class BotRunnerSpy extends BotRunner {

    private String messageReceived;
    private boolean wasCalled = false;

    public BotRunnerSpy() {
        super(null, null);
    }
    
    @Override
    public void respondToMessage(String message) {
        this.wasCalled = true;
        this.messageReceived = message;
    }
    
    public String getMessageReceived() {
        return messageReceived;
    }
    
    public boolean getWasCalled() {
        return wasCalled;
    }

}
