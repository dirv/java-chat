package dirv.chat.client.hangman;

public class Letter {

    private final String letter;
    private boolean wasGuessed;

    public Letter(String letter) {
        this.letter = letter;
    }
    
    public void guess(String guess) {
        if (guess.equals(letter)) {
            wasGuessed = true;
        }
    }
    
    public boolean wasGuessed() {
        return wasGuessed;
    }

    public String toString() {
        if (wasGuessed) return letter;
        return "_";
    }

}
