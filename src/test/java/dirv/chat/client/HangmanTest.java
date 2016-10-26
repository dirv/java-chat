package dirv.chat.client;

import static org.junit.Assert.*;

import java.util.Arrays;

import static dirv.chat.client.Examples.*;

import org.junit.Test;

import dirv.chat.Message;
import dirv.chat.client.ServerListener;

public class HangmanTest {
	
    private MessageSenderStub messageSender = new MessageSenderStub();
    private Hangman hangman = new Hangman(messageSender);
//    private ServerListener serverListener = new ServerListener(messageSender, display);

	@Test
	public void replaceLettersWithUnderscores(){
		assertEquals("_ _ _ _ ", hangman.wordToUnderscore());
	}
	
//	@Test
//	public void testCorrectCharacterEntered() {
//		Message guessA = new Message(1, "Bot", "a");
//		messageSender = new MessageSenderStub(Arrays.asList(guessA));
//		serverListener().run();
//		
//	}

}
