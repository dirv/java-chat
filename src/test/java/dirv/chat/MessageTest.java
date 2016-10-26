package dirv.chat;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageTest {
    
    @Test
    public void returnsUserPortionOfMessage() {
        Message message = new Message(1, "user", "@donald hello...");
        assertEquals("hello...", message.getUserMessage());
    }
    
    @Test
    public void returnsEntireMessageIfNoUser() {
        Message message = new Message(1, "user", "hello Donald");
        assertEquals("hello Donald", message.getUserMessage());
    }

    @Test
    public void returnsEmptyUserMessage() {
        Message message = new Message(1, "user", "@donald");
        assertEquals("", message.getUserMessage());
    }

}
