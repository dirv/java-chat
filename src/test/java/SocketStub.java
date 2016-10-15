package test.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStub extends Socket {

    private final String input;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

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
    
    public OutputStream getOutputStream() {
        return output;
    }
    
    public String getOutput() {
        return output.toString();
    }
}
