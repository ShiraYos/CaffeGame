import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Kitchen {

    private Menu menu;

    public Kitchen() {
        menu = new Menu();

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

        ArrayList<FoodItem> items = this.menu.getMenu();
        int spacing = 5;

        for (FoodItem foodItem : items) {

            BufferedImage image = foodItem.getPhoto();
            if (image != null) {
                g.drawImage(image, panelX + spacing, panelY, 40, 40, null);
                spacing += 40;
            }
        }

    }

}
