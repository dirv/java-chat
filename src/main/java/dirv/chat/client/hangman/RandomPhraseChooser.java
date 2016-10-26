package dirv.chat.client.hangman;

import java.util.Random;
import java.util.function.Function;

public class RandomPhraseChooser implements Function<String[], String> {

    @Override
    public String apply(String[] phrases) {
        Random random = new Random();
        return phrases[random.nextInt(phrases.length)];
    }

}
