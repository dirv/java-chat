package test.java;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.Socket;

public class SocketStub extends Socket {

    private final String input;

    public SocketStub() {
        this("");
    }
    public SocketStub(String input) {
        this.input = input;
    }
    
    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(input.getBytes());
    }
}
