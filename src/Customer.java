import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.List;
import java.util.Random;

public class Customer extends Player {

    protected ProgressBar progressBar;
    protected boolean nextToTable;
    private static Timer customerSpawnTimer;
    private static Random random = new Random();

    public Customer(int x, int y) {
        super(x, y);
        setPlayerImage();
        this.nextToTable = false;
        progressBar = new ProgressBar();
    }

    @Override
    void drawPlayer(Graphics g) {
        if (this.playerImage != null) {
            g.drawImage(this.playerImage, this.playerX, this.playerY, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillOval(playerX, playerY, 50, 50);
        }
    }

    @Override
    void setPlayerImage() {
        try {
            BufferedImage original = ImageIO.read(getClass().getResource("/pictures/waitress2.png"));
            Image tmp = original.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
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
        if (isNextToTable() && dish != null && dish.getPhoto() != null) {
            BufferedImage img = dish.getPhoto();

            g.setColor(Color.white);
            g.fillOval(playerX + 65, playerY - 30, 44, 44);
            g.drawOval(playerX + 65, playerY - 30, 44, 44);

            g.fillRect(playerX + 67, playerY, 20, 10);
            g.drawOval(playerX + 67, playerY, 20, 10);
            g.drawImage(img, playerX + 66, playerY - 28, 40, 40, null);

            if (this.progressBar != null) {
                JPanel barPanel = this.progressBar.getPanel();
                game.add(barPanel);
                barPanel.setBounds(this.getX() + 10, this.getY(), 10, 50);
            }
        }
    }

    public static void startSpawner(List<Customer> customers, int spawnX, int[] possibleY, JPanel panel) {
        if (customerSpawnTimer != null && customerSpawnTimer.isRunning()) return;

        customerSpawnTimer = new Timer(0, null); 
        customerSpawnTimer.addActionListener(e -> {
            
            int y = possibleY[random.nextInt(possibleY.length)];
            Customer newCustomer = new Customer(spawnX, y);
            customers.add(newCustomer);
            panel.repaint();

            
            int nextDelay = 2000 + random.nextInt(4000);
            customerSpawnTimer.setInitialDelay(nextDelay);
            customerSpawnTimer.setDelay(nextDelay);
            customerSpawnTimer.restart();
        });

        int initialDelay = 2000 + random.nextInt(4000);
        customerSpawnTimer.setInitialDelay(initialDelay);
        customerSpawnTimer.setRepeats(false);
        customerSpawnTimer.start();
    }

    public static void stopSpawner() {
        if (customerSpawnTimer != null) {
            customerSpawnTimer.stop();
        }
    }
}
