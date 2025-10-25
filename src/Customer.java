import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Customer extends Player {

    protected ProgressBar progressBar;
    protected ProgressBar timeToLeave;
    protected boolean nextToTable;
    protected boolean dishCooked;
    protected boolean wasServed;
    private boolean visible = true;
    private boolean startTimeToLeave;
    private boolean waitingTooLong = false;
    private static Timer customerSpawnTimer;
    private static Random random = new Random();

    ScoreSystem scoreSystem;
    private boolean scoreGiven = false;

    Music customerSound = new Music();

    private long spawnTime;
    private static final long MAX_WAIT_TIME = 8000; // 8 seconds before leaving if not seated

    private boolean isMovingToTable = false;

    public Customer(int x, int y, Menu menu, ScoreSystem scoreSystem) {
        super(x, y);
        this.dish = new FoodItem(menu);
        setPlayerImage();
        this.startTimeToLeave = false;
        this.dishCooked = false;
        this.nextToTable = false;
        this.wasServed = false;
        timeToLeave = new ProgressBar();
        progressBar = new ProgressBar();
        this.timeToLeave.setDelay(50);
        this.scoreSystem = scoreSystem;
        this.spawnTime = System.currentTimeMillis();
    }

    @Override
    void drawPlayer(Graphics g) {
        if (this.playerImage != null) {
            g.drawImage(this.playerImage, this.playerX, this.playerY, null);

            if (!waitingTooLong) {
                customerSound.playSound("src/sounds/customerEntered.wav");
                waitingTooLong = true;
            }

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

    public void setMovingToTable(boolean moving) {
        this.isMovingToTable = moving;
    }

    public boolean isMovingToTable() {
        return this.isMovingToTable;
    }

    void drawBubble(Graphics g, Kitchen kitchen, CaffeGame game) {

        if (!nextToTable && !isMovingToTable) {
            long elapsed = System.currentTimeMillis() - spawnTime;
            if (elapsed > MAX_WAIT_TIME) {
                try {
                    BufferedImage img = ImageIO.read(getClass().getResource("/pictures/rage.png"));
                    if (img != null) {
                        g.drawImage(img, playerX + 66, playerY - 28, 40, 40, null);
                    }
                } catch (Exception e) {
                }
                markForRemoval();
                return;
            }
        }

        if (isNextToTable()) {
            BufferedImage img = null;
            JPanel barPanel = (this.progressBar != null) ? this.progressBar.getPanel() : null;

            try {
                if (dish != null && dish.getPhoto() != null) {
                    img = dish.getPhoto();

                    if (this.progressBar != null && game != null) {
                        if (barPanel != null && barPanel.getParent() != game) {
                            game.add(barPanel);
                        }
                        if (barPanel != null) {
                            barPanel.setBounds(this.getX() + 10, this.getY(), 10, 50);
                        }
                    }

                    if (!dishCooked && kitchen != null) {
                        dishCooked = true;
                        kitchen.prepareDish(this.dish, game);
                    }
                }

                if (progressBar != null && !progressBar.isTimeUp() && this.dish == null) {
                    if (barPanel != null && game != null) {
                        game.remove(barPanel);
                    }
                    this.wasServed = true;
                    if (wasServed && !scoreGiven) {
                        scoreSystem.addScore(20);
                        scoreGiven = true;
                        customerSound.playSound("src/sounds/customerServed.wav");
                    }
                    try {
                        progressBar.stopProgressBar();
                    } catch (NullPointerException ignored) {}
                    img = ImageIO.read(getClass().getResource("/pictures/grin.png"));
                    if (!startTimeToLeave) {
                        startTimeToLeave = true;
                        if (timeToLeave != null) timeToLeave.startProgressBar();
                    }

                } else if (progressBar != null && progressBar.isTimeUp() && !wasServed) {
                    if (barPanel != null && game != null) {
                        game.remove(barPanel);
                    }
                    if (kitchen != null && this.dish != null) {
                        kitchen.removeDish(this.dish);
                    }
                    this.dish = null;
                    img = ImageIO.read(getClass().getResource("/pictures/rage.png"));
                    if (!startTimeToLeave) {
                        startTimeToLeave = true;
                        if (timeToLeave != null) timeToLeave.startProgressBar();
                    }
                }

                if (startTimeToLeave) {
                    removeCustomer();
                }

                g.setColor(Color.white);
                g.fillOval(playerX + 65, playerY - 30, 44, 44);
                g.drawOval(playerX + 65, playerY - 30, 44, 44);

                g.fillRect(playerX + 67, playerY, 20, 10);
                g.drawOval(playerX + 67, playerY, 20, 10);
                if (img != null) {
                    g.drawImage(img, playerX + 66, playerY - 28, 40, 40, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void startSpawner(List<Customer> customers, int spawnX, int[] possibleY, JPanel panel,
            ScoreSystem scoreSystem, Menu m) {
        if (customerSpawnTimer != null && customerSpawnTimer.isRunning()) {
            return;
        }

        customerSpawnTimer = new Timer(0, null);
        customerSpawnTimer.addActionListener(e -> {

            // don't spawn if 5 or more customers are active
            long activeCustomers = customers.stream().filter(Customer::isVisible).count();
            if (activeCustomers < 5) {
                int y = possibleY[random.nextInt(possibleY.length)];
                Customer newCustomer = new Customer(spawnX, y, m, scoreSystem);
                customers.add(newCustomer);
                panel.repaint();
            }

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

    public void removeCustomer() {
        if (startTimeToLeave && timeToLeave != null && timeToLeave.isTimeUp()) {
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

    public void markForRemoval() {
        this.visible = false; 
        progressBar.stopProgressBar(); 
        timeToLeave.stopProgressBar();
    }

}