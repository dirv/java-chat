package dirv.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import dirv.chat.commands.Command;
import dirv.chat.commands.RegisterUserCommand;
import dirv.chat.commands.RelayMessagesCommand;
import dirv.chat.commands.SaveMessageCommand;
import dirv.chat.commands.UnknownCommand;

public class Server {

    private final ServerSocketFactory serverSocketFactory;
    private final List<Command> commands;
    private final int port;
    
    public Server(ServerSocketFactory serverSocketFactory, List<String> users, MessageRepository messageRepository, int port) {
        this.serverSocketFactory = serverSocketFactory;
        this.port = port;
        commands = buildCommands(users, messageRepository);
    }
    
    private static List<Command> buildCommands(List<String> users, MessageRepository messageRepository) {
        return Arrays.asList(
                new RegisterUserCommand(users),
                new SaveMessageCommand(messageRepository, users),
                new RelayMessagesCommand(messageRepository),
                new UnknownCommand());
    }

    public void listen() {
        try {
            ServerSocket serverSocket = serverSocketFactory.buildServerSocket(port);
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                handleSocket(socket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }
    
    private void handleSocket(Socket socket) throws IOException {
        try(BufferedReader bufferedReader = bufferedReader(socket);
            PrintWriter printWriter = printWriter(socket)) {

            String commandType = bufferedReader.readLine();
            findCommand(commandType).execute(bufferedReader, printWriter);
        }
    }
    
    private static BufferedReader bufferedReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    private static PrintWriter printWriter(Socket socket) throws IOException {
        return new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())));
        
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
