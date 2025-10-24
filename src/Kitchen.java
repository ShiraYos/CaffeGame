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

    public void drawKitchen(Graphics g, int gameWidth, int gameHeight) {

        int panelX = gameWidth - 650;
        int panelY = gameHeight - 50;
        int panelWidth = 220;
        int panelHeight = 150;

        g.setColor(new Color(200, 200, 200));
        g.fillRect(panelX, panelY, panelWidth, panelHeight);

        g.setColor(Color.BLACK);
        g.drawRect(panelX, panelY, panelWidth, panelHeight);

        int spacing = 5;

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

    public void prepareDish(FoodItem item, CaffeGame game) {
        Timer timer = new Timer(5000, e -> {
            item.setIsReady(true);
            game.repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void switchItems(FoodItem clicked, FoodItem toSwitch) {
        this.toPrepare.remove(clicked);
        this.toPrepare.add(toSwitch);
    }

    public void removeDish(FoodItem dish) {
        if (dish != null) {
            toPrepare.remove(dish);
        }
    }

}
