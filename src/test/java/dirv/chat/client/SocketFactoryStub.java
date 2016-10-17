package dirv.chat.client;

import java.net.Socket;

import dirv.chat.FlushRequiringSocketStub;
import dirv.chat.SocketStub;

public class SocketFactoryStub implements SocketFactory {

    private String passedAddress;
    private int passedPort;
    private SocketStub lastSocket;
    private String nextResponse = "";

    @Override
    public Socket createSocket(String address, int port) {
        this.passedAddress = address;
        this.passedPort = port;
        return this.lastSocket = new FlushRequiringSocketStub(nextResponse);
    }
    
    public String getPassedAddress() {
        return passedAddress;
    }
    
    public int getPassedPort() {
        return passedPort;
    }
    
    public SocketStub getLastSocket() {
        return lastSocket;
    }

    public void setResponse(String nextResponse) {
        this.nextResponse = nextResponse;
    }
}
