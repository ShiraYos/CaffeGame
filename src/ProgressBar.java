import java.awt.*;
import javax.swing.*;

/**
 * This class presents a countdown progress bar.
 * It is mainly used for the customers.
 */
public class ProgressBar {

    private JPanel panel = new JPanel(null);
    private JProgressBar bar = new JProgressBar(SwingConstants.VERTICAL);
    private int delay;
    private Timer timer;
    private int counter = 100;
    private boolean timeUp;

    /**
     * Constructor - create a new progress bar, set values and layout.
     */
    public ProgressBar() {
        bar.setValue(100);
        bar.setForeground(Color.red);
        bar.setMinimum(0);
        bar.setMaximum(100);
        bar.setBounds(0, 0, 10, 50);
        this.timeUp = false;
        this.delay = 400;

        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(10, 50));
        panel.add(bar);

    }

    /**
     * Start the progress bar countdown from 100.
     *  **with an option to set a different delay.
     */
    public void startProgressBar() {

        counter = 100; // reset counter.

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(delay, e -> {
            counter--; // decrease counter according to the delay.
            bar.setValue(counter);
            if (counter <= 0) {
                this.timeUp = true; // stop the timer if the time is up.
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

    public void stopProgressBar() {
        this.timer.stop();
    }

    public boolean isTimeUp() {
        return this.timeUp;
    }

}
