import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final int amountOfStops = 10;
    private static final int amountOfBuses = 100;

    public static final List<BusStop> busStops = new ArrayList<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(amountOfBuses);
    private static final List<Bus> buses = new ArrayList<>();


    public static void main(String[] args) {
        initBusStops();
        runBuses();
        startObserver();
    }

    private static void startObserver() {
        Observer observer = new Observer();
        observer.setDaemon(true);
        observer.start();
    }

    private static class Observer extends Thread {
        public void run() {
            while (true) {
                boolean anyIsAlive = false;
                for (int i = 0; i < amountOfBuses; i++) {
                    if (buses.get(i).isAlive()) {
                        anyIsAlive = true;
                    }
                }
                if (!anyIsAlive) {
                    executor.shutdown();
                }
            }
        }
    }

    private static void runBuses() {
        for (int i = 0; i < amountOfBuses; i++) {
            Bus bus = new Bus(i);
            buses.add(bus);
            executor.execute(bus);
        }
    }

    private static void initBusStops() {
        for (int i = 0; i < amountOfStops; i++) {
            busStops.add(new BusStop(i));
        }
    }
}
