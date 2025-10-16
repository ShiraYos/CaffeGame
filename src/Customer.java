import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Customer extends Player {

    protected ProgressBar progressBar;
    protected boolean nextToTable;

    public Customer() {
        super(50, 50);
        setPlayerImage();
        this.nextToTable = false;
        progressBar = new ProgressBar();
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

    public boolean isNextToTable() {
        return this.nextToTable;
    }

    public void setNextToTable(boolean nearTable) {
        this.nextToTable = nearTable;
    }

    void drawBubble(Graphics g) {
        if (dish != null && dish.getPhoto() != null) {
            BufferedImage img = dish.getPhoto();

            g.setColor(Color.white);
            g.fillOval(playerX + 50, playerY - 30, 44, 44);
            g.drawOval(playerX + 50, playerY - 30, 44, 44);

            g.fillRect(playerX + 52, playerY, 20, 10);;
            g.drawOval(playerX + 52, playerY, 20, 10);

            g.drawImage(img, playerX + 51, playerY - 28, 40, 40, null);
        }
    }

}
