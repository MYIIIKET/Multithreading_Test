import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BusStop {
    int number;

    final Lock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();

    private static final int stops = 5;
    final Thread[] buses = new Thread[stops];
    private volatile int count;

    public BusStop(int number) {
        this.number = number;
    }

    public void put(Thread t) throws InterruptedException {
        lock.lock();
        try {
            while (count == buses.length)
                notFull.await();
            for (int i = 0; i < stops; i++) {
                if (buses[i] == null) {
                    buses[i] = t;
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void take(Thread t) throws InterruptedException {
        lock.lock();
        for (int i = 0; i < stops; i++) {
            if (buses[i] == t) {
                buses[i] = null;
                break;
            }
        }
        notFull.signal();
        lock.unlock();
    }
}
