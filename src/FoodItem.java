import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class describes a food item in the menu.
 */
public class FoodItem {

    JLabel dishName;
    BufferedImage dishPicture;

    public FoodItem(JLabel name, BufferedImage pic) {
        this.dishName = name;
        this.dishPicture = pic;
    }

    public FoodItem(FoodItem item) {
        this.dishName = item.getName();
        this.dishPicture = item.getPhoto();
    }

    public FoodItem(){
        Menu menu = new Menu();
        FoodItem item = menu.randomFoodItem();

        this.dishName = item.getName();
        this.dishPicture = item.getPhoto();
    }

    public JLabel getName() {
        return this.dishName;
    }

    public BufferedImage getPhoto() {
        return this.dishPicture;
    }

    
}
