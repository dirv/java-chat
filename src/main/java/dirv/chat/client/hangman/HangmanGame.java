package dirv.chat.client.hangman;

import java.util.Arrays;

import dirv.chat.client.Bot;

public class HangmanGame implements Bot {

    private char[] letters = "DONALD".toCharArray();
    private char[] guesses = "______".toCharArray();
    private int lives = 8;

    public void respondToMessage(String message) {
        if (message.length() > 1) return;
        if (lives == 0) return;

        boolean changed = false;
        char guess = Character.toUpperCase(message.toCharArray()[0]);
        for (int i = 0; i < letters.length; ++i) {
            if (letters[i] == guess) {
                changed = true;
                guesses[i] = guess;
            }
        }
        if (!changed) {
            lives--;
        }
    }

    @Override
    public String getState() {
        String state = "";
        for (char guess : guesses) {
            state += guess + " ";
        }
        state += "(" + lives + " lives left)";
        if (Arrays.equals(guesses, letters)) {
            state += System.lineSeparator() + "Congratulations, you survived!";
        }
        if (lives == 0) {
            state += System.lineSeparator() + "Oh dear, you were hanged!";
        }
        return state;
    }
}
