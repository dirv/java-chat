package dirv.chat.server;

import java.io.IOException;
import java.net.ServerSocket;

public interface ServerSocketFactory {
    public ServerSocket buildServerSocket(int port) throws IOException;
}
