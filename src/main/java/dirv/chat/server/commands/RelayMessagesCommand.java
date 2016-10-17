package dirv.chat.server.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dirv.chat.server.MessageRepository;

public class RelayMessagesCommand extends RecognizedCommand {

    private final MessageRepository messageRepository;

    public RelayMessagesCommand(MessageRepository messageRepository) {
        super("3");
        this.messageRepository = messageRepository;
    }

    @Override
    public void execute(BufferedReader reader, PrintWriter printWriter) throws IOException {
        long timestamp = Long.parseLong(reader.readLine());
        messageRepository
            .getMessagesSince(timestamp)
            .forEach(m -> printWriter.print(m.asResponseString()));
    }
}
