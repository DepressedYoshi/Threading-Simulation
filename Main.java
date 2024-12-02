import java.util.PriorityQueue;

public class Main {
    private static PriorityQueue<Customer> queue;
    private static long simTime = 20_000;
    private static long startTime;

    public static void main(String[] args) {
        queue = new PriorityQueue<>();
        startTime = System.currentTimeMillis();

        CustomerFactory customerFactory = new CustomerFactory();
        customerFactory.start();

        while (startTime + simTime > System.currentTimeMillis()) {
            customerFactory.interrupt();
        }
    }

    public static void addToQ(Customer customer){
        queue.add(customer);
    }
}