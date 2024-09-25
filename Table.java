import java.util.ArrayList;
import java.util.List;

public class Table {
    private final Fork[] forks = new Fork[5];
    private final List<Philosopher> philosophers = new ArrayList<>();

    public Table() {
        for (int i = 0; i < 5; i++) {
            forks[i] = new Fork();
        }
    }

    public synchronized void addPhilosopher(Philosopher philosopher) {
        philosophers.add(philosopher);
    }

    public synchronized void removePhilosopher(Philosopher philosopher) {
        philosophers.remove(philosopher);
    }

    public synchronized boolean isDeadlocked() {
        // This is a simplified deadlock detection.
        // In a real scenario, you'd need to check if each philosopher
        // is holding one fork and waiting for the other.
        return philosophers.size() == 5;
    }

    public synchronized boolean isFull() {
        return philosophers.size() == 5;
    }

    public synchronized Fork getLeftFork(int index) {
        return forks[index];
    }

    public synchronized Fork getRightFork(int index) {
        return forks[(index + 1) % 5];
    }
}