public class Customer extends Thread {
    private static int nextId = 0;
    private final int id;
    private final long shopTime;
    private final long enterTime;
    private final long checkoutTime;
    private long enterQueTime;
    private long startCTime;
    private long leaveTime;

    public Customer() {
        this.id = nextId++;
        this.shopTime = (int) (2000 + Math.random() * 15_000);
        this.enterTime = System.currentTimeMillis();
        this.checkoutTime = (int) (2000 + Math.random() * 3000);
    }

    public long getCheckoutTime() {
        return checkoutTime;
    }

    public long getWaitTime(){
        return startCTime - enterQueTime;
    }

    public void setLeaveTime(long leaveTime) {
        this.leaveTime = leaveTime;
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
        this.enterQueTime = System.currentTimeMillis();
        System.out.println(this + " enters que at " + enterQueTime);
    }
}
