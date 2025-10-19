import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class describes a food item in the menu.
 */
public class FoodItem {

    protected JLabel dishName;
    protected BufferedImage dishPicture;
    protected int foodID;
    protected boolean isReady = false;

    public FoodItem(JLabel name, BufferedImage pic, int id) {
        this.dishName = name;
        this.dishPicture = pic;
        this.foodID = id;
    }

    public FoodItem(FoodItem item) {
        this.dishName = item.getName();
        this.dishPicture = item.getPhoto();
        this.foodID = item.foodID;
    }

    public FoodItem() {
        Menu menu = new Menu();
        FoodItem item = menu.randomFoodItem();

        this.dishName = item.dishName;
        this.dishPicture = item.dishPicture;
        this.foodID = item.foodID;
    }

    public JLabel getName() {
        return this.dishName;
    }

    public int getFoodID() {
        return this.foodID;
    }

    public boolean isReady() {
        return this.isReady;
    }

    public void setIsReady(boolean ready) {
        this.isReady = ready;
    }

    public BufferedImage getPhoto() {
        return this.dishPicture;
    }

}
