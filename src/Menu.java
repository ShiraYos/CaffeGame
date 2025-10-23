import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class generates different food items for the menu.
 */
public class Menu {

    private ArrayList<FoodItem> menu;
    private ArrayList<FoodItem> unlockedItems;
    private Random random;

    public Menu() {

        this.menu = new ArrayList<FoodItem>();
        this.unlockedItems = new ArrayList<FoodItem>();
        random = new Random();
        generateItems();
        generateUnlockedMenu();

    }

    private void generateItems() {

        try {
            menu.add(new FoodItem(new JLabel("Burger"),
                    ImageIO.read(getClass().getResource("/pictures/burger.png")), 1, false));
            menu.add(new FoodItem(new JLabel("Cake"),
                    ImageIO.read(getClass().getResource("/pictures/cake.png")), 2, false));
            menu.add(new FoodItem(new JLabel("Coffee"),
                    ImageIO.read(getClass().getResource("/pictures/coffee.png")), 3, true));
            menu.add(new FoodItem(new JLabel("IceCream"),
                    ImageIO.read(getClass().getResource("/pictures/icecream.png")), 4, true));
            menu.add(new FoodItem(new JLabel("Orange juice"),
                    ImageIO.read(getClass().getResource("/pictures/orange.png")), 5, true));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void generateUnlockedMenu() {
        if (unlockedItems != null) {
            unlockedItems.clear();
        }
        
        for (FoodItem item : menu) {
            if (item.isUnlocked()) {
                unlockedItems.add(item);
            }
        }
    }

    public FoodItem unlockNext() {
        for (FoodItem item : menu) {
            if (!item.isUnlocked()) {
                item.setUnlocked(true);
                generateUnlockedMenu();
                return item;
            }
        }

        //In case no more locked items
        return null;
    }

    public FoodItem randomFoodItem() {

        int place = random.nextInt(unlockedItems.size());
        FoodItem randomDish = unlockedItems.get(place);

        return randomDish;
    }

    public ArrayList<FoodItem> getMenu() {
        return this.menu;
    }

        public ArrayList<FoodItem> getUnlockedMenu() {
        return this.unlockedItems;
    }

    public void updateMenu(FoodItem item) {
        for (FoodItem current : this.menu) {
            if (current.getFoodID() == item.getFoodID()) {
                current.setIsReady(item.isReady());
            }
        }
    }

}
