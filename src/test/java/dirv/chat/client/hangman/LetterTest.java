package dirv.chat.client.hangman;

import static org.junit.Assert.*;

import org.junit.Test;

public class LetterTest {
    
    @Test
    public void isAlwaysGuessedIfIsASpace() {
        Letter letter = new Letter(" ");
        assertTrue(letter.wasGuessed());
    }
    
    @Test
    public void isAlwaysGuessedIfNotAlphabetic() {
        Letter letter = new Letter("/");
        assertTrue(letter.wasGuessed());
    }
        
}
