import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
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

        Image item1 = new ImageIcon(getClass().getResource("pictures/cookie.png")).getImage();

        FoodItem cookie = new FoodItem(new JLabel("Cookie"), item1);
        FoodItem cookie2 = new FoodItem(new JLabel("Cookie2"), item1);
        FoodItem cookie3 = new FoodItem(new JLabel("Cookie3"), item1);
        FoodItem cookie4 = new FoodItem(new JLabel("Cookie4"), item1);

        this.menu.add(cookie);
        this.menu.add(cookie2);
        this.menu.add(cookie3);
        this.menu.add(cookie4);

    }

    public FoodItem randomFoodItem() {

        int place = random.nextInt(menu.size());
        FoodItem randomDish = menu.get(place);

        return randomDish;
    }

}
