import java.util.ArrayList;
import java.util.LinkedList;

public class Cashier extends Thread {
    private final LinkedList<Customer> queue;
    private final Object queueLock;
    private int laneID;
    private static ArrayList<Customer> finished = new ArrayList<>();
    private final String[] names = {"José", "María", "Luis", "Carmen", "Juan"};


    public Cashier(LinkedList<Customer> queue, Object queueLock, int laneID) {
        this.queue = queue;
        this.queueLock = queueLock;
        this.laneID = laneID;
    }

    /**
     *how many has been chekc out */
    public int getCustomerCount(){
        return finished.size();
    }

    private void checkout(Customer customer) {
        if (customer == null) return;

        long checkoutTime = customer.getCheckoutTime();
        try {
            customer.setStartCTime(System.currentTimeMillis());
            System.out.println("[" + TimeUtil.getCurrentTime() + "] " + names[laneID] + " yells at " + customer + " to start checking out.");
             Thread.sleep(checkoutTime); //They are chatting
            customer.setLeaveTime(TimeUtil.getCurrentTime());
            finished.add(customer);
            System.out.println("[" + TimeUtil.getCurrentTime() + "] " + names[laneID] + " helped " + customer + " finishes checkout.");
                } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printStat(ArrayList<Customer> finalList){
        Main.printStat(finalList);
    }

    @Override
    public void run() {
        while (!queue.isEmpty() || !Main.isCustomerFactoryDone () || !Main.noCustomerShopping(this)) {
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
            checkout(currentCustomer);
        }
        printStat(finished);
        System.exit(69420);
    }
}
