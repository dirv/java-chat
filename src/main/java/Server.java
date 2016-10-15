package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.java.commands.Command;
import main.java.commands.RegisterUserCommand;
import main.java.commands.RelayMessagesCommand;
import main.java.commands.SaveMessageCommand;
import main.java.commands.UnknownCommand;

public class Server {

    private final ServerSocketFactory serverSocketFactory;
    private final List<Command> commands = new ArrayList<Command>();
    
    public Server(ServerSocketFactory serverSocketFactory, List<String> users, MessageRepository messageRepository) {
        this.serverSocketFactory = serverSocketFactory;
        commands.add(new RegisterUserCommand(users));
        commands.add(new SaveMessageCommand(messageRepository));
        commands.add(new RelayMessagesCommand(messageRepository));
        commands.add(new UnknownCommand());
    }

    public void startListening() {
        try {
            ServerSocket serverSocket = serverSocketFactory.buildServerSocket(3000);
            Socket socket = serverSocket.accept();
            if (socket != null) {
                try(BufferedReader bufferedReader
                        = new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()))) {
                    try(PrintWriter printWriter
                            = new PrintWriter(
                                    new BufferedWriter(
                                            new OutputStreamWriter(
                                            socket.getOutputStream())))) {
                        String commandType = bufferedReader.readLine();

                        findCommand(commandType).execute(bufferedReader, printWriter);
                    }
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }
    
    private Command findCommand(String commandType) {
        for (Command c : commands) {
            if (c.respondsTo(commandType)) {
                return c;
            }
        }
        return null;
    }
}
