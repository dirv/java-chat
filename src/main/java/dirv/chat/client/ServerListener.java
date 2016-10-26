package dirv.chat.client;

import java.io.IOException;

import dirv.chat.Message;

public class ServerListener implements Runnable {

    private final MessageSender messageSender;
    private final Display display;
    private long lastTimestampSeen = 0;
    private String user;
    private BotRunner botRunner;
    
    public ServerListener(MessageSender messageSender, Display display, String user, BotRunner botRunner) {
        this.messageSender = messageSender;
        this.display = display;
        this.user = user;
        this.botRunner = botRunner;
    }

    @Override
    public void run() {
        try {
            messageSender.retrieveMessagesSince(lastTimestampSeen)
            .stream()
            .forEach(this::handleMessage);
        } catch (IOException e) {
            display.exception(e);
        }
    }
    
    private void handleMessage(Message message) {
        updateTimestamp(message);
        performBotCommand(message);
        display.message(message);
    }

    private void performBotCommand(Message message) {
        if (message.isFor(user)) {
            String userMessage = message.getUserMessage();
            botRunner.respondToMessage(userMessage);
        }
    }
    
    private void updateTimestamp(Message message) {
        this.lastTimestampSeen = message.getTimestamp();
    }
}
