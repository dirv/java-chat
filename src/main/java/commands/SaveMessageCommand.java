package main.java.commands;

import java.io.BufferedReader;
import java.io.IOException;

import main.java.MessageRepository;

public class SaveMessageCommand extends Command {

    private final MessageRepository messageRepository;

    public SaveMessageCommand(MessageRepository messageRepository) {
        super("2");
        this.messageRepository = messageRepository;
    }
    @Override
    public void execute(BufferedReader reader) throws IOException {
        String user = reader.readLine();
        String message = reader.readLine();
        messageRepository.receiveMessage(user,  message);
    }

}
