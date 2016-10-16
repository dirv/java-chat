package dirv.chat;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Stream;

public class CappedMessageRepository implements MessageRepository {

    private TreeSet<Message> messages = new TreeSet<Message>(new ChronologicalComparator());
    private final Clock clock;
    private final int cap;

    public CappedMessageRepository(Clock clock, int cap) {
        this.clock = clock;
        this.cap = cap;
    }

    @Override
    public void receiveMessage(String user, String message) {
        messages.add(new Message(clock.now(), user, message));
        if(messages.size() > cap) {
            messages.remove(messages.first());
        }
    }

    @Override
    public Stream<Message> getMessagesSince(long timestamp) {
        return messages.stream()
                .filter(m -> m.getTimestamp() > timestamp);
    }
    
    public class ChronologicalComparator implements Comparator<Message> {
        @Override
        public int compare(Message m1, Message m2) {
            return new Long(m1.getTimestamp()).compareTo(m2.getTimestamp());
        }
    }
}
