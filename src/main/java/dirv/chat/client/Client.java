package dirv.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client implements Runnable {

    private final ScheduledExecutorService executor;
    private final ServerListener serverListener;
    private final MessageSender messageSender;
    private final Display display;
    private final InputStream input;
    
    public Client(ScheduledExecutorService executor, ServerListener serverListener,
            MessageSender messageSender, Display display, InputStream input) {
        this.executor = executor;
        this.serverListener = serverListener;
        this.messageSender = messageSender;
        this.display = display;
        this.input = input;
    }

    public void run() {
        scheduleMessageReading();
        try {
            if (!registerUser()) {
                display.error("user registration failed");
                return;
            }
            sendMessagesFromInput();
        } catch(IOException ex) {
            display.exception(ex);
        }
    }

    private void scheduleMessageReading() {
        executor.scheduleWithFixedDelay(serverListener, 0, 3, TimeUnit.SECONDS);
    }
    
    private boolean registerUser() throws IOException {
        return messageSender.register();
    }
    
    private void sendMessagesFromInput() throws IOException {
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(input))) {
            String message;
            while ((message = reader.readLine()) != null) {
                boolean success = messageSender.sendMessage(message);
                if (!success)
                    display.error("server rejected message");
            }
        }
    }
    

}
