package dirv.chat.server.commands;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DisplayClientListCommandTest extends CommandTest {
    private final List<String> users = new ArrayList<String>();

	@Test
	public void returnEmptyListIfNoUsers() throws IOException {		
		String output = executeCommand("ls users\n");
    
		assertEquals("No users present\r\n", output.toString());
	}
	
	@Test
	public void returnsANameWhenUserPresent() throws IOException {
		users.add("Donald");
		String output = executeCommand("ls users\n");
		assertEquals("Donald\r\n", output.toString());
	}
	
	@Test
	public void returnsMultipleNamesWhenUsersArePresent() throws IOException{
		users.add("Donald");
		users.add("Sophie");
		users.add("Steve");
		users.add("Charlotte");
		users.add("Molly");
		String output = executeCommand("ls users\n");
		assertEquals("Donald\r\nSophie\r\nSteve\r\nCharlotte\r\nMolly\r\n", output.toString());
	}

	protected Command command() {
		return new DisplayClientListCommand(users);
	}

}
