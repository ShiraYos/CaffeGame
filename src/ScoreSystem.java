import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ScoreSystem {

    private int score = 0;
    private int targetScore = 0;
    private int displayedScore = 0;

    private int maxScore = 100;
    private int unlockedStars = 0;
    private final int totalStars = 5;

    private Timer animationTimer;
    private BufferedImage starFilled;
    private BufferedImage starGrey;

    private Runnable onStarUnlocked;

    public ScoreSystem() {
        loadStarImages();

        animationTimer = new Timer(15, e -> {
            if (displayedScore < targetScore) {
                displayedScore++;
            } else if (displayedScore > targetScore) {
                displayedScore--;
            }
        });
        animationTimer.start();
    }

    public void setOnStarUnlocked(Runnable onStarUnlocked) {
        this.onStarUnlocked = onStarUnlocked;
    }

    private void loadStarImages() {
        try {
            starFilled = ImageIO.read(getClass().getResource("/pictures/star_filled.png"));
            starGrey = ImageIO.read(getClass().getResource("/pictures/star_grey.png"));
        } catch (Exception e) {
            System.out.println("Could not load star images.");
            e.printStackTrace();
        }
    }

    public void addScore(int amount) {
        targetScore += amount;
        if (targetScore >= maxScore) {
            targetScore -= maxScore;
            score = targetScore;
            unlockStar();
        }
    }

    private void unlockStar() {
        if (unlockedStars < totalStars) {
            unlockedStars++;
            if (onStarUnlocked != null) {
                onStarUnlocked.run();
            }
        }
    }

    public void draw(Graphics g, int panelWidth) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw progress bar
        int barWidth = 200;
        int barHeight = 20;
        int x = panelWidth - barWidth - 30;
        int y = 50;

        g2.setColor(new Color(40, 40, 40, 180));
        g2.fillRoundRect(x, y, barWidth, barHeight, 15, 15);

        float progress = (float) displayedScore / maxScore;
        int fillWidth = (int) (barWidth * progress);

        GradientPaint gradient = new GradientPaint(
                x, y, new Color(223, 82, 134),
                x + fillWidth, y + barHeight, new Color(227, 82, 134)
        );
        g2.setPaint(gradient);
        g2.fillRoundRect(x, y, fillWidth, barHeight, 15, 15);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, barWidth, barHeight, 15, 15);

        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(Color.WHITE);
        g2.drawString(displayedScore +"/" + maxScore, x + 80, y + 15);

        drawStars(g2, x - 15, y - 45);
    }

    private void drawStars(Graphics2D g2, int startX, int startY) {
        int spacing = 45;
        int size = 45;

        for (int i = 0; i < totalStars; i++) {
            BufferedImage img = (i < unlockedStars) ? starFilled : starGrey;
            if (img != null) {
                g2.drawImage(img, startX + i * spacing, startY, size, size, null);
            } else {
                // fallback if images fail
                g2.setColor(i < unlockedStars ? Color.YELLOW : Color.GRAY);
                g2.fillOval(startX + i * spacing, startY, size, size);
            }
        }
    }

    public int getUnlockedStars() {
        return unlockedStars;
    }

    public int getScore() {
        return displayedScore;
    }
}
