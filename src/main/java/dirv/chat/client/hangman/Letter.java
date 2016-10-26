package dirv.chat.client.hangman;

public class Letter {

    private final String letter;
    private boolean wasGuessed;

    public Letter(String letter) {
        this.letter = letter;
    }
    
    public boolean guess(String guess) {
        if (wasGuessed) return false;
        if (guess.equals(letter)) {
            wasGuessed = true;
        }
        return wasGuessed;
    }
    
    public boolean wasGuessed() {
        return wasGuessed;
    }

    public String toString() {
        if (wasGuessed) return letter;
        return "_";
    }

}
