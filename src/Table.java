import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;


public class Table {
    private final int tableSize = 70;
    private final int rows = 3;
    private final int cols = 4;

    private int[][] tablePositions;
    private BufferedImage tableImage;

    public Table() {
        tablePositions = new int[rows * cols][2];

        // Load image once
        try {
            tableImage = ImageIO.read(getClass().getResource("/pictures/table.png"));
        } catch (IOException e) {
            e.printStackTrace();
            tableImage = null;
        }
    }

    public void calculatePositions(int panelWidth, int panelHeight) {
        int spacingX = panelWidth / (cols + 1);
        int spacingY = panelHeight / (rows + 1);

        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                tablePositions[index][0] = spacingX * (c + 1);
                tablePositions[index][1] = spacingY * (r + 1);
                index++;
            }
        }
    }

    public void drawTables(Graphics g) {
        for (int i = 0; i < tablePositions.length; i++) {
            int x = tablePositions[i][0] - tableSize / 2;
            int y = tablePositions[i][1] - tableSize / 2;

            if (tableImage != null) {
                g.drawImage(tableImage, x, y, tableSize, tableSize, null);
            } else {
                // Fallback if image not found
                g.setColor(new Color(79, 29, 29));
                g.fillOval(x, y, tableSize, tableSize);
                g.setColor(Color.BLACK);
                g.drawOval(x, y, tableSize, tableSize);
            }

            // Label
            g.setColor(Color.BLACK);
            g.drawString("T" + (i + 1), x + tableSize / 2 - 10, y + tableSize + 15);
        }
    }

    public int[][] getTablePositions() {
        return tablePositions;
    }

    public int getTableSize() {
        return tableSize;
    }

    public boolean isTableOccupied(List<Customer> customers, Point p) {
        for (Customer customer : customers) {
            double distance = Math.sqrt(Math.pow(p.getX() - customer.getX(), 2)
                    + Math.pow(p.getY() - customer.getY(), 2));

            if (distance <= 50) {
                return true;
            }
        }
        return false;
    }

}
