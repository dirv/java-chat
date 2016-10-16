package dirv.chat.client;

import java.io.IOException;

public class ServerListener implements Runnable {

    private final Client client;
    private final Display display;
    
    public ServerListener(Client client, Display display) {
        this.client = client;
        this.display = display;
    }

    @Override
    public void run() {
        try {
            client.retrieveMessagesSince(0)
            .stream()
            .forEach(display::message);
        } catch (IOException e) {
            display.exception(e);
        }
    }

}
