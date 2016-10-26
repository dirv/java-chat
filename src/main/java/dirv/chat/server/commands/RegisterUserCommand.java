package dirv.chat.server.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//import dirv.chat.server.MessageRepository;

public class RegisterUserCommand extends RecognizedCommand {

    private final List<String> users;
//	private MessageRepository messageRepository;

    public RegisterUserCommand(List<String> users
//    		,MessageRepository messageRepository
    		) {
        super("1");
        this.users = users;
//        this.messageRepository = messageRepository;
    }

    @Override
    public void execute(BufferedReader reader, PrintWriter printWriter) throws IOException {
        String user = reader.readLine();

        boolean added = attemptAdd(user);
        if(added) {
//        	messageRepository.receiveMessage(user, "has logged on");
            printWriter.println("OK");
        } else {
            printWriter.println("ERROR");
        }
    }
    
    private boolean attemptAdd(String user) {
        if (user == null) return false;
        user = user.trim();
        if (user.isEmpty()) return false;
        if (users.contains(user)) return false;
        users.add(user);
        return true;
    }

}
