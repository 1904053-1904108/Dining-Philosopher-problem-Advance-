import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiningPhilosophersSimulation {
    private final Table[] tables = new Table[6];
    private final List<Philosopher> allPhilosophers = new ArrayList<>();
    private final Random random = new Random();

    public DiningPhilosophersSimulation() {
        for (int i = 0; i < 6; i++) {
            tables[i] = new Table();
        }
        initializePhilosophers();
    }

    private void initializePhilosophers() {
        for (int i = 0; i < 25; i++) {
            int tableIndex = i / 5;
            int seatIndex = i % 5;
            Table table = tables[tableIndex];
            Fork leftFork = table.getLeftFork(seatIndex);
            Fork rightFork = table.getRightFork(seatIndex);
            Philosopher philosopher = new Philosopher(String.valueOf((char) ('A' + i)), leftFork, rightFork, table);
            allPhilosophers.add(philosopher);
            table.addPhilosopher(philosopher);
        }
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        String lastMovedPhilosopher = "";

        for (Philosopher philosopher : allPhilosophers) {
            new Thread(philosopher).start();
        }

        while (!tables[5].isDeadlocked()) {
            for (int i = 0; i < 5; i++) {
                if (tables[i].isDeadlocked()) {
                    Philosopher philosopherToMove = selectPhilosopherToMove(tables[i]);
                    movePhilosopher(philosopherToMove);
                    lastMovedPhilosopher = philosopherToMove.getName();
                }
            }
            try {
                Thread.sleep(100); // Small delay to prevent busy-waiting
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Simulation completed in " + (endTime - startTime) + " ms");
        System.out.println("Last moved philosopher: " + lastMovedPhilosopher);
    }

    private Philosopher selectPhilosopherToMove(Table table) {
        // For simplicity, we're selecting a random philosopher.
        // In a more sophisticated simulation, you might want to choose based on certain
        // criteria.
        List<Philosopher> tablePhilosophers = new ArrayList<>();
        for (Philosopher p : allPhilosophers) {
            if (p.getCurrentTable() == table) {
                tablePhilosophers.add(p);
            }
        }
        return tablePhilosophers.get(random.nextInt(tablePhilosophers.size()));
    }

    private void movePhilosopher(Philosopher philosopher) {
        Table destination = tables[5]; // The 6th table
        philosopher.moveToTable(destination);
    }

    public static void main(String[] args) {
        DiningPhilosophersSimulation simulation = new DiningPhilosophersSimulation();
        simulation.run();
    }
}