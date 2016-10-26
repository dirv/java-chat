package dirv.chat.server.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DisplayClientListCommand extends RecognizedCommand {

	private List<String> users;
	
	public DisplayClientListCommand(List<String> users) {
		super("4");
		this.users = users;
	}

	@Override
	public void execute(BufferedReader reader, PrintWriter printWriter) throws IOException {
		if (!users.isEmpty()) {
			for (String user : users){
				printWriter.println(user);
			}
			return;
		}
		printWriter.println("No users present");
	}

}
