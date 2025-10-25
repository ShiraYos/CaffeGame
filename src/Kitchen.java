import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This class describes a kitchen object. 
 * Includes the kitchen drawing representation.
 * In addition manages the displayed items according to the user orders.
 */
public class Kitchen {

    ArrayList<FoodItem> toPrepare;
    public static int DISHSIZE = 40;

    public Kitchen() {
        toPrepare = new ArrayList<FoodItem>();
    }

    /**
     * Draw the kitchen panel and prepared items according to customers orders.
     */
    public void drawKitchen(Graphics g, int gameWidth, int gameHeight) {

        // Panel settings 
        int panelX = gameWidth - 650;
        int panelY = gameHeight - 50;
        int panelWidth = 220;
        int panelHeight = 150;

        g.setColor(new Color(200, 200, 200));
        g.fillRect(panelX, panelY, panelWidth, panelHeight);

        g.setColor(Color.BLACK);
        g.drawRect(panelX, panelY, panelWidth, panelHeight);

        int spacing = 5;

        // Draw each prepared item on screen
        for (FoodItem foodItem : toPrepare) {
            BufferedImage image = foodItem.getPhoto();
            if (foodItem.isReady() && image != null) {

                int drawX = panelX + spacing;
                int drawY = panelY;

                // Store the items position (so we can detect clicks later)
                foodItem.setScreenPosition(drawX, drawY);

                g.drawImage(image, drawX, drawY, DISHSIZE, DISHSIZE, null);
                spacing += DISHSIZE + 10;

            }
        }

    }

    /**
     * Start time preparation time of a dish when a customerr is seated.
     */
    public void prepareDish(FoodItem item, CaffeGame game) {
        Timer timer = new Timer(5000, e -> {
            item.setIsReady(true); // Set prepared when timer is done
            game.repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * This allows the user to switch items between the 
     * waitress current dish and a different one from the kitchen ber.
     */
    public void switchItems(FoodItem clicked, FoodItem toSwitch) {
        this.toPrepare.remove(clicked);
        this.toPrepare.add(toSwitch);
    }

    /**
     * Remove a given item from being displayed.
     */
    public void removeDish(FoodItem dish) {
        if (dish != null) {
            toPrepare.remove(dish);
        }
    }

}
