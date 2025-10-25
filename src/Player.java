import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class describes an abstract player.
 * A player can be a customer or a waitress.
 */
abstract class Player {

    protected FoodItem dish;
    protected BufferedImage playerImage;

    protected int playerX;
    protected int playerY;

    /**
     * Create a new player object in a given position.
     */
    public Player(int x, int y) {

        this.playerX = x;
        this.playerY = y;
    }

    /**
     * Set a new position.
     */
    public void setPosition(int x, int y) {
        this.playerX = x;
        this.playerY = y;
    }

    public int getX() {
        return this.playerX;
    }

    public int getY() {
        return this.playerY;
    }

    // Set abstrat method - drawPlayer 
    abstract void drawPlayer(Graphics g);

    public void setDish(FoodItem item) {
        this.dish = item;
    }

    public FoodItem getDish() {
        return this.dish;
    }

    abstract void setPlayerImage();

    public BufferedImage getPlayerImage() {
        return this.playerImage;
    }
}
