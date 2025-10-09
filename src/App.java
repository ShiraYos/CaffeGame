import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("CaffeGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // int rowCount = 12;
        // int colCount = 16;
        // int tileSize = 48;
        // int boardWidth = colCount * tileSize;
        // int boardHeight = rowCount * tileSize;

        // frame.setSize(boardWidth, boardHeight );

        CaffeGame game = new CaffeGame();
        frame.add(game);

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
