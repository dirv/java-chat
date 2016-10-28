package dirv.chat.server.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import dirv.chat.server.MessageRepository;

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
        int length = Integer.parseInt(reader.readLine());
        String message = readStringOfLength(length, reader);
        reader.readLine();
        messageRepository.receiveMessage(user,  message);
        printWriter.println("OK");
    }

    private String readStringOfLength(int length, BufferedReader reader) throws IOException {
        char[] bytes = new char[length];
        reader.read(bytes, 0, length);
        return new String(bytes);
    }
}
