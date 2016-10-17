package dirv.chat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStub extends Socket {

    protected InputStream input;
    protected OutputStream output;
    private boolean wasClosed;

    public SocketStub() {
        this("");
    }
    public SocketStub(String input) {
        this.input = new ByteArrayInputStream(input.getBytes());
        this.output = new ByteArrayOutputStream();
    }

    @Override
    public InputStream getInputStream() {
        return input;
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