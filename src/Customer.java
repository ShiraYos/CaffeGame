import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class represents a customer in the game.
 * In addition it manages the customer speech bubble and time bar.
 */
public class Customer extends Player {

    protected ProgressBar progressBar;
    protected ProgressBar timeToLeave;
    protected boolean nextToTable;
    protected boolean dishCooked;
    protected boolean wasServed;
    private boolean visible = true;
    private boolean startTimeToLeave;
    private static Timer customerSpawnTimer;
    private static Random random = new Random();

    ScoreSystem scoreSystem;
    private boolean scoreGiven = false;

    /**
     * Constructor - create a new customer in a given position.
     */
    public Customer(int x, int y, Menu menu, ScoreSystem scoreSystem) {
        super(x, y);
        this.dish = new FoodItem(menu);
        setPlayerImage();

        this.startTimeToLeave = false;
        this.dishCooked = false;
        this.nextToTable = false;
        this.wasServed = false;

        // Set progress bars -
        // One for the time customer waits for his order
        // The other marks the time he leaves the caffe
        timeToLeave = new ProgressBar();
        progressBar = new ProgressBar();
        this.timeToLeave.setDelay(50);

        this.scoreSystem = scoreSystem;
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

            // Generate randon customer image out of three options.
            ArrayList<BufferedImage> photos = new ArrayList<>();

            photos.add(ImageIO.read(getClass().getResource("/pictures/customer1.png")));
            photos.add(ImageIO.read(getClass().getResource("/pictures/customer2.png")));
            photos.add(ImageIO.read(getClass().getResource("/pictures/customer3.png")));

            // Randomly shuffle and pick one
            Collections.shuffle(photos, random);
            BufferedImage chosen = photos.get(0);

            Image tmp = chosen.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            this.playerImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = this.playerImage.createGraphics();
            g2.drawImage(tmp, 0, 0, null);
            g2.dispose();
        } catch (Exception e) {
            e.printStackTrace(); // Fall back in case image does not exist
            this.playerImage = null;
        }
    }

    public boolean isNextToTable() {
        return this.nextToTable;
    }

    public void setNextToTable(boolean nearTable) {
        this.nextToTable = nearTable;
    }

    /**
     * Draw and update the customer's speech bubble according to the game.
     */
    void drawBubble(Graphics g, Kitchen kitchen, CaffeGame game) {

        // Only draw bubble if customer is seated next to table.
        if (isNextToTable()) {
            BufferedImage img = null;
            JPanel barPanel = this.progressBar.getPanel();

            if (dish != null && dish.getPhoto() != null) {
                img = dish.getPhoto();

                // Add progress bar
                if (this.progressBar != null && game != null) {
                    if (barPanel.getParent() != game) {
                        game.add(barPanel); // only add once
                    }
                    barPanel.setBounds(this.getX() + 10, this.getY(), 10, 50);
                }


                // Start dish preparation timer.
                if (!dishCooked) {
                    dishCooked = true;
                    kitchen.prepareDish(this.dish, game);
                }

            }

            // Handle bubble when time to be served is up.
            if (!this.progressBar.isTimeUp() && this.dish == null) {
                try {
                    game.remove(barPanel); // renove time bar from screen.
                    this.wasServed = true;

                    // If customer was served in time - user achieves points.
                    if (wasServed && !scoreGiven) { 
                        scoreSystem.addScore(20);
                        scoreGiven = true;
                    }

                    // Stopping time bar and displaying custommer's comment.
                    this.progressBar.stopProgressBar();
                    img = ImageIO.read(getClass().getResource("/pictures/grin.png"));
                    if (!startTimeToLeave) { // Starting time to leave timer.
                        startTimeToLeave = true;
                        this.timeToLeave.startProgressBar();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // In case customer was not served in time
            } else if (this.progressBar.isTimeUp() && !wasServed) { 
                try {
                    game.remove(barPanel);
                    game.kitchen.removeDish(this.dish);

                    this.dish = null; 
                    // Setting customer's response
                    img = ImageIO.read(getClass().getResource("/pictures/rage.png")); 
                    if (!startTimeToLeave) { // Starting time to leave timer.
                        startTimeToLeave = true;
                        this.timeToLeave.startProgressBar();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            // Remove customer from game panel when time to leave panel is done.
            if (startTimeToLeave) {
                removeCustomer(); 
            }

            // Speach bubble display
            g.setColor(Color.white);
            g.fillOval(playerX + 65, playerY - 30, 44, 44);
            g.drawOval(playerX + 65, playerY - 30, 44, 44);

            g.fillRect(playerX + 67, playerY, 20, 10);
            g.drawOval(playerX + 67, playerY, 20, 10);
            g.drawImage(img, playerX + 66, playerY - 28, 40, 40, null);
        }

    }

    /**
     * 
     */
    public static void startSpawner(List<Customer> customers, int spawnX, int[] possibleY, JPanel panel,
            ScoreSystem scoreSystem, Menu m) {
        if (customerSpawnTimer != null && customerSpawnTimer.isRunning()) {
            return;
        }

        customerSpawnTimer = new Timer(0, null);
        customerSpawnTimer.addActionListener(e -> {

            int y = possibleY[random.nextInt(possibleY.length)];
            Customer newCustomer = new Customer(spawnX, y, m, scoreSystem);
            customers.add(newCustomer);
            panel.repaint();

            int nextDelay = 6000 + random.nextInt(4000);
            customerSpawnTimer.setInitialDelay(nextDelay);
            customerSpawnTimer.setDelay(nextDelay);
            customerSpawnTimer.restart();
        });

        int initialDelay = 5000 + random.nextInt(4000);
        customerSpawnTimer.setInitialDelay(initialDelay);
        customerSpawnTimer.setRepeats(true);
        customerSpawnTimer.start();
    }

    /**
     * Mark a customer for removal when time is up.
     */
    public void removeCustomer() {
        if (startTimeToLeave && timeToLeave.isTimeUp()) {
            markForRemoval();
        }
    }

    public static void stopSpawner() {
        if (customerSpawnTimer != null) {
            customerSpawnTimer.stop();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    /**
     * When time to leave is up - set customer visibility to false 
     * and stop progress bars if they are still running.
     */
    public void markForRemoval() {
        this.visible = false;
        progressBar.stopProgressBar();
        timeToLeave.stopProgressBar();
    }

}