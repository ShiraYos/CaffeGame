import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CaffeGame extends JPanel implements Runnable, MouseListener {

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
        g.dispose();        


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

}
