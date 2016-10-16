package dirv.chat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStub extends Socket {

    private final String input;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private boolean wasClosed;

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
    
    @Override
    public void close() throws IOException {
        this.wasClosed = true;
    }
    
    public OutputStream getOutputStream() {
        return output;
    }
    
    public String getOutput() {
        return output.toString();
    }
    
    public boolean getWasClosed() {
        return wasClosed;
    }
}