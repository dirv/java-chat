package dirv.chat.client;

import java.io.IOException;

import dirv.chat.Message;
import dirv.chat.client.hangman.HangmanGame;

public class ServerListener implements Runnable {

    private final MessageSender messageSender;
    private final Display display;
    private long lastTimestampSeen = 0;
    private String user;
    private boolean hangmanMode;
    private HangmanGame hangman;
    
    public ServerListener(MessageSender messageSender, Display display, String user) {
        this.messageSender = messageSender;
        this.display = display;
        this.user = user;
        this.hangmanMode = false;
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
        if (message.isFor(user)) {
            try {
                String userMessage = message.getUserMessage();
                if (userMessage.equals("hangman!") && hangman == null) {
                        hangman = new HangmanGame();
                        messageSender.sendMessage("_ _ _ _ _ _ (8 lives left)");
                } else if (hangman != null) {
                    messageSender.sendMessage(hangman.currentGuess());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updateTimestamp(Message message) {
        this.lastTimestampSeen = message.getTimestamp();
    }
}
