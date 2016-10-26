package dirv.chat.client.hangman;

import java.util.Arrays;
import java.util.List;
import java.util.stream.*;
import dirv.chat.client.Bot;

public class HangmanGame implements Bot {

    private char[] letters = "DONALD".toCharArray();
    private char[] guesses = "______".toCharArray();
    private int lives = 8;
    private final List<Letter> lettersList;
    
    public HangmanGame() {
        this.lettersList = Arrays.asList("DONALD".split(""))
                .stream()
                .map(l->new Letter(l))
                .collect(Collectors.toList());
    }

    public void respondToMessage(String message) {
        if (message.length() > 1)
            return;
        if (lives == 0)
            return;
        
        String guess = message.toUpperCase();
        
        lettersList
            .stream()
            .forEach(l -> l.guess(guess));
        boolean anyFound = lettersList
                .stream() 
                .anyMatch(Letter::wasGuessed);

        if (!anyFound) {
            lives--;
        }
    }

    @Override
    public String getState() {
        List<String> output = lettersList.stream().map(Letter::toString).collect(Collectors.toList());
        String state = String.join(" ", output);
        state += " (" + lives + " lives left)";
        if (allGuessed()) {
            state += System.lineSeparator() + "Congratulations, you survived!";
        }
        if (lives == 0) {
            state += System.lineSeparator() + "Oh dear, you were hanged!";
        }
        return state;
    }
    
    private boolean allGuessed() {
        return lettersList.stream().allMatch(Letter::wasGuessed);
    }
}
