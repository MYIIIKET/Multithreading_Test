
public class Bus extends Thread {
    private int number;

    public Bus(int number) {
        this.number = number;
    }

    public void run(){
        Main.busStops.forEach((stop) -> {
            try {
                stop.put(this);
                System.out.println("Bus number: " + number + " arrived to busstop number: " + stop.number);
                sleep(500);
                System.out.println("Bus number: " + number + " departed from busstop number: " + stop.number);
                stop.take(this);
            } catch (InterruptedException e) {
                System.out.println("catched");
            }
        });

        System.out.println("Bus number: " + number + " is done");
    }
}
