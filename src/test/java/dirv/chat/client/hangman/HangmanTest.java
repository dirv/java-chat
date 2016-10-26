package dirv.chat.client.hangman;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class HangmanTest {

    private HangmanGame hangman = new HangmanGame("DONALD");

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
    
    @Test
    public void choosesWordFromAList() {
        hangman = new HangmanGame("JAVA");
        playAllLetters("jav");
        String[] state = hangman.getState().split(System.lineSeparator());
        assertEquals(2, state.length);
        assertEquals("J A V A (8 lives left)", state[0]);
        assertEquals("Congratulations, you survived!", state[1]);
    }
    
    @Test
    public void capitalizesWord() {
        hangman = new HangmanGame("donald");
        playAllLetters("donal");
        assertThat(hangman.getState(), containsString("D O N A L D"));
        
    }

    @Test
    public void cannotGuessTheSameLetterTwiceWhenItIsCorrect() {
        hangman.respondToMessage("a");
        hangman.respondToMessage("a");
        String[] state = hangman.getState().split(System.lineSeparator());
        assertEquals("_ _ _ A _ _ (8 lives left)", state[0]);
    }

    @Test
    public void cannotGuessTheSameLetterTwiceWhenItIsIncorrect() {
        hangman.respondToMessage("q");
        hangman.respondToMessage("q");
        String[] state = hangman.getState().split(System.lineSeparator());
        assertEquals("_ _ _ _ _ _ (7 lives left)", state[0]);
    }
    
    private void playAllLetters(String letters) {
        for(String letter : letters.split("")) {
            hangman.respondToMessage(letter);
        }
    }
}
