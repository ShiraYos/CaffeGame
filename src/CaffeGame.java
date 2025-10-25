import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * This class represents the main Caffe game panel.
 */
public class CaffeGame extends JPanel {

    // SCREEN SETTINGS
    int rowCount = 13;
    int colCount = 18;
    int tileSize = 48;
    int boardWidth = colCount * tileSize;
    int boardHeight = rowCount * tileSize;

    MouseHandler mouseH = new MouseHandler();
    Table table;
    Waitress waitress;
    Kitchen kitchen;
    TileManager tileManager;
    Menu menu;

    Customer selectedCustomer = null;
    List<Customer> customers = new ArrayList<>();
    private int spawnX = boardWidth - 120;
    private int[] possibleY = { 100, 200, 300 };

    List<Point> path = new ArrayList<>();
    Timer timer;
    boolean waitressMoving = false;
    boolean customerSelected = false;

    private Timer waitressMoveTimer;
    private List<Point> currentWaitressPath;
    private int waitressTargetIndex;
    private boolean waitressReturning;
    private final double WAITRESS_SPEED = 2.0;
    ScoreSystem scoreSystem = new ScoreSystem();

    /**
     * Constructor - create new game panel and new objects
     * relevant to the game.
     */
    public CaffeGame() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        tileManager = new TileManager(this);
        setDoubleBuffered(true);
        addMouseListener(mouseH);

        timer = new Timer(16, e -> repaint());
        timer.start();

        menu = new Menu();
        table = new Table();
        waitress = new Waitress(50, 500);
        kitchen = new Kitchen();

        Customer.startSpawner(customers, spawnX, possibleY, this, scoreSystem, menu);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g);
        table.calculatePositions(getWidth(), getHeight());
        table.drawTables(g2);
        waitress.drawPlayer(g2);
        kitchen.drawKitchen(g2, getWidth(), getHeight());

        for (Customer c : customers) {
            c.drawPlayer(g2);
            c.drawBubble(g2, kitchen, this);
        }

        customers.removeIf(c -> !c.isVisible());
        scoreSystem.draw(g, getWidth());
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {

            int clickedX = e.getX();
            int clickedY = e.getY();

            // check if customer is clicked
            for (Customer c : customers) {
                Rectangle rect = new Rectangle(c.getX(), c.getY(), 100, 100);
                if (rect.contains(clickedX, clickedY)) {
                    // ignore if customer already seated
                    if (!c.isNextToTable()) {
                        selectedCustomer = c;
                        customerSelected = true;
                    }
                    return;
                }
            }

            // check if table is clicked
            for (int[] pos : table.getTablePositions()) {
                int radius = table.getTableSize() / 2;
                double dist = Math.sqrt(Math.pow(clickedX - pos[0], 2)
                        + Math.pow(clickedY - pos[1], 2));
                if (dist <= radius) {
                    if (customerSelected && selectedCustomer != null
                            && !selectedCustomer.isNextToTable()
                            && !table.isTableOccupied(customers, getMousePosition())) {
                        List<Point> customerPath = createPath(selectedCustomer.getX(),
                                selectedCustomer.getY(), pos[0],
                                pos[1]);
                        startCustomerAnimation(selectedCustomer, customerPath);
                        selectedCustomer = null;
                        customerSelected = false;
                    } else {
                        List<Point> waitressPath = createPath(waitress.getX(),
                                waitress.getY(), pos[0], pos[1]);
                        startWaitressAnimation(waitressPath);

                    }
                    break;
                }
            }

            // check if food item is clicked
            for (FoodItem dish : kitchen.toPrepare) {
                int dishX = dish.getScreenX();
                int dishY = dish.getScreenY();
                int radius = Kitchen.DISHSIZE / 2;

                double dx = clickedX - (dishX + radius);
                double dy = clickedY - (dishY + radius);
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist <= radius) {
                    if (waitress.getDish() == null) {
                        kitchen.toPrepare.remove(dish);
                    } else {
                        kitchen.switchItems(dish, waitress.getDish());
                    }

                    waitress.setDish(dish);
                    repaint();
                    break;
                }
            }
        }
    }

    private void startCustomerAnimation(Customer customer, List<Point> path) {
        Timer moveTimer = new Timer(10, null);
        moveTimer.addActionListener(new ActionListener() {
            private int targetIndex = 1;
            private final double speed = 2.0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (targetIndex >= path.size()) {
                    moveTimer.stop();
                    customer.nextToTable = true;
                    if (customer.getDish() != null) {
                        kitchen.toPrepare.add(customer.getDish());
                        customer.progressBar.startProgressBar();
                    }

                    return;
                }

                Point target = path.get(targetIndex);
                int currentX = customer.getX();
                int currentY = customer.getY();

                double dx = target.x - currentX;
                double dy = target.y - currentY;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= speed) {
                    customer.setPosition(target.x, target.y);
                    targetIndex++;
                } else {
                    double stepX = (dx / distance) * speed;
                    double stepY = (dy / distance) * speed;
                    customer.setPosition((int) (currentX + stepX), (int) (currentY + stepY));
                }

                repaint();
            }
        });
        moveTimer.start();
    }

    private void startWaitressAnimation(List<Point> path) {
        // stop any existing timer to prevent overlaps
        if (waitressMoveTimer != null && waitressMoveTimer.isRunning()) {
            waitressMoveTimer.stop();
        }

        // clone path to avoid reference issues
        currentWaitressPath = new ArrayList<>(path);
        waitressTargetIndex = 1;
        waitressReturning = false;
        waitressMoving = true;

        waitressMoveTimer = new Timer(10, e -> {
            if (currentWaitressPath == null || waitressTargetIndex >= currentWaitressPath.size()) {
                handleWaitressReachedTarget();
                return;
            }

            moveWaitressStep();
        });

        waitressMoveTimer.start();
    }

    private void moveWaitressStep() {
        if (currentWaitressPath == null || waitressTargetIndex >= currentWaitressPath.size()) {
            return;
        }
        Point target = currentWaitressPath.get(waitressTargetIndex);
        int currentX = waitress.getX();
        int currentY = waitress.getY();

        double dx = target.x - currentX;
        double dy = target.y - currentY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= WAITRESS_SPEED) {
            waitress.setPosition(target.x, target.y);
            waitressTargetIndex++;
        } else {
            double stepX = (dx / distance) * WAITRESS_SPEED;
            double stepY = (dy / distance) * WAITRESS_SPEED;
            waitress.setPosition((int) (currentX + stepX), (int) (currentY + stepY));
        }
        repaint();
    }

    private void handleWaitressReachedTarget() {
        for (Customer c : customers) {
            double dist = Math.sqrt(Math.pow(c.getX() - waitress.getX(), 2)
                    + Math.pow(c.getY() - waitress.getY(), 2));

            if (dist < 50 && waitress.getDish() != null) {
                if (c.getDish() != null
                        && c.getDish().getFoodID() == waitress.getDish().getFoodID() 
                        && !c.progressBar.isTimeUp()) {
                    waitress.setDish(null);
                    c.setDish(null);
                    repaint();
                }
            }
        }

        if (!waitressReturning && currentWaitressPath != null) {
            List<Point> reversed = new ArrayList<>();
            for (int i = currentWaitressPath.size() - 2; i >= 0; i--) {
                reversed.add(currentWaitressPath.get(i));
            }

            currentWaitressPath = reversed;
            waitressTargetIndex = 0;
            waitressReturning = true;
        } else {
            waitressMoving = false;
            waitressReturning = false;
            if (waitressMoveTimer != null) {
                waitressMoveTimer.stop();
            }
        }
    }

    private List<Point> createPath(int startX, int startY, int targetX, int targetY) {
        List<Point> path = new ArrayList<>();
        int panelWidth = getWidth();

        int leftSafeX = 50;
        int rightSafeX = panelWidth - 50;

        double leftDist = Math.abs(startX - leftSafeX)
                + Math.abs(startY - (targetY - table.getTableSize() / 2 - 20))
                + Math.abs(leftSafeX - targetX);

        double rightDist = Math.abs(startX - rightSafeX)
                + Math.abs(startY - (targetY - table.getTableSize() / 2 - 20))
                + Math.abs(rightSafeX - targetX);

        int safeX = (leftDist <= rightDist) ? leftSafeX : rightSafeX;

        path.add(new Point(startX, startY));
        path.add(new Point(safeX, startY));
        path.add(new Point(safeX, targetY - table.getTableSize() / 2 - 20));
        path.add(new Point(targetX, targetY - table.getTableSize() / 2 - 20));

        return path;
    }
}