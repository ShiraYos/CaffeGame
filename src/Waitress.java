import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Waitress {
    private int x;
    private int y;
    private BufferedImage image;

    public Waitress(int startX, int startY) {
        x = startX;
        y = startY;

        try {
            BufferedImage original = ImageIO.read(getClass().getResource("/pictures/waitress.png"));

            Image tmp = original.getScaledInstance(70, 70, Image.SCALE_SMOOTH); //scale image
            image = new BufferedImage(70, 70, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = image.createGraphics();
            g2.drawImage(tmp, 0, 0, null);
            g2.dispose();

        } catch (Exception e) {
            e.printStackTrace();
            image = null; // fallback to circle
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void drawWaitress(Graphics g) {
        if (image != null) {
            g.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
        } else {
            g.setColor(Color.RED);
            g.fillOval(x - 10, y - 10, 20, 20);
        }
    }
}
