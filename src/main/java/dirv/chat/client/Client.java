package dirv.chat.client;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {

    private final ScheduledExecutorService executor;
    private final ServerListener serverListener;
    private final MessageSender messageSender;
    
    public Client(ScheduledExecutorService executor, ServerListener serverListener,
            MessageSender messageSender) {
        this.executor = executor;
        this.serverListener = serverListener;
        this.messageSender = messageSender;
    }

    public void start() {
        scheduleMessageReading();
        
    }

    private void scheduleMessageReading() {
        executor.scheduleWithFixedDelay(serverListener, 0, 3, TimeUnit.SECONDS);
    }
    
    

}
