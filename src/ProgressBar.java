import java.awt.*;
import javax.swing.*;

public class ProgressBar {

    private JPanel panel = new JPanel(null);
    public JProgressBar bar = new JProgressBar();
    private int delay = 500;
    private Timer timer;
    private int counter = 100;

    public ProgressBar() {
        bar.setValue(100);
        bar.setStringPainted(true);
        bar.setPreferredSize(new Dimension(420, 30));
        panel.add(bar);
        panel.setPreferredSize(new Dimension(420, 30));

    }

    public void startProgressBar() {
        timer = new Timer(delay, e -> {
            counter--;
            bar.setValue(counter);
            if (counter <= 0) {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public JProgressBar getProgressBar() {
        return this.bar;
    }

    public void setDelay(int d) {
        this.delay = d;
    }

    public void setCounter() {
        this.counter = 100;
    }

}
