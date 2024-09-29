
// File: Philosopher.java
import java.util.Random;

public class Philosopher implements Runnable {
    private final String name;
    private final Fork leftFork;
    private final Fork rightFork;
    private Table currentTable;
    private final Random random = new Random();

    public Philosopher(String name, Fork leftFork, Fork rightFork, Table initialTable) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.currentTable = initialTable;
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                if (pickUpForks()) {
                    eat();
                    putDownForks();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        Thread.sleep(random.nextInt(10000));
    }

    private boolean pickUpForks() throws InterruptedException {
        if (leftFork.pickup()) {
            Thread.sleep(4000);
            if (rightFork.pickup()) {
                return true;
            } else {
                leftFork.putDown();
            }
        }
        return false;
    }

    private void eat() throws InterruptedException {
        Thread.sleep(random.nextInt(5000));
    }

    private void putDownForks() {
        leftFork.putDown();
        rightFork.putDown();
    }

    public void moveToTable(Table newTable) {
        currentTable.removePhilosopher(this);
        newTable.addPhilosopher(this);
        currentTable = newTable;
    }

    public String getName() {
        return name;
    }

    // fix the compilation error
    public Table getCurrentTable() {
        return currentTable;
    }
}