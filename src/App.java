import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * Main - start the game board panel and set frame settings.
 */
public class App {
    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("CaffeGame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        Music bgSound = new Music();
        bgSound.playMusic("src/sounds/bgMusic.wav");

        CaffeGame game = new CaffeGame();
        
        Menu m = new Menu();
        MemoryGame mg = new MemoryGame(frame, m);

        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
