import java.awt.*;

public class Table {
    private final int tableSize = 60; // diameter of table
    private final int rows = 3;
    private final int cols = 4;

    private int[][] tablePositions;

    public Table() {
        tablePositions = new int[rows * cols][2];
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
        g.setColor(new Color(79, 29, 29));
        for (int i = 0; i < tablePositions.length; i++) {
            int x = tablePositions[i][0];
            int y = tablePositions[i][1];
            g.fillOval(x - tableSize / 2, y - tableSize / 2, tableSize, tableSize);
            g.setColor(Color.BLACK);
            g.drawOval(x - tableSize / 2, y - tableSize / 2, tableSize, tableSize);
            g.drawString("T" + (i + 1), x - 10, y + tableSize);
            g.setColor(new Color(79, 29, 29 ));
        }
    }

    public int[][] getTablePositions() {
        return tablePositions;
    }

    public int getTableSize() {
        return tableSize;
    }
}