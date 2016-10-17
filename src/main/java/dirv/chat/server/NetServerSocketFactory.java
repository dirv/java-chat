package dirv.chat.server;

import java.io.IOException;
import java.net.ServerSocket;

public class NetServerSocketFactory implements ServerSocketFactory {

    @Override
    public ServerSocket buildServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }
}
