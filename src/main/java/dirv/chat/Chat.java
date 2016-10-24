package dirv.chat;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import dirv.chat.client.Client;
import dirv.chat.client.Display;
import dirv.chat.client.MessageSender;
import dirv.chat.client.NetSocketFactory;
import dirv.chat.client.ServerListener;
import dirv.chat.server.CappedMessageRepository;
import dirv.chat.server.NetServerSocketFactory;
import dirv.chat.server.Server;

public class Chat {

    private static String usage = 
            "Usage for server: java -jar chat.jar server <port>   (default is 3000)\n"+
            "Usage for client: java -jar chat.jar <address> <port> <your name>\n"+
            " e.g. java -jar chat 127.0.0.1 3000 Donald\n";
    private static final ServerFactory realServerFactory = new ServerFactory() {
        @Override
        public Runnable build(int port) {
            return new Server(new NetServerSocketFactory(),
                    new ArrayList<String>(),
                    new CappedMessageRepository(new Clock(), 200),
                    port);
        }
    };

    private static final ClientFactory realClientFactory = new ClientFactory() {
        @Override
        public Runnable build(String address, int port, String user) {
            MessageSender messageSender = new MessageSender(new NetSocketFactory(), address, port, user);
            Display display = new Display(System.out);
            return new Client(Executors.newScheduledThreadPool(1),
                    new ServerListener(messageSender, display),
                    messageSender,
                    display,
                    System.in);
        }
    };

    public static void main(String[] args) {
        new Chat(realServerFactory, realClientFactory, System.out).runWith(args);
    }

    private final ServerFactory serverFactory;
    private final ClientFactory clientFactory;
    private final PrintStream out;

    public Chat(ServerFactory serverFactory, ClientFactory clientFactory, PrintStream out) {
        this.serverFactory = serverFactory;
        this.clientFactory = clientFactory;
        this.out = out;
    }

    public void runWith(String[] args) {
        try {
            buildBasedOn(args).run();
        } catch(Exception ex) {
            out.println(usage);
        }
    }
    
    private Runnable buildBasedOn(String[] args) {
        String mode = args[0];
        if (mode.equals("server")) {
            int port = 3000;
            if (args.length > 1)
                port = Integer.parseInt(args[1]);
            return serverFactory.build(port);
        } else if (mode.equals("client")) {
            String address = args[1];
            int port = Integer.parseInt(args[2]);
            String user = args[3];
            return clientFactory.build(address, port, user);
        }
        return null;
    }
}
