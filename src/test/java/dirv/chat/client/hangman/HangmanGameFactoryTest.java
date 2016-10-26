package dirv.chat.client.hangman;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.function.Function;

import org.junit.Test;

import dirv.chat.client.Bot;

public class HangmanGameFactoryTest {

    @Test
    public void usesChooserToChoose() {
        Function<String[], String> chooser = 
                allPhrases -> {
                    return allPhrases[0];
                };
        String[] phrases = new String[] { "hello" };
        HangmanGameBotFactory factory = new HangmanGameBotFactory(chooser, phrases);
        Bot bot = factory.buildBot();
        bot.respondToMessage("h");
        bot.respondToMessage("e");
        bot.respondToMessage("l");
        bot.respondToMessage("o");
        assertThat(bot.getState(), containsString("Congratulations"));
    }
}
