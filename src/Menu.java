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

        Image burger = new ImageIcon(getClass().getResource("pictures/burger.png")).getImage();
        Image cake = new ImageIcon(getClass().getResource("pictures/cake.png")).getImage();
        Image coffe = new ImageIcon(getClass().getResource("pictures/coffe.png")).getImage();
        Image iceCream = new ImageIcon(getClass().getResource("pictures/icecream.png")).getImage();

        FoodItem item1 = new FoodItem(new JLabel("burger"), burger);
        FoodItem item2 = new FoodItem(new JLabel("cake"), cake);
        FoodItem item3 = new FoodItem(new JLabel("coffe"), coffe);
        FoodItem item4 = new FoodItem(new JLabel("icecream"), iceCream);

        this.menu.add(item1);
        this.menu.add(item2);
        this.menu.add(item3);
        this.menu.add(item4);

    }

    public FoodItem randomFoodItem() {

        int place = random.nextInt(menu.size());
        FoodItem randomDish = menu.get(place);

        return randomDish;
    }

}
