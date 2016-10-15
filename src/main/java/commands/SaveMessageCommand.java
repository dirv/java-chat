package main.java.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import main.java.MessageRepository;

public class SaveMessageCommand extends RecognizedCommand {

    private final MessageRepository messageRepository;

    public SaveMessageCommand(MessageRepository messageRepository) {
        super("2");
        this.messageRepository = messageRepository;
    }

    @Override
    public void execute(BufferedReader reader, PrintWriter printWriter) throws IOException {
        String user = reader.readLine();
        String message = reader.readLine();
        messageRepository.receiveMessage(user,  message);
    }

}
