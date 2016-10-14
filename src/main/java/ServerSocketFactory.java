package main.java;

import java.io.IOException;
import java.net.ServerSocket;

import test.java.SocketSpy;

public interface ServerSocketFactory {
    public ServerSocket buildServerSocket(int port) throws IOException;
}
