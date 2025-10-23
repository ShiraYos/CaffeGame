import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This is an abstract class representing the player object.
 * A player is a customer or  waitress.
 */
abstract class Player {

    protected FoodItem dish;
    protected BufferedImage playerImage;

    protected int playerX;
    protected int playerY;

    /**
     * Constructor - set the player's position on screen.
     */
    public Player(int x, int y) {

        this.playerX = x;
        this.playerY = y;
    }

    /**
     * 
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
