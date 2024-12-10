public class Customer extends Thread {
    private static int nextId = 0;
    private final int id;
    private final long shopTime;
    private final long enterTime;
    private final long checkoutTime;

    public Customer() {
        this.id = nextId++;
        this.shopTime = (int) (2000 + Math.random() * 15_000);
        this.enterTime = System.currentTimeMillis();
        this.checkoutTime = (int) (2000 + Math.random() * 3000);
    }

    public long getCheckoutTime() {
        return checkoutTime;
    }

    @Override
    public String toString() {
        return "Customer" + id + ": (enterTime=" + enterTime % 100_000 + ", shopTime=" + shopTime + ")";
    }

    @Override
    public void run() {
        try {
            Thread.sleep(shopTime); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.addToQ(this);
        System.out.println(this + " enters queue at " + System.currentTimeMillis());
    }
}
