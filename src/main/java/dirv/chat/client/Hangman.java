package dirv.chat.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import dirv.chat.Message;

public class Hangman {

	List<String> hangmanDict = new ArrayList<>(Arrays.asList(
			"java",
			"steve",
			"sophie"));
	
	private final MessageSender messageSender;
	
	Message message = new Message(1, "bot", "a");
	
    public Hangman(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

	
	public String getHangmanDict() {
		return hangmanDict.get(0);
	}

	public void startMessage() {
		try {
			messageSender.sendMessage("Welcome to hangman");
			messageSender.sendMessage(wordToUnderscore() + "[8 lives remaining]");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void guestResultMessage() {
		try {
			messageSender.sendMessage(guessCharacter(message) + "[8 lives remaining]");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	


	public String guessCharacter(Message message) {
		String character = message.getMessage();
		String changed = "";
		for (int i = 0; i < getHangmanDict().length(); i++){
			if(getHangmanDict().substring(i, i + 1).equals(character)){
				changed = changed.concat(character);
			} else {
				changed = changed.concat("_ ");
			}
		}
		return changed;
		
	}


	public String wordToUnderscore() {
		String wordToCovert = getHangmanDict();
		String underscores = "";
		for (int i = 0; i < wordToCovert.length(); i ++){
			underscores = underscores.concat("_ ");
		}
		return underscores;
	}

}
