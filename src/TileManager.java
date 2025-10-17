import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileManager {

    CaffeGame gamePanel;
    BufferedImage tileImage;

    public TileManager(CaffeGame game) {
        this.gamePanel = game;
        getTileImage();

    }

    public void getTileImage() {

        try {
            tileImage = ImageIO.read(getClass().getResource("/pictures/tile.png"));
        } catch (IOException e) {
            e.printStackTrace();
            tileImage = null;
        }

    }

    public void draw(Graphics g) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gamePanel.colCount && row < gamePanel.rowCount) {

            g.drawImage(tileImage, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            col++;
            x += gamePanel.tileSize;

            if (col == gamePanel.colCount) {
                col = 0;
                x = 0;
                row++;
                y += gamePanel.tileSize;
            }
        }

    }

}
