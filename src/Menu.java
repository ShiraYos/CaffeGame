import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class generates different food items for the menu.
 */
public class Menu {

    private ArrayList<FoodItem> menu;
    private Random random;

    public Menu() {

        this.menu = new ArrayList<FoodItem>();
        random = new Random();
        generateItems();

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

    public FoodItem randomFoodItem() {

        int place = random.nextInt(menu.size());
        FoodItem randomDish = menu.get(place);

        return randomDish;
    }

    public ArrayList<FoodItem> getMenu() {
        return this.menu;
    }

    public void updateMenu(FoodItem item) {
        for (FoodItem current : this.menu) {
            if (current.getFoodID() == item.getFoodID()) {
                current.setIsReady(item.isReady());
            }
        }
    }

}
