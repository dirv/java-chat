package dirv.chat.client;

import java.io.IOException;

import dirv.chat.client.hangman.HangmanGame;

public class BotRunner {

    private Bot currentBot;
    private final MessageSender messageSender;
    private final BotFactory botFactory;
    
    public BotRunner(MessageSender messageSender, BotFactory botFactory) {
        this.messageSender = messageSender;
        this.botFactory = botFactory;
    }

    public void respondToMessage(String message) {
        try {
            if (currentBot == null) {
                if (message.equals("hangman!")) {
                    currentBot = botFactory.buildBot();
                }
            } else {
                currentBot.respondToMessage(message);
            }
            if (currentBot != null) {
                messageSender.sendMessage(currentBot.getState());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
