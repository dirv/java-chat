package dirv.chat.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class HangmanTest {
	
    private MessageSenderStub messageSender = new MessageSenderStub();
    private Hangman hangman = new Hangman(messageSender);

	@Test
	public void replaceLettersWithUnderscores(){
		assertEquals("_ _ _ _", hangman.wordToUnderscore());
	}		

}
