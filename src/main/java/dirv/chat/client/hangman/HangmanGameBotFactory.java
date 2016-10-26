package dirv.chat.client.hangman;

import dirv.chat.client.Bot;
import dirv.chat.client.BotFactory;

public class HangmanGameBotFactory implements BotFactory {

    @Override
    public Bot buildBot() {
        return new HangmanGame();
    }

}
