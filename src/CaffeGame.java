import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
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

    Timer timer;

    public CaffeGame() {
        setPreferredSize((new Dimension(boardWidth, boardHeight)));
        setBackground(Color.pink);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseH);
        Menu menu = new Menu();
        icon = menu.randomFoodItem().getPhoto();

        timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();

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

        g2.drawImage(icon, waitressX,waitressY,tileSize,tileSize,null);
        
        // g2.setColor(Color.RED);
        // g2.fillOval(waitressX, waitressY, tileSize, tileSize);
        g2.dispose();

    }


}
