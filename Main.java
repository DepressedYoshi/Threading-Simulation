import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    private static LinkedList<Customer> queue;
    private static final Object queueLock = new Object();
    private static long simTime = 30_000;
    private static volatile boolean isCustomerFactoryDone = false; // Tracks when CustomerFactory finishes
    static CustomerFactory customerFactory = new CustomerFactory(simTime, queue, queueLock);
    
        /*
         * - how long is each customer in line for 
        - how does changing the number of cashiers effect wait times 
        - total number of customers
        - how long did customer shop for 
         */
    
    
    
        public static void main(String[] args) throws InterruptedException {
            queue = new LinkedList<>();
            int lanes = 3;
    
            Cashier[] cashiers = new Cashier[lanes];
    
            for (int i = 0; i < lanes; i++) {
                cashiers[i] = new Cashier(queue, queueLock, i + 1);
                cashiers[i].start();
            }
            customerFactory.start();

            System.out.println("finished");

    }

    public static synchronized boolean isCustomerFactoryDone() {
        return isCustomerFactoryDone;
    }
    
    public static synchronized void setCustomerFactoryDone(boolean isCustomerFactoryDone) {
        Main.isCustomerFactoryDone = isCustomerFactoryDone;
    }

    public static synchronized boolean noCustomerShopping(Cashier cashier){
        //check if there are still customer thread running on shopping time and yet enbtered the que 
        return customerFactory.getCustomerCount() <= cashier.getCustomerCount();
    }

    public static synchronized void addToQ(Customer customer) {
        synchronized (queueLock) {
            queue.add(customer);
            queueLock.notify(); // Update the Linked list for the Cashier class
        }
    }

    public static void printStat(ArrayList<Customer> finalist){

    }

   
}
