package dirv.chat.client.hangman;

import java.util.function.Function;

import dirv.chat.client.Bot;
import dirv.chat.client.BotFactory;

public class HangmanGameBotFactory implements BotFactory {

    private Function<String[], String> stringChooser;
    private String[] phrases;

    public HangmanGameBotFactory(Function<String[], String> stringChooser, String[] phrases) {
        this.stringChooser = stringChooser;
        this.phrases = phrases;
    }

    @Override
    public Bot buildBot() {
        String phrase = stringChooser.apply(phrases);
        return new HangmanGame(phrase);
    }
    
}
