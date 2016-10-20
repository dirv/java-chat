package dirv.chat.client;

import dirv.chat.Message;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

import org.junit.Test;

public class DisplayTest {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream pw = new PrintStream(out);
    
    @Test
    public void outputsMessage() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timestamp = formatter.parse("2016-10-16 16:18:30").getTime();
        Message message = new Message(timestamp, "Donald", "Hello, world!");
        new Display(pw).message(message);
        
        assertEquals("[2016-10-16 16:18:30] [Donald] Hello, world!" + LINE_SEPARATOR, out.toString());
    }
    
    @Test
    public void outputsException() {
        Exception ex = new Exception("Test");
        new Display(pw).exception(ex);
        
        assertEquals("An internal error occurred: Test" + LINE_SEPARATOR, out.toString());
    }
    
    @Test
    public void outputsErrorOnConsole() {
        new Display(pw).error("This is an error");
        assertEquals("Error: This is an error" + LINE_SEPARATOR, out.toString());
    }
    
}
