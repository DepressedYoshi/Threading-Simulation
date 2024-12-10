import java.util.LinkedList;

public class Cashier extends Thread {
    private final LinkedList<Customer> queue;
    private final Object queueLock;
    private int laneID;

    public Cashier(LinkedList<Customer> queue, Object queueLock, int laneID) {
        this.queue = queue;
        this.queueLock = queueLock;
        this.laneID = laneID;
    }

    private void checkout(Customer customer) {
        if (customer == null) return;

        long checkoutTime = customer.getCheckoutTime();
        try {
            System.out.println("Cashier " + laneID + " starts checking out " + customer + " at " + System.currentTimeMillis());
            Thread.sleep(checkoutTime); //They are chatting
            System.out.println(customer + " leaves the store at " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            Customer currentCustomer;
            /* the synchrinized bracket will constantly seek if there is a Customer
            While the Que is empty, Wait for new customers.
            When new customer is in que, there will be an update.*/
            synchronized (queueLock) {
                while (queue.isEmpty()) {
                    try {
                        queueLock.wait();
                    } catch (InterruptedException e) {
                        return; // Java Syntax force me to do this  
                    }
                }
                currentCustomer = queue.removeFirst(); 
            }
            // Exit the loop if no more customers will be added
            if (queue.isEmpty() && Main.isCustomerFactoryDone()) {
                System.out.println("Cashier: All customers processed. Shutting down.");
                break; 
            }
            System.out.println(queue.isEmpty() + " " + Main.isCustomerFactoryDone());
            checkout(currentCustomer);
        }
    }
}
