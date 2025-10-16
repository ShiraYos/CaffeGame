import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Customer extends Player {

    ProgressBar timeToBeServed;

    public Customer() {
        super(50, 50);
        setPlayerImage();
        timeToBeServed = new ProgressBar();
    }

    @Override
    void drawPlayer(Graphics g) {
        if (this.playerImage != null) {
            g.drawImage(this.playerImage, this.playerX, this.playerY, null);
        } else {
            // Fallback if image not found

        }

    }

    @Override
    void setPlayerImage() {
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


    void drawBubble(Graphics g) {
        if (dish != null && dish.getPhoto() != null) {
            BufferedImage img = dish.getPhoto();
            g.drawImage(img, playerX + 50, playerY - 30, 40, 40, null);
        }
    }

}
