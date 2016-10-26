package dirv.chat.client;

public interface Bot {
    void respondToMessage(String message);
    String getState();
}
