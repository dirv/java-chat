package dirv.chat.client;

import java.io.IOException;

import dirv.chat.Message;

public class ServerListener implements Runnable {

    private final MessageSender messageSender;
    private final Display display;
    private long lastTimestampSeen = 0;
    private String user;
    
    public ServerListener(MessageSender messageSender, Display display, String user) {
        this.messageSender = messageSender;
        this.display = display;
        this.user = user;
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
        performHangmanCommand(message);
        display.message(message);
    }

    private void performHangmanCommand(Message message) {
        if (message.isFor(user) && message.getUserMessage().equals("hangman!")) {
            try {
                messageSender.sendMessage("_ _ _ _ _ _ (8 lives left)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateTimestamp(Message message) {
        this.lastTimestampSeen = message.getTimestamp();
    }
}
