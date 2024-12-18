import java.util.LinkedList;

public class CustomerFactory extends Thread {
    private long nextCustomer;
    private final long simTime;
    private final long startTime;
    private volatile boolean done = false;
    private int customerCount = 0;  

    public CustomerFactory(long simTime, LinkedList<Customer> queue, Object queueLock) {
        this.simTime = simTime;
        this.startTime = System.currentTimeMillis();
        this.nextCustomer = getNextTime(100, 1000);
    }

    public boolean isDone(){
        return done;
    }

    private long getNextTime(long min, long max) {
        return System.currentTimeMillis() + (long) (min + Math.random() * (max - min + 1));
    }

    public int getCustomerCount() {
        return customerCount;
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < startTime + simTime) {
            if (System.currentTimeMillis() >= nextCustomer) {
                Customer customer = new Customer();
                customer.start();
                customerCount++;
                System.out.println( "[" + customer.getEnterTime() + "] " + customer + " has entered the store");
                nextCustomer = getNextTime(1000, 10000);
            }
        }
        done = true;
        Main.setCustomerFactoryDone(done);
        System.out.println("[" + TimeUtil.getCurrentTime() + "] ANNOUCEMENT: The DOL is coming after us, we will close now to erase evidence. Please checkout ASAP");
    }
}
