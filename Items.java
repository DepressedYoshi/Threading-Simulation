import java.util.Random;

public class Items{
    private String name;
    private double price;
    private static double profitMargin;
    private int quantity;
    private long timeToFind;
    private final String[] groceries = {
        "Apples", "Bananas", "Oranges", "Grapes", "Watermelon", "Mangoes", "Pineapple", "Strawberries", 
        "Blueberries", "Raspberries", "Cherries", "Tomatoes", "Potatoes", "Carrots", "Onions", "Garlic", 
        "Spinach", "Lettuce", "Cabbage", "Broccoli", "Cauliflower", "Peppers", "Cucumbers", "Zucchini", 
        "Eggplant", "Avocados", "Beans", "Lentils", "Rice", "Pasta", "Bread", "Butter", "Cheese", "Milk", 
        "Yogurt", "Eggs", "Chicken", "Beef", "Pork", "Fish", "Shrimp", "Salmon", "Tuna", "Tofu", 
        "Mushrooms", "Corn", "Peas", "Green Beans", "Asparagus", "Beets", "Radishes", "Celery", 
        "Applesauce", "Cereal", "Oatmeal", "Granola", "Peanut Butter", "Jelly", "Honey", "Olive Oil", 
        "Vinegar", "Salt", "Pepper", "Spices", "Herbs", "Sugar", "Flour", "Baking Powder", "Yeast", 
        "Soup", "Canned Tomatoes", "Canned Beans", "Canned Corn"
    };

    public Items (){
        this.name = getRandomGrocery(groceries);
        this.price = 2.99 + Math.random() * 25.99;
        //half of the time 1, half of the time 2 or 3
        this.quantity = (Math.random() < 0.5) ? 1 : (int)(2 + Math.random() * 2);
        Items.profitMargin = 0.65;
        this.timeToFind = (long) (2_000 + Math.random() * 3_000); 
    }

    public long getTime(){
        return this.timeToFind;
    }

    public int getQ(){
        return this.quantity;
    }

    public double getPrice() {
        return price;
    }

    public static double getProfit() {
        return profitMargin;
    }

    public static String getRandomGrocery(String[] groceries) {
        Random random = new Random();
        int index = random.nextInt(groceries.length);
        return groceries[index];
    }

    @Override
    public String toString() {
        return name + "(" + quantity + "@" + Math.round(price * 100.0) / 100.0 + ")";
    }
    
}