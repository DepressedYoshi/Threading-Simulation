import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    private static LinkedList<Customer> queue;
    private static final Object queueLock = new Object();
    private static long simTime = 30_000;
    private static volatile boolean isCustomerFactoryDone = false; // Tracks when CustomerFactory finishes
    static CustomerFactory customerFactory = new CustomerFactory(simTime, queue, queueLock);    
    private static int lanes = 3;
    
            public static void main(String[] args) throws InterruptedException {
                queue = new LinkedList<>();
                Cashier[] cashiers = new Cashier[lanes];

                System.out.println("[" + TimeUtil.getCurrentTime() + "] Yoshi's El Supremo Shein Factory Grocery is Open For Business !!!!!!!");
        
                for (int i = 0; i < lanes; i++) {
                    cashiers[i] = new Cashier(queue, queueLock, i + 1);
                    cashiers[i].start();
                }
                customerFactory.start();
    
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

        private static void printEachCustomerStat(ArrayList<Customer> finalist){
            for (Customer c : finalist) {
                System.out.println(c.printSummary());
            }
        }

        private static long calculateShopTime(ArrayList<Customer> list){
            long sum = 0;
            for (Customer customer : list) {
                sum += customer.getShopTime();
            }

            return sum / customerFactory.getCustomerCount();
        }

        private static long calculateCOutTime(ArrayList<Customer> list){
            long sum = 0;
            for (Customer customer : list) {
                sum += customer.getCheckoutTime();
            }

            return sum / customerFactory.getCustomerCount();
        }
        private static long calculateWaitTime(ArrayList<Customer> list){
            long sum = 0;
            for (Customer customer : list) {
                sum += customer.getWaitTime();
            }

            return sum / customerFactory.getCustomerCount();
        }
        private static double getReveue(ArrayList<Customer> list){
            double sum = 0;
            for (Customer customer : list) {
                sum += customer.getSubtotal();
            }
            return sum;
        }
        private static double getProfit(ArrayList<Customer> list){
            double sum = 0;
            double profitMargin = Items.getProfit();
            for (Customer customer : list) {
                sum += customer.getSubtotal();
            }
            return sum * profitMargin;
        }

    

        public static void printStat(ArrayList<Customer> finalist){
            System.out.println("--------------------------------------------STAT--------------------------------------------");
            System.out.println("=========Overview=========");
            System.out.println("There were " + lanes +" lnes of Cashiers");
            System.out.println("Total Number of Customer Entered: " + customerFactory.getCustomerCount());
            System.out.println("Time Store Stayed Opened: " + TimeUtil.beautifyMili(simTime));
            System.out.println("Average Shopping Time: " + TimeUtil.beautifyMili(calculateShopTime(finalist)));
            System.out.println("Average Waiting Time: " + TimeUtil.beautifyMili(calculateWaitTime(finalist)));
            System.out.println("Average Checkout Time: " + TimeUtil.beautifyMili(calculateCOutTime(finalist)));
            System.out.println("Total Reveunue: $" + getReveue(finalist));
            System.out.println("Total Profit: $" + getProfit(finalist));
            System.out.println("=========Customer Activity Log========="); 
            printEachCustomerStat(finalist);
    }

   
}
