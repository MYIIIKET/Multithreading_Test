import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int amountOfStops = 10;
    private static final int amountOfBuses = 100;

    public static final List<BusStop> busStops = new ArrayList<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(amountOfBuses);
    private static final List<Bus> buses = new ArrayList<>();


    public static void main(String[] args) throws InterruptedException {
        initBusStops();
        runBuses();
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
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
