package dirv.chat.server.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DisconnectCommand extends RecognizedCommand {

	private final List<String> users;
	
	protected DisconnectCommand(List<String> users) {
		super("1");
		this.users = users;
	}

	@Override
	public void execute(BufferedReader reader, PrintWriter printWriter) throws IOException {
		
	}

}
