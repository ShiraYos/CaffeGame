import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class MemoryGame extends JPanel {

    private Menu menu;
    private List<FoodItem> unlockedItems;
    private ArrayList<FoodItem> sequence = new ArrayList<>();
    private ArrayList<ArrayList<FoodItem>> answers;
    private boolean showSequence = true;

    public MemoryGame(JFrame parent, Menu menu) {

        setPreferredSize(new Dimension(600, 400));
        setDoubleBuffered(true);
        setLayout(new BorderLayout());
        setBackground(Color.PINK);

        // Only use unlocked items
        unlockedItems = menu.getUnlockedMenu();

        answers = new ArrayList<>();
        this.menu = menu;
        this.sequence = generateSequence(4);
        answers.add(sequence);
        generateAnswers();

        Collections.shuffle(answers);

        JPanel answersJPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        answersJPanel.setBackground(Color.PINK);
        for (ArrayList<FoodItem> seq : answers) {
            JButton btn = createSequenceButton(seq);
            answersJPanel.add(btn);
        }

        add(answersJPanel, BorderLayout.SOUTH);
        answersJPanel.setVisible(false);

        new Timer(1000, e -> {
            showSequence = false;
            repaint();
            ((Timer) e.getSource()).stop();
            answersJPanel.setVisible(true);
        }).start();

    }

    private ArrayList<FoodItem> generateSequence(int length) {

        ArrayList<FoodItem> generatedList = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            generatedList.add(unlockedItems.get(r.nextInt(unlockedItems.size())));
        }

        return generatedList;
    }

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

    private void generateAnswers() {
        for (int i = 0; i < 3; i++) {
            ArrayList<FoodItem> genList = new ArrayList<>();
            genList = generateSequence(4);
            this.answers.add(genList);
        }
    }

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
        btn.addActionListener(e -> handleClick(seq));

        return btn;
    }

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

            FoodItem newItem = this.menu.unlockNext();
            JOptionPane.showMessageDialog(this, "CORRECT - new item unlocked - "
                    + newItem.getName().getText());
            SwingUtilities.getWindowAncestor(this).dispose();

        } else {
            JOptionPane.showMessageDialog(this, "WRONG");
            SwingUtilities.getWindowAncestor(this).dispose();
        }

    }
}
