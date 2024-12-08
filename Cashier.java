import java.util.LinkedList;

public class Cashier extends Thread {
    private final LinkedList<Customer> queue;
    private final Object queueLock;

    public Cashier(LinkedList<Customer> queue, Object queueLock) {
        this.queue = queue;
        this.queueLock = queueLock;
    }

    private void checkout(Customer customer) {
        if (customer == null) return;

        long checkoutTime = customer.getCheckoutTime();
        try {
            System.out.println(customer + " starts checking out at " + System.currentTimeMillis());
            Thread.sleep(checkoutTime); // Simulate checkout
            System.out.println(customer + " leaves the store at " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            Customer currentCustomer;
            synchronized (queueLock) {
                while (queue.isEmpty()) {
                    try {
                        queueLock.wait(); // Wait for new customers
                    } catch (InterruptedException e) {
                        return; // Gracefully exit the thread if interrupted
                    }
                }
                currentCustomer = queue.removeFirst(); // Get the next customer
            }
            checkout(currentCustomer);
        }
    }
}
