package dirv.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerSocketStub extends ServerSocket {

    public final int port;

    public ServerSocketStub(int port, List<Socket> sockets) throws IOException {
        this.port = port;
    }
}
