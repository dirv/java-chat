package dirv.chat.client.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;
import dirv.chat.client.Bot;

public class HangmanGame implements Bot {

    private int lives = 8;
    private final List<Letter> letters;
    private final List<String> badGuesses = new ArrayList<>();
    
    public HangmanGame(String phrase) {
        this.letters = Arrays.asList(phrase.toUpperCase().split(""))
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
        
        letters
            .stream()
            .forEach(l -> l.guess(guess));
        boolean anyFound = letters
                .stream() 
                .anyMatch(Letter::wasGuessed);

        if (!anyFound) {
            if (!badGuesses.contains(guess)) {
                badGuesses.add(guess);
                lives--;
            }
        }
    }

    @Override
    public String getState() {
        List<String> output = letters.stream().map(Letter::toString).collect(Collectors.toList());
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
        return letters.stream().allMatch(Letter::wasGuessed);
    }
}
