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
    private long checkoutTime;
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
    private boolean isKaren;
    
        
    public Customer() {
        this.shoppingList = genShoppingList();
        this.id = nextId++;
        this.shopTime = getShopTime(shoppingList);
        this.enterTime = TimeUtil.getCurrentTime();
        this.name = genName();
        this.isKaren = name.equals("Karen");
        this.checkoutTime = getCheckoutTime(shoppingList);
            if (isKaren) {
            this.checkoutTime += 30_000;
        }
        this.race = genRace();
    }
    

    private String genName() {
        if (Math.random() < 0.2) {
            return "Karen";
        }
        return names[(int)(Math.random() * names.length)];
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
        StringBuilder summary = new StringBuilder();
        summary.append(" information: \n")
           .append("Name: ").append(name)
           .append("Race: ").append(race).append("\n")
           .append("Entered at: ").append(getEnterTime()).append("\n")
           .append("Left at: ").append(getLeaveTime()).append("\n")
           .append("Shop Time: ").append(TimeUtil.beautifyMili(this.shopTime)).append("\n")
           .append("Wait Time: ").append(TimeUtil.beautifyMili(this.getWaitTime())).append("\n")
           .append("Checkout Time: ").append(TimeUtil.beautifyMili(this.getCheckoutTime())).append("\n")
           .append("Items Purchased: ").append(shoppingList.toString()).append("\n")
           .append("Subtotal: ").append(getSubtotal()).append("\n");
        if (isKaren) {
            summary.append("NOTE: This Karen does not like the fact her cashier is Mexican, she started a fight that delayed checkout by 30s\n");
        }

        return this + summary.toString();
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
