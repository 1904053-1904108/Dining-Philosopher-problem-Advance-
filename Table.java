
// File: Table.java
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
        if (philosophers.size() != 5) {
            return false; // Can't be deadlocked if the table isn't full
        }

        boolean[] forksHeld = new boolean[5];

        // First, check if exactly one fork is held between each pair of philosophers
        for (int i = 0; i < 5; i++) {
            if (forks[i].pickup()) {
                forksHeld[i] = false;
                forks[i].putDown(); // Put it back down immediately
            } else {
                forksHeld[i] = true;
            }
        }

        // Now check if it's a circular wait (deadlock)
        boolean allTrue = true;
        boolean allFalse = true;
        for (boolean held : forksHeld) {
            allTrue &= held;
            allFalse &= !held;
        }

        // If all forks are held or all forks are available, it's not a deadlock
        return !(allTrue || allFalse);
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