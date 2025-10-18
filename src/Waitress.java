import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Waitress extends Player {

    public Waitress(int startX, int startY) {

        super(startX, startY);
        setPlayerImage();

    }

    @Override
    void drawPlayer(Graphics g) {
        if (this.getPlayerImage() != null) {
            g.drawImage(this.getPlayerImage(), this.getX() - this.getPlayerImage().getWidth()/2, this.getY() - this.getPlayerImage().getHeight()/2, null);
        } else {
            g.setColor(Color.RED);
            g.fillOval(this.getX() - 10, this.getY() - 10, 20, 20);
        }
    }

    @Override
    void setPlayerImage() {
        try {
            BufferedImage original = ImageIO.read(getClass().getResource("/pictures/waitress2.png"));

            Image tmp = original.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // scale image
            this.playerImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = this.playerImage.createGraphics();
            g2.drawImage(tmp, 0, 0, null);
            g2.dispose();

        } catch (Exception e) {
            e.printStackTrace();
            this.playerImage = null; // fallback to circle
        }
    }
    
}
