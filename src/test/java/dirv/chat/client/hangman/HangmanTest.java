package dirv.chat.client.hangman;

import static org.junit.Assert.*;

import org.junit.Test;

public class HangmanTest {

    private final HangmanGame hangman = new HangmanGame();

    @Test
    public void hasAnInitialState() {
        assertEquals("_ _ _ _ _ _ (8 lives left)", hangman.getState());
    }
    
    @Test
    public void canSetALetter() {
        hangman.respondToMessage("a");
        assertEquals("_ _ _ A _ _ (8 lives left)", hangman.getState());
    }
    
    @Test
    public void canSetMultipleLetters() {
        hangman.respondToMessage("a");
        hangman.respondToMessage("o");
        assertEquals("_ O _ A _ _ (8 lives left)", hangman.getState());
    }
    
    @Test
    public void losesLife() {
        hangman.respondToMessage("q");
        assertEquals("_ _ _ _ _ _ (7 lives left)", hangman.getState());
    }
    
    @Test
    public void onlyRespondsToSingleLetters() {
        hangman.respondToMessage("aa");
        assertEquals("_ _ _ _ _ _ (8 lives left)", hangman.getState());
    }
    
    @Test
    public void stateIncludesWinMessageIfComplete() {
        playAllLetters("adonl");
        String[] state = hangman.getState().split(System.lineSeparator());
        assertEquals(2, state.length);
        assertEquals("D O N A L D (8 lives left)", state[0]);
        assertEquals("Congratulations, you survived!", state[1]);
    }

    @Test
    public void stateIncludesHangedMessageIfGameOver() {
        playAllLetters("tujbzyri");
        String[] state = hangman.getState().split(System.lineSeparator());
        assertEquals(2, state.length);
        assertEquals("_ _ _ _ _ _ (0 lives left)", state[0]);
        assertEquals("Oh dear, you were hanged!", state[1]);
    }

    @Test
    public void cannotPlayMoveIfHanged() {
        playAllLetters("tujbzyrid");
        String[] state = hangman.getState().split(System.lineSeparator());
        assertEquals(2, state.length);
        assertEquals("_ _ _ _ _ _ (0 lives left)", state[0]);
        assertEquals("Oh dear, you were hanged!", state[1]);
    }
    
    private void playAllLetters(String letters) {
        for(String letter : letters.split("")) {
            hangman.respondToMessage(letter);
        }
    }
}
