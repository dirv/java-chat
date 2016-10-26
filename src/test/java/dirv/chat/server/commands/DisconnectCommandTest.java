package dirv.chat.server.commands;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dirv.chat.server.commands.Command;
import dirv.chat.server.commands.DisconnectCommand;

public class DisconnectCommandTest extends CommandTest {

	
	private final List<String> users = new ArrayList<String>();

	@Test
	public void removesNewName() throws IOException{
		users.add("Sophie");
		users.add("Test");
//		executeCommand("Sophie\n");
		assertThat(users, not("Sophie"));
	}

	protected Command command() {
		return new DisconnectCommand(users);
	}
}
