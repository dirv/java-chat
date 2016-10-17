package dirv.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import dirv.chat.server.ServerSocketFactory;

public class ServerSocketFactoryStub implements ServerSocketFactory {

    public final List<Socket> remainingSockets = new ArrayList<Socket>();
    public final List<Socket> acceptedSockets = new ArrayList<Socket>();
    private int port;

    public int getPort() {
        return port;
    }

    @Override
    public ServerSocket buildServerSocket(int port) throws IOException {
        this.port = port;
        return new ServerSocket() {
            @Override
            public Socket accept() throws IOException {
                if (remainingSockets.isEmpty())
                    return null;
                Socket nextSocket = remainingSockets.remove(0);
                acceptedSockets.add(nextSocket);
                return nextSocket;
            }
        };
    }

    public void addClient(Socket client) {
        remainingSockets.add(client);
    }

}
