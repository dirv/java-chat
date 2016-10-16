package dirv.chat.client;

import dirv.chat.Message;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DisplayTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream pw = new PrintStream(out);
    
    private final PrintStream oldOut = System.out;
    
    @Before
    public void before() {
        System.setOut(pw);
    }

    @After
    public void after() {
        System.setOut(oldOut);
    }
    
    @Test
    public void outputsMessageOnConsole() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timestamp = formatter.parse("2016-10-16 16:18:30").getTime();
        Message message = new Message(timestamp, "Donald", "Hello, world!");
        new Display().message(message);
        
        assertEquals("[2016-10-16 16:18:30] [Donald] Hello, world!\n", out.toString());
    }
    
    @Test
    public void outputsExceptionOnConsole() {
        Exception ex = new Exception("Test");
        new Display().exception(ex);
        
        assertEquals("An internal error occurred: Test\n", out.toString());

    }
    
}
