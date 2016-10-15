package test.java;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import main.java.CappedMessageRepository;
import main.java.Message;

public class CappedMessageRepositoryTest {

    private final ClockStub clock = new ClockStub();
    private final CappedMessageRepository repository
     = new CappedMessageRepository(clock, 2);

    @Test
    public void getsSavedMessages() {
        repository.receiveMessage("Donald", "Save me");
        assertEquals(1, messagesSince(0).size());
        assertEquals("Donald", messagesSince(0).get(0).getUser());
        assertEquals("Save me", messagesSince(0).get(0).getMessage());
    }
    
    @Test
    public void savesTimestampOnEachMessage() {
        clock.setNow(123L);
        repository.receiveMessage("Donald", "Save me");
        assertTrue(clock.wasCalled());
        assertEquals(123L, messagesSince(0).get(0).getTimestamp());
    }

    @Test
    public void returnsMessagesInTimeOrder() {
        clock.setNow(200L);
        repository.receiveMessage("Donald", "Goodbye");
        clock.setNow(100L);
        repository.receiveMessage("Donald", "Hello");
        assertEquals(2, messagesSince(0).size());
        assertEquals("Hello", messagesSince(0).get(0).getMessage());
        assertEquals("Goodbye", messagesSince(0).get(1).getMessage());
    }
    
    @Test
    public void removesEarliestMesageWhenCapIsHit() {
        clock.setNow(200L);
        repository.receiveMessage("Donald", "Goodbye");
        clock.setNow(100L);
        repository.receiveMessage("Donald", "Hello");
        clock.setNow(300L);
        repository.receiveMessage("Donald", "x");
        assertEquals(2, messagesSince(0).size());
        assertEquals("Goodbye", messagesSince(0).get(0).getMessage());
        assertEquals("x", messagesSince(0).get(1).getMessage());
        
    }
    
    private List<Message> messagesSince(long timestamp) {
        return repository.getMessagesSince(timestamp).collect(Collectors.toList());
    }
}
