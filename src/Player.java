import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

abstract class Player {

    protected FoodItem dish;
    protected BufferedImage playerImage;

    protected int playerX;
    protected int playerY;

    public Player(int x, int y) {

        this.playerX = x;
        this.playerY = y;
        this.dish = new FoodItem();
    }

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
