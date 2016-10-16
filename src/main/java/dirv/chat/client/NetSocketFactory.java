package dirv.chat.client;

import java.io.IOException;
import java.net.Socket;

public class NetSocketFactory implements SocketFactory {

    @Override
    public Socket createSocket(String address, int port) throws IOException {
        return new Socket(address, port);
    }

}
