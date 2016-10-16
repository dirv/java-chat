package dirv.chat.test;

import dirv.chat.Clock;

public class ClockStub extends Clock {

    private long now;
    private boolean wasCalled = false;
    
    @Override
    public long now() {
        this.wasCalled = true;
        return now;
    }
    
    public void setNow(long now) {
        this.now = now;
    }
    
    public boolean wasCalled() {
        return wasCalled;
    }
}
