import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CaffeGame extends JPanel implements Runnable {

    // SCREEN SETTINGS

    int rowCount = 12;
    int colCount = 16;
    int tileSize = 48;
    int boardWidth = colCount * tileSize;
    int boardHeight  = rowCount * tileSize;
    Thread gameThread;

    public CaffeGame() {
        setPreferredSize((new Dimension(boardWidth, boardHeight)));
        setBackground(Color.pink);
        this.setDoubleBuffered(true);
        startGameThread();
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(10, 10, 50, 50);
    }

}
