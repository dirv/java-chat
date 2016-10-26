package dirv.chat.server;

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

import dirv.chat.client.Display;
import dirv.chat.server.commands.Command;
import dirv.chat.server.commands.RegisterUserCommand;
import dirv.chat.server.commands.RelayMessagesCommand;
import dirv.chat.server.commands.SaveMessageCommand;
import dirv.chat.server.commands.UnknownCommand;

public class Server implements Runnable {

    private final ServerSocketFactory serverSocketFactory;
    private final List<Command> commands;
    private final Display display;
    private final int port;
    
    public Server(ServerSocketFactory serverSocketFactory,
            List<String> users,
            MessageRepository messageRepository,
            Display display,
            int port) {
        this.serverSocketFactory = serverSocketFactory;
        this.port = port;
        this.display = display;
        commands = buildCommands(users, messageRepository);
    }
    
    private static List<Command> buildCommands(List<String> users, MessageRepository messageRepository) {
        return Arrays.asList(
                new RegisterUserCommand(users),
                new SaveMessageCommand(messageRepository, users),
                new RelayMessagesCommand(messageRepository),
                new UnknownCommand());
    }

    public void run() {
        try {
            ServerSocket serverSocket = serverSocketFactory.buildServerSocket(port);
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                try {
                    handleSocket(socket);
                } catch (IOException ex) {
                    display.exception(ex);
                }
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
