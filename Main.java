import java.util.LinkedList;

public class Main {
    private static LinkedList<Customer> queue;
    private static final Object queueLock = new Object();
    private static long simTime = 30_000;

    public static void main(String[] args) {
        queue = new LinkedList<>();

        CustomerFactory customerFactory = new CustomerFactory(simTime, queue, queueLock);
        Cashier cashier = new Cashier(queue, queueLock);

        customerFactory.start();
        cashier.start();

        try {
            customerFactory.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

    public static void addToQ(Customer customer) {
        synchronized (queueLock) {
            queue.add(customer);
            queueLock.notify(); // Notify the cashier that a customer is added
        }
    }
}
