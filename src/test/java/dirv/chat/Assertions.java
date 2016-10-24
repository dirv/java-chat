package dirv.chat;

import static org.junit.Assert.*;

import java.util.List;

public class Assertions {

    private final static String PREFIX = "Message was not as expected: ";
    public static void assertMessageEquals(Message expected, Message actual) {
        if(expected.getTimestamp() != actual.getTimestamp())
            fail(format(PREFIX + "timestamp was ", expected.getTimestamp(), actual.getTimestamp()));
        else if (!expected.getUser().equals(actual.getUser()))
            fail(format(PREFIX + "user was", expected.getUser(), actual.getUser()));
        else if(!expected.getMessage().equals(actual.getMessage()))
            fail(format(PREFIX + "message was", expected.getUser(), actual.getUser()));
    }
    
    public static void assertEqualsLines(List<String> expected, String actual) {
        String allExpected = String.join(System.lineSeparator(), expected)
                + System.lineSeparator();
        assertEquals(allExpected, actual);
    }

    public static void assertEqualsLine(String expected, String actual) {
        assertEquals(expected + System.lineSeparator(), actual);
    }

    private static String format(String prefix, Object expected, Object actual) {
        return prefix + " " + actual + " but expected " + expected;
    }
}
