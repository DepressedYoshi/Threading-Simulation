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


        public static void printStat(ArrayList<Customer> finalist){
            System.out.println("--------------------------------------------STAT--------------------------------------------");
            System.out.println("==========================================Overview==========================================");
        
            int customerCount = customerFactory.getCustomerCount();
            long totalShopTime = 0, totalWaitTime = 0, totalCheckoutTime = 0;
            double totalRevenue = 0;
        
            for (Customer customer : finalist) {
                totalShopTime += customer.getShopTime();
                totalWaitTime += customer.getWaitTime();
                totalCheckoutTime += customer.getCheckoutTime();
                totalRevenue += customer.getSubtotal();
            }
        
            double profit = totalRevenue * Items.getProfit();
            String avgShopTime = TimeUtil.beautifyMili(totalShopTime / customerCount);
            String avgWaitTime = TimeUtil.beautifyMili(totalWaitTime / customerCount);
            String avgCheckoutTime = TimeUtil.beautifyMili(totalCheckoutTime / customerCount);
        
            StringBuilder statReport = new StringBuilder();
            statReport.append("There were ").append(lanes).append(" Cashiers\n")
                      .append("Total Number of Customers Entered: ").append(customerCount).append("\n")
                      .append("Time Store Stayed Open: ").append(TimeUtil.beautifyMili(simTime)).append("\n")
                      .append("Average Shopping Time: ").append(avgShopTime).append("\n")
                      .append("Average Waiting Time: ").append(avgWaitTime).append("\n")
                      .append("Average Checkout Time: ").append(avgCheckoutTime).append("\n")
                      .append("Total Revenue: $").append(String.format("%.2f", totalRevenue)).append("\n")
                      .append("Total Profit: $").append(String.format("%.2f", profit)).append("\n")
                      .append("====================================Customer Activity Log=====================================\n");
        
            System.out.println(statReport);
            printEachCustomerStat(finalist);
        }
        

   
}
