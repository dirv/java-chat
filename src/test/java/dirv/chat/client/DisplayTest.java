package dirv.chat.client;

import dirv.chat.Message;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.ByteArrayOutputStream;

import static dirv.chat.Assertions.*;

import org.junit.Test;

public class DisplayTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream pw = new PrintStream(out);
    
    @Test
    public void outputsMessage() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timestamp = formatter.parse("2016-10-16 16:18:30").getTime();
        Message message = new Message(timestamp, "Donald", "Hello, world!");
        new Display(pw).message(message);
        
        assertEqualsLine("[2016-10-16 16:18:30] [Donald] Hello, world!", out.toString());
    }
    
    @Test
    public void outputsException() {
        Exception ex = new Exception("Test");
        new Display(pw).exception(ex);
        
        assertEqualsLine("An internal error occurred: Test", out.toString());
    }
    
    @Test
    public void outputsErrorOnConsole() {
        new Display(pw).error("This is an error");
        assertEqualsLine("Error: This is an error", out.toString());
    }
    
}
