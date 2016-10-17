package dirv.chat;

public interface ServerFactory {
    Runnable build(int port);
}
