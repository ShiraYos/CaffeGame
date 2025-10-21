import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Customer extends Player {

    protected ProgressBar progressBar;
    protected boolean nextToTable;
    protected boolean dishCooked;
    protected boolean wasServed;

    public Customer(int x, int y) {
        super(x, y);
        this.dish = new FoodItem();
        setPlayerImage();
        this.dishCooked = false;
        this.nextToTable = false;;
        this.wasServed = false;
        progressBar = new ProgressBar();
    }

    @Override
    void drawPlayer(Graphics g) {
        if (this.playerImage != null) {
            g.drawImage(this.playerImage, this.playerX, this.playerY, null);
        } else {
            // fallback if image not found
            g.setColor(Color.BLUE);
            g.fillOval(playerX, playerY, 50, 50);
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
            this.playerImage = null;
        }
    }

    public boolean isNextToTable() {
        return this.nextToTable;
    }

    public void setNextToTable(boolean nearTable) {
        this.nextToTable = nearTable;
    }

    void drawBubble(Graphics g, Kitchen kitchen, CaffeGame game) {

        if (isNextToTable()) {
            BufferedImage img = null;
            JPanel barPanel = this.progressBar.getPanel();

            if (dish != null && dish.getPhoto() != null) {
                img = dish.getPhoto();

                // Add progress bar

                if (this.progressBar != null) {
                    game.add(barPanel);
                    barPanel.setBounds(
                            this.getX() + 10,
                            this.getY(),
                            10, 50);

                }

                if (!dishCooked) {
                    dishCooked = true;
                    kitchen.prepareDish(this.dish, game);
                }

            }

            if (!this.progressBar.isTimeUp() && this.dish == null) {
                try {
                    game.remove(barPanel);
                    this.wasServed = true;
                    this.progressBar.stopProgressBar();
                    img = ImageIO.read(getClass().getResource("/pictures/grin.png"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (this.progressBar.isTimeUp() && !wasServed) {
                try {
                    game.remove(barPanel);
                    this.dish = null;
                    img = ImageIO.read(getClass().getResource("/pictures/rage.png"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            g.setColor(Color.white);
            g.fillOval(playerX + 65, playerY - 30, 44, 44);
            g.drawOval(playerX + 65, playerY - 30, 44, 44);

            g.fillRect(playerX + 67, playerY, 20, 10);
            g.drawOval(playerX + 67, playerY, 20, 10);
            g.drawImage(img, playerX + 66, playerY - 28, 40, 40, null);
        }

    }
}
