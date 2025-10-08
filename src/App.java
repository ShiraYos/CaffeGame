import javax.swing.JFrame;
public class App {
    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("CaffeGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        int rowCount = 12;
        int colCount = 16;
        int tileSize = 48;
        int boardWidth = colCount * tileSize;
        int boardHeigt = rowCount * tileSize;

        frame.setSize(boardWidth, boardHeigt);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        CaffeGame game = new CaffeGame();
        frame.add(game);
        frame.pack();
    }
}
