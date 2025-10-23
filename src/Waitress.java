import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * This class represents a waitress player.
 */
public class Waitress extends Player {

    /**
     * Constructor - create a new waitress in a given position and set player's image.
     */
    public Waitress(int startX, int startY) {

        super(startX, startY);
        this.dish = null;
        setPlayerImage();
    }

    @Override
    void drawPlayer(Graphics g) {
        if (this.getPlayerImage() != null) {

            int drawX = this.getX() - this.getPlayerImage().getWidth() / 2;
            int drawY = this.getY() - this.getPlayerImage().getHeight() / 2;
            g.drawImage(this.getPlayerImage(), drawX, drawY, null);

            if (this.dish != null && this.dish.getPhoto() != null) {
                g.drawImage(this.dish.getPhoto(), drawX + 65, drawY + 22, null);
            }

        } else { // Fallback in case image is null.
            g.setColor(Color.RED);
            g.fillOval(this.getX(), this.getY(), 20, 20);
        }
    }

    @Override
    void setPlayerImage() {
        try {
            BufferedImage original = 
                ImageIO.read(getClass().getResource("/pictures/waitress2.png"));

            Image tmp = original.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // scale image
            this.playerImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = this.playerImage.createGraphics();
            g2.drawImage(tmp, 0, 0, null);
            g2.dispose();

        } catch (Exception e) {
            e.printStackTrace();
            this.playerImage = null;
        }
    }

}