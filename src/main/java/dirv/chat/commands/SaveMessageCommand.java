package dirv.chat.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dirv.chat.MessageRepository;

public class SaveMessageCommand extends RecognizedCommand {

    private final MessageRepository messageRepository;
    private final List<String> users;

    public SaveMessageCommand(MessageRepository messageRepository, List<String> users) {
        super("2");
        this.messageRepository = messageRepository;
        this.users = users;
    }

    @Override
    public void execute(BufferedReader reader, PrintWriter printWriter) throws IOException {
        String user = reader.readLine();
        if (!users.contains(user)) {
            printWriter.println("ERROR");
            return;
        }
        String message = reader.readLine();
        messageRepository.receiveMessage(user,  message);
        printWriter.println("OK");
    }
}
