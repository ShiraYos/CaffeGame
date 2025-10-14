import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class CaffeGame extends JPanel {

    // SCREEN SETTINGS

    int rowCount = 12;
    int colCount = 16;
    int tileSize = 48;
    int boardWidth = colCount * tileSize;
    int boardHeight = rowCount * tileSize;

    MouseHandler mouseH = new MouseHandler();

    // Set waitress default position
    int waitressX = 100;
    int waitressY = 100;
    int waitresSpeed = 6;
    Image icon;

    Table table;
    Waitress waitress;

    List<Point> path = new ArrayList<>();
    Timer timer;
    boolean isMoving = false;

    public CaffeGame() {
        setPreferredSize((new Dimension(boardWidth, boardHeight)));
        setBackground((new Color(110, 67, 50)));
        
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseH);
        Menu menu = new Menu();
        icon = menu.randomFoodItem().getPhoto();

        timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();

        table = new Table();
        waitress = new Waitress(50, 500); // initial position

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isMoving)
                    return;

                for (int[] pos : table.getTablePositions()) {
                    int radius = table.getTableSize() / 2;
                    double dist = Math.sqrt(Math.pow(e.getX() - pos[0], 2) + Math.pow(e.getY() - pos[1], 2));
                    if (dist <= radius) {
                        createPathToTable(pos[0], pos[1]);
                        startAnimation();
                        break;
                    }
                }
            }
        });
    }

    public void update() {

        if (mouseH.clickedLocation == null)
            return; // no click yet

        double targetX = mouseH.clickedLocation.getX();
        double targetY = mouseH.clickedLocation.getY();

        double directionX = targetX - waitressX;
        double directionY = targetY - waitressY;
        double distance = Math.sqrt(Math.pow(directionX, 2) + Math.pow(directionY, 2));

        if (distance > 5) { // only move if not already close enough
            waitressX += (directionX / distance) * waitresSpeed;
            waitressY += (directionY / distance) * waitresSpeed;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        table.calculatePositions(getWidth(), getHeight());
        table.drawTables(g2);
        waitress.drawWaitress(g2);

        // Draw side panel
        int panelX = getWidth() - 650; 
        int panelY = getHeight()-50;
        int panelWidth = 220;
        int panelHeight = 150;

        g.setColor(new Color(200, 200, 200)); 
        g.fillRect(panelX, panelY, panelWidth, panelHeight);

        g.setColor(Color.BLACK); 
        g.drawRect(panelX, panelY, panelWidth, panelHeight);


    }

    public void createPathToTable(int targetX, int targetY) {
        path.clear();
        int startX = waitress.getX();
        int startY = waitress.getY();
        int panelWidth = getWidth();

        int leftSafeX = 50;
        int rightSafeX = panelWidth - 50;

        // total path length via left side
        double leftDist = Math.abs(startX - leftSafeX)
                + Math.abs(startY - (targetY - table.getTableSize() / 2 - 20))
                + Math.abs(leftSafeX - targetX);

        // via right side
        double rightDist = Math.abs(startX - rightSafeX)
                + Math.abs(startY - (targetY - table.getTableSize() / 2 - 20))
                + Math.abs(rightSafeX - targetX);

        int safeX = (leftDist <= rightDist) ? leftSafeX : rightSafeX;

        // Forward path: initial -> safe side horizontally -> vertical -> table
        path.add(new Point(startX, startY));
        path.add(new Point(safeX, startY));
        path.add(new Point(safeX, targetY - table.getTableSize() / 2 - 20));
        path.add(new Point(targetX, targetY - table.getTableSize() / 2 - 20));

        // Return path: mirror
        path.add(new Point(safeX, targetY - table.getTableSize() / 2 - 20));
        path.add(new Point(safeX, startY));
        path.add(new Point(startX, startY));

    }

    private void startAnimation() {
        if (timer != null && timer.isRunning())
            timer.stop();

        isMoving = true; // disable clicks

        timer = new Timer(10, null);
        timer.addActionListener(new ActionListener() {
            private int targetIndex = 1;
            private double speed = 2.0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (targetIndex >= path.size()) {
                    timer.stop();
                    isMoving = false; // re-enable clicks
                    return;
                }

                Point target = path.get(targetIndex);
                int currentX = waitress.getX();
                int currentY = waitress.getY();

                double dx = target.x - currentX;
                double dy = target.y - currentY;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= speed) {
                    waitress.setPosition(target.x, target.y);
                    targetIndex++;
                } else {
                    double stepX = (dx / distance) * speed;
                    double stepY = (dy / distance) * speed;
                    waitress.setPosition((int) (currentX + stepX), (int) (currentY + stepY));
                }

                repaint();
            }
        });

        timer.start();
    }
}
