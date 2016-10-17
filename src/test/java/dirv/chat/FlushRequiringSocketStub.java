package dirv.chat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class FlushRequiringSocketStub extends SocketStub {

    private boolean triggered = false;

    public FlushRequiringSocketStub() {
        this("");
    }
    public FlushRequiringSocketStub(String input) {
        this.input = new HeldUntilTriggeredInputStream(input.getBytes());
        this.output = new InputTriggeringOutputStream();
    }
    
    public class HeldUntilTriggeredInputStream extends ByteArrayInputStream {

        public HeldUntilTriggeredInputStream(byte[] buf) {
            super(buf);
        }
        
        @Override
        public int read(byte[] b, int off, int len) {
            if (!triggered)
                throw new RuntimeException("Please flush before accessing this stream");
            
            return super.read(b, off, len);
        }
    }
    
    public class InputTriggeringOutputStream extends ByteArrayOutputStream {

        @Override
        public void write(byte[] b, int off, int len) {
            triggered = true;
            super.write(b, off, len);
        }
    }
}