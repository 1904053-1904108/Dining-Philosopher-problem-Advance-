import java.util.concurrent.atomic.AtomicBoolean;

public class Fork {
    private AtomicBoolean available = new AtomicBoolean(true);
    // atomic boolean ensures thread safety

    public boolean pickup() {
        return available.compareAndSet(true, false);
    }// initialized as true (false upon pickup)

    public void putDown() {
        available.set(true);
    }
}