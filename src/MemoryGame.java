import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.*;

/**
 * This class represents the memory mini game in between stars.
 * Win the game - unlock another item for customers menu.
 */
public class MemoryGame extends JPanel {

    private Menu menu;
    private List<FoodItem> unlockedItems;
    private ArrayList<FoodItem> sequence = new ArrayList<>();
    private ArrayList<ArrayList<FoodItem>> answers;
    private boolean showSequence = true;

    /**
     * Constructor - generate new panel.
     * Create the random sequence and answers.
     */
    public MemoryGame(JFrame parent, Menu menu) {

        // Board settings
        setPreferredSize(new Dimension(600, 400));
        setDoubleBuffered(true);
        setLayout(new BorderLayout());
        setBackground(Color.PINK);

        // Only use unlocked items
        unlockedItems = menu.getUnlockedMenu();

        answers = new ArrayList<>();
        this.menu = menu;
        this.sequence = generateSequence(4); // generate a random sequence for the user to memorize
        answers.add(sequence);
        generateAnswers(); // generate different answers

        // Shuffle the answers list so the correct answer wont be in the same place
        // every time
        Collections.shuffle(answers);

        // Create the answers grid
        JPanel answersJPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        answersJPanel.setBackground(Color.PINK);
        for (ArrayList<FoodItem> seq : answers) {
            JButton btn = createSequenceButton(seq); // add each sequence as a button
            answersJPanel.add(btn);
        }

        add(answersJPanel, BorderLayout.SOUTH);
        // while the main sequence is presented - the answers are not visible
        answersJPanel.setVisible(false);

        new Timer(1000, e -> { // Set a timer for the main sequence visibility
            showSequence = false;
            repaint();
            ((Timer) e.getSource()).stop();
            answersJPanel.setVisible(true); // Present answers panel when done
        }).start();

    }

    // Generate a random sequence
    private ArrayList<FoodItem> generateSequence(int length) {

        ArrayList<FoodItem> generatedList = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            generatedList.add(unlockedItems.get(r.nextInt(unlockedItems.size())));
        }

        return generatedList;
    }

    // Paint sequence on screen.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (showSequence) {
            int x = 190;
            int y = 100;

            g.setColor(Color.WHITE);
            g.fillRect(160, 90, 240, 50);

            g.setColor(Color.BLACK);
            g.drawRect(160, 90, 240, 50);

            for (FoodItem item : sequence) {
                Image tmp = item.getPhoto().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                g.drawImage(tmp, x, y, null);
                x += 50;
            }
        } else {
            repaint();
        }

    }

    // Generate differnt answers.
    private void generateAnswers() {
        for (int i = 0; i < 3; i++) {
            ArrayList<FoodItem> genList = new ArrayList<>();
            genList = generateSequence(4);
            this.answers.add(genList);
        }
    }

    // Each answer is created as a button and added to the layout.
    private JButton createSequenceButton(ArrayList<FoodItem> seq) {
        JButton btn = new JButton();
        btn.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

        for (FoodItem item : seq) {
            ImageIcon icon = new ImageIcon(item.getPhoto().getScaledInstance(40,
                    40, Image.SCALE_SMOOTH));
            JLabel lbl = new JLabel(icon);
            btn.add(lbl);
        }

        btn.setBackground(Color.WHITE);
        btn.addActionListener(e -> handleClick(seq)); // Add click handler to each button.

        return btn;
    }

    /**
     * Check if clicked answer is the correct one.
     */
    private void handleClick(ArrayList<FoodItem> clicked) {

        boolean correctAns = true;

        if (clicked.size() != sequence.size()) {
            correctAns = false;
        } else {
            for (int i = 0; i < sequence.size(); i++) {
                if (clicked.get(i).getFoodID() != sequence.get(i).getFoodID()) {
                    correctAns = false;
                    break;
                }
            }
        }

        if (correctAns) {

            // In case of correct answer - customer unlockes another menu item.
            FoodItem newItem = this.menu.unlockNext();
            if (newItem != null) {
                JOptionPane.showMessageDialog(this, "CORRECT - new item unlocked - "
                        + newItem.getName().getText());
            } else {
                JOptionPane.showMessageDialog(this, "CORRECT!!");
            }

            SwingUtilities.getWindowAncestor(this).dispose();

        } else {
            JOptionPane.showMessageDialog(this, "WRONG");
            SwingUtilities.getWindowAncestor(this).dispose();
        }

    }
}
