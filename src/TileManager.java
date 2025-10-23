import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class creates the game background.
 * It takes a single tile photo and draw it multiple times to fill the screen.
 */
public class TileManager {

    CaffeGame gamePanel;
    BufferedImage tileImage;

    /**
     * Constructor - get the game panel and create tile image.
     */
    public TileManager(CaffeGame game) {
        this.gamePanel = game;
        getTileImage();

    }

    /**
     * Get tile image from the pictures library.
     */
    public void getTileImage() {

        try {
            tileImage = ImageIO.read(getClass().getResource("/pictures/tile.png"));
        } catch (IOException e) {
            e.printStackTrace();
            tileImage = null;
        }

    }

    /**
     * Draw the single tile image multiple times until it fills the screen.
     */
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
