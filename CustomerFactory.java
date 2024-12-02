
public class CustomerFactory extends Thread{

    private long nextCustomer; //time in ms in the future when the nesxt customer walks in 

    public CustomerFactory(Main mainApp){
        nextCustomer = getNextTime(1000, 20000);
    }

    public long getNextTime(long min, long max){
        return System.currentTimeMillis() + (long)(min + Math.random() * (max-min +1));
    }

    @Override
    public void run() {
        while (true) {
            if (nextCustomer < System.currentTimeMillis()) {
                Customer c = new Customer();
                c.start();
                System.out.println(c + " has entered the store");
                nextCustomer = getNextTime(1000, 10000);
            }
        }
    }
}
