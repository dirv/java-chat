package dirv.chat.client;

import java.io.IOException;

import dirv.chat.Message;

public class ServerListener implements Runnable {

    private final Client client;
    private final Display display;
    private long lastTimestampSeen = 0;
    
    public ServerListener(Client client, Display display) {
        this.client = client;
        this.display = display;
    }

    @Override
    public void run() {
        try {
            client.retrieveMessagesSince(lastTimestampSeen)
            .stream()
            .forEach(this::handleMessage);
        } catch (IOException e) {
            display.exception(e);
        }
    }
    
    private void handleMessage(Message message) {
        updateTimestamp(message);
        display.message(message);
    }
    
    private void updateTimestamp(Message message) {
        this.lastTimestampSeen = message.getTimestamp();
    }

}
