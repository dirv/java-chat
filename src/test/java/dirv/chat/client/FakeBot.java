package dirv.chat.client;

public class FakeBot implements Bot {

    private boolean wasCalled = false;
    private String receivedMessage;
    private String state = "initial state";
    private boolean getStateWasCalled = false;

    @Override
    public void respondToMessage(String message) {
        this.receivedMessage = message;
        this.wasCalled = true;
        this.state = "called with " + message;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }
    
    public boolean getWasCalled() {
        return wasCalled;
    }
    
    public String getState() {
        this.getStateWasCalled = true;
        return state;
    }
    
    public boolean getStateWasCalled() {
        return getStateWasCalled;
    }
}
