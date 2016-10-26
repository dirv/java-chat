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
	
    public Hangman(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

	
	public String getHangmanDict() {
		return hangmanDict.get(0);
	}

	public Message startMessage() {
		try {
			messageSender.sendMessage("Welcome to hangman");
			messageSender.sendMessage("_ _ _ _ [8 lives remaining]");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}


	public String wordToUnderscore() {
		// TODO Auto-generated method stub
		return null;
	}

}
