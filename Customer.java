import java.util.ArrayList;

public class Customer extends Thread {

    /*
     * TODO: 
     * Shopping list 
     * race 
     * gender 
     * karen 
     * black 
     * revenue 
     */
    private static int nextId = 0;
    private final int id;
    private final long shopTime;
    private final String enterTime;
    private final long checkoutTime;
    private long enterQueTime;
    private long startCTime;
    private String leaveTime;
    private String name;
    private String race;
    private final String[] names = {
        "Karen", "Marha", "Lucas", "Carmen", "Josh", 
        "Aisha", "Wei", "Hiroshi", "Fatima", "Carlos", 
        "Amara", "Raj", "Liam", "Chen", "Sofia", 
        "Miguel", "Alejandra", "Kwame", "Dae", "Zara", 
        "Emily", "James", "Michael", "Jessica", "Robert"
    };
    private String[] races = {"White", "Asian", "Mexican", "Black", "Other"} ;
    private ArrayList<Items> shoppingList;
    
    public Customer() {
        this.shoppingList = genShoppingList();
        this.id = nextId++;
        this.shopTime = getShopTime(shoppingList);
        this.enterTime = TimeUtil.getCurrentTime();
        this.checkoutTime = getCheckoutTime(shoppingList);
        this.name = genName();
        this.race = genRace();
    }

    private String genName(){
        return names[(int)(Math.random()*20)];
    }
    private String genRace(){
        return races[(int)(Math.random()*5)];
    }

    private  long getShopTime(ArrayList<Items> list){
        long sum =0;
        for (Items items : list) {
            sum +=items.getTime();
        }
        return sum;
    }

    private  long getCheckoutTime(ArrayList<Items> list){
        int totalItem =0;
        for (Items items : list) {
            totalItem +=items.getQ();
        }
        return (long) (totalItem * (200+Math.random()*3000));
    }

    private ArrayList<Items> genShoppingList(){
        ArrayList<Items> answer = new ArrayList<>();
        int length = (int) (1 + Math.random() * 10);
        for (int i = 0; i < length; i++) {
            answer.add(new Items());
        }
        return answer;
    }

    public double getSubtotal(){;
        double sum = 0;
        for (Items items : shoppingList) {
            sum += items.getPrice() * items.getQ();
        }
        return Math.round(sum * 100.0) / 100.0;
    }

    public long getCheckoutTime() {
        return checkoutTime;
    }

    public long getWaitTime(){
        return startCTime - enterQueTime;
    }
    public String getEnterTime(){
        return this.enterTime;
    }
    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }
    public long getShopTime() {
        return shopTime;
    }
    public void setStartCTime(long startCTime) {
        this.startCTime = startCTime;
    }
    public String getLeaveTime() {
        return leaveTime;
    }

    public String printSummary(){        
        String basicInfo = " information: \n" + "Name: " + name + 
                        "\nRaces: " + race +
                        "\nEntered at: " +  getEnterTime() + 
                        "\nLeft at: " +  getLeaveTime() + 
                        "\nShopTime: " +  TimeUtil.beautifyMili(this.shopTime) + 
                        "\nWaitime: " + TimeUtil.beautifyMili(this.getWaitTime()) + 
                        "\nCheckout Time: " + TimeUtil.beautifyMili(this.getCheckoutTime()) +
                        "\nItems Purchased: " + shoppingList.toString() + 
                        "\nSubtotal: " + getSubtotal() + "\n";
        return this + basicInfo;
    }
    @Override
    public String toString() {
        //todo
        return "Customer-" + id;
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
        System.out.println( "[" + TimeUtil.getCurrentTime() + "] " + this + " enters the que");
    }
}
