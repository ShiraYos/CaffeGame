import javax.swing.*;

/**
 * This class describes a food item in the menu.
 */
public class FoodItem {

    JLabel dishName;
    ImageIcon dishPicture;

    public FoodItem(JLabel name, ImageIcon pic) {
        this.dishName = name;
        this.dishPicture = pic;
    }

    public JLabel getName() {
        return this.dishName;
    }

    public ImageIcon getPhoto() {
        return this.dishPicture;
    }

    
}
