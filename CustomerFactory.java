import java.util.LinkedList;

public class CustomerFactory extends Thread {
    private long nextCustomer;
    private final long simTime;
    private final long startTime;
  

    public CustomerFactory(long simTime, LinkedList<Customer> queue, Object queueLock) {
        this.simTime = simTime;
        this.startTime = System.currentTimeMillis();
        this.nextCustomer = getNextTime(1000, 20000);
    }

    private long getNextTime(long min, long max) {
        return System.currentTimeMillis() + (long) (min + Math.random() * (max - min + 1));
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < startTime + simTime) {
            if (System.currentTimeMillis() >= nextCustomer) {
                Customer customer = new Customer();
                customer.start();
                System.out.println(customer + " has entered the store");
                nextCustomer = getNextTime(1000, 10000);
            }
        }
        System.out.println("Customer Factory DONE!");
    }
}
