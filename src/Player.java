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
        this.dish = null;
        
        try {
            BufferedImage original = ImageIO.read(getClass().getResource("/pictures/waitress.png"));

            Image tmp = original.getScaledInstance(70, 70, Image.SCALE_SMOOTH); // scale image
            this.playerImage = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = this.playerImage.createGraphics();
            g2.drawImage(tmp, 0, 0, null);
            g2.dispose();

        } catch (Exception e) {
            e.printStackTrace();
            this.playerImage = null; // fallback to circle
        }
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

    public void drawWaitress(Graphics g) {
        if (this.playerImage != null) {
            g.drawImage(this.playerImage, this.playerX - this.playerImage.getWidth() / 2,
                    this.playerY - this.playerImage.getHeight() / 2, null);
        }
    }

    public void setDish(FoodItem item) {
        this.dish = item;
    }

    public FoodItem getDish() {
        return this.dish;
    }

    public void setPlayerImage(BufferedImage image) {
        this.playerImage = image;
    }

    public Image getPlayerImage() {
        return this.playerImage;
    }
}
