package dirv.chat;

public interface ClientFactory {
    Runnable build(String address, int port, String user);
}
