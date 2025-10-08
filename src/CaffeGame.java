import java.awt.*;
import javax.swing.*;

public class CaffeGame extends JPanel {

    // SCREEN SETTINGS 

    int rowCount = 12;
    int colCount = 16;
    int tileSize = 48;
    int boardWidth = colCount * tileSize;
    int boardHeigt = rowCount * tileSize;

    public CaffeGame() {
        setPreferredSize((new Dimension(boardWidth, boardHeigt)));
        setBackground(Color.pink);
        this.setDoubleBuffered(true);
    }

}
