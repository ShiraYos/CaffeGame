import java.awt.*;
import javax.swing.*;

public class ProgressBar {

    private JPanel panel = new JPanel(null);
    private JProgressBar bar = new JProgressBar(SwingConstants.VERTICAL);
    private int delay = 200;
    private Timer timer;
    private int counter = 100;
    private boolean timeUp;

    public ProgressBar() {
        bar.setValue(100);
        bar.setForeground(Color.red);
        bar.setMinimum(0);
        bar.setMaximum(100);
        bar.setBounds(0, 0, 10, 50);
        this.timeUp = false;

        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(10, 50));
        panel.add(bar);

    }

    public void startProgressBar() {
        
        counter = 100;

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(delay, e -> {
            counter--;
            bar.setValue(counter);
            if (counter <= 0) {
                this.timeUp = true;
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
        ;
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

    public void resetCounter() {
        this.counter = 100;
        bar.setValue(100);
    }

    public void stopProgressBar() {
        this. timer.stop();
    }

    public boolean isTimeUp() {
        return this.timeUp;
    }

}
