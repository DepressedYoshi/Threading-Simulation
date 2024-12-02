public class Customer extends Thread{
    private static int nextId = 0;
    private int id;
    private long shopTime; // how long in ms is the customner shopping 
    private long enterTime; // when did the cutomer entoer the store
    
    public Customer() {
        this.id = nextId++;
        shopTime = (int)(2000 + Math.random() * 15000);
        enterTime = System.currentTimeMillis();
    }


    @Override
    public String toString() {
        return "Customer: " + id + " (enterTime=" + enterTime % 100_000 + " shoptime = " + shopTime + " ) ";
    }


    /*
     * this method will contnually run in it's own thread
     */
    @Override
    public void run() {
        while (enterTime + shopTime > System.currentTimeMillis()) {
            //they are stuck in the store right now 
        }
        Main.addToQ(this);
        System.out.println(this.toString() + " enters que at " + System.currentTimeMillis());
    }
}