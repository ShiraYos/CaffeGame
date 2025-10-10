import java.awt.Image;

import javax.swing.*;

/**
 * This class describes a food item in the menu.
 */
public class FoodItem {

    JLabel dishName;
    Image dishPicture;

    public FoodItem(JLabel name, Image pic) {
        this.dishName = name;
        this.dishPicture = pic;
    }

    public JLabel getName() {
        return this.dishName;
    }

    public Image getPhoto() {
        return this.dishPicture;
    }

    
}
