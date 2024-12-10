import java.util.LinkedList;

public class Main {
    private static LinkedList<Customer> queue;
    private static final Object queueLock = new Object();
    private static long simTime = 30_000;
    private static volatile boolean isCustomerFactoryDone = false; // Tracks when CustomerFactory finishes



    public static void main(String[] args) throws InterruptedException {
        queue = new LinkedList<>();
        int lanes = 3;

        CustomerFactory customerFactory = new CustomerFactory(simTime, queue, queueLock);
        Cashier[] cashiers = new Cashier[lanes];

        
        for (int i = 0; i < lanes; i++) {
            cashiers[i] = new Cashier(queue, queueLock, i + 1);
            cashiers[i].start();
        }
        customerFactory.start();
    }

    public static synchronized boolean isCustomerFactoryDone() {
        return isCustomerFactoryDone;
    }
 

    public static synchronized void addToQ(Customer customer) {
        synchronized (queueLock) {
            queue.add(customer);
            queueLock.notify(); // Update the Linked list for the Cashier class
        }
    }
}
