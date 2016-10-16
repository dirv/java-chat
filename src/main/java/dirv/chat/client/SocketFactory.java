package dirv.chat.client;

import java.io.IOException;
import java.net.Socket;

public interface SocketFactory {
    Socket createSocket(String address, int port) throws IOException;
}
