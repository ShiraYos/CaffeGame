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
    protected boolean isClicked = false;
    protected boolean unlocked;

    private int screenX;
    private int screenY;

    public FoodItem(JLabel name, BufferedImage pic, int id, boolean unlock) {
        this.dishName = name;
        this.dishPicture = pic;
        this.foodID = id;
        this.unlocked = unlock;
    }

    public FoodItem(FoodItem item) {
        this.dishName = item.getName();
        this.dishPicture = item.getPhoto();
        this.foodID = item.foodID;
    }

    public FoodItem(Menu m) {
        FoodItem item = m.randomFoodItem();
        this.dishName = item.dishName;
        this.dishPicture = item.dishPicture;
        this.foodID = item.foodID;
    }

    public void setScreenPosition(int x, int y) {
        this.screenX = x;
        this.screenY = y;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
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

    public void setIsClicked(boolean clicked) {
        this.isClicked = clicked;
    }

    public BufferedImage getPhoto() {
        return this.dishPicture;
    }

    public boolean isUnlocked() {
        return this.unlocked;
    }

    public void setUnlocked(boolean lock) {
        this.unlocked = lock;
    }

}
