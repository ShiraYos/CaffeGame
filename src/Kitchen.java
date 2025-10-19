import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Kitchen {

    ArrayList<FoodItem> toPrepare;

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
                g.drawImage(image, panelX + spacing, panelY, 40, 40, null);
                spacing += 40;
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

}
