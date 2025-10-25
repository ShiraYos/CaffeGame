import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class generates different food items for the menu.
 * It includes two menus - the full menu and the one only woth the unlocked
 * items.
 */
public class Menu {

    private ArrayList<FoodItem> menu;
    private ArrayList<FoodItem> unlockedItems;
    private Random random;

    /**
     * Constructor - generate the two menues.
     */
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
                    ImageIO.read(getClass().getResource("/pictures/icecream.png")), 4, false));
            menu.add(new FoodItem(new JLabel("Orange juice"),
                    ImageIO.read(getClass().getResource("/pictures/orange.png")), 5, true));

        } catch (Exception e) { // Fallback in case image doesn't exist
            e.printStackTrace();
        }

    }

    /**
     * Generate the unlocked items menu from the full menu.
     */
    public void generateUnlockedMenu() {
        // Check if unlocked items is empty and clear it if not - so items dont
        // overloop.
        if (unlockedItems != null) {
            unlockedItems.clear();
        }

        for (FoodItem item : menu) {
            if (item.isUnlocked()) {
                unlockedItems.add(item);
            }
        }
    }

    /**
     * Unlock the next locked item in the menu.
     */
    public FoodItem unlockNext() {
        for (FoodItem item : menu) {
            if (!item.isUnlocked()) {
                item.setUnlocked(true);
                generateUnlockedMenu();
                return item;
            }
        }

        // In case no more locked items
        return null;
    }

    /**
     * Get a random food item from the unlocked menu - used to generate customers
     * orders.
     */
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

    /**
     * Update the food preparation according to customers orders.
     */
    public void updateMenu(FoodItem item) {
        for (FoodItem current : this.menu) {
            if (current.getFoodID() == item.getFoodID()) {
                current.setIsReady(item.isReady());
            }
        }
    }

}
