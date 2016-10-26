package dirv.chat.client;

public class BotFactorySpy implements BotFactory {

    private FakeBot botSpy;

    @Override
    public Bot buildBot() {
        return this.botSpy = new FakeBot();
    }
    
    public FakeBot getBot() {
        return botSpy;
    }

}
