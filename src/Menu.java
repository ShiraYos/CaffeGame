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
    Random random;

    public Menu() {

        this.menu = new ArrayList<FoodItem>();
        random = new Random();
        generateItems();

    }

    private void generateItems() {

        try {
            menu.add(new FoodItem(new JLabel("Burger"), ImageIO.read(getClass().getResource("/pictures/burger.png"))));
            menu.add(new FoodItem(new JLabel("Cake"), ImageIO.read(getClass().getResource("/pictures/cake.png"))));
            menu.add(new FoodItem(new JLabel("Coffee"), ImageIO.read(getClass().getResource("/pictures/coffee.png"))));
            menu.add(new FoodItem(new JLabel("IceCream"), ImageIO.read(getClass().getResource("/pictures/iceCream.png"))));
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public FoodItem randomFoodItem() {

        int place = random.nextInt(menu.size());
        FoodItem randomDish = menu.get(place);

        return randomDish;
    }

}
