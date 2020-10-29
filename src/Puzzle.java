import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.CropImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import javax.swing.AbstractAction;

import MyButton.MyButton;

import java.awt.event.ActionEvent;

public class Puzzle extends JFrame {

    private JPanel panel;
    private BufferedImage source;
    private BufferedImage resizedImage;
    private Image image;
    private int width, height;
    private final int myWidth = 600;
    private final int numberOfButtons = 16;
    private List<Point> solution;
    private List<MyButton> buttons;
    private MyButton usedButton;

    public Puzzle() {
        compute();

    }

    private void compute() {

        solution = new ArrayList<>();
        solution.add(new Point(0, 0));
        solution.add(new Point(1, 0));
        solution.add(new Point(2, 0));
        solution.add(new Point(3, 0));
        solution.add(new Point(0, 1));
        solution.add(new Point(1, 1));
        solution.add(new Point(2, 1));
        solution.add(new Point(3, 1));
        solution.add(new Point(0, 2));
        solution.add(new Point(1, 2));
        solution.add(new Point(2, 2));
        solution.add(new Point(3, 2));
        solution.add(new Point(0, 3));
        solution.add(new Point(1, 3));
        solution.add(new Point(2, 3));
        solution.add(new Point(3, 3));

        buttons = new ArrayList<>();

        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panel.setLayout(new GridLayout(4, 4, 0, 0)); // rows, cols, horizonatalgap, verticalgap

        try {
            source = loadImage();
            int h = getNewHeight(source.getWidth(), source.getHeight());
            resizedImage = resizeImage(source, myWidth, h, BufferedImage.TYPE_INT_ARGB);
        } catch (IOException ex) {
            System.out.println("Image not found" + ex);
        }

        width = resizedImage.getWidth(null);
        height = resizedImage.getHeight(null);

        add(panel, BorderLayout.CENTER);

        // TODO Loop through x, y and display image/buttons in grid (4, 3)

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                image = createImage(new FilteredImageSource(resizedImage.getSource(),
                        new CropImageFilter(x * width / 4, y * height / 4, (width / 4), height / 4)));

                var button = new MyButton(image);
                button.putClientProperty("position", new Point(x, y));

                if (x == 3 && y == 3) {
                    usedButton = new MyButton();
                    usedButton.setBorderPainted(false);
                    usedButton.setContentAreaFilled(false);
                    usedButton.setUsedButton();
                    usedButton.putClientProperty("position", new Point(x, y));

                } else {
                    buttons.add(button);
                }
            }

        }

        Collections.shuffle(buttons);
        buttons.add(usedButton);

        for (int i = 0; i < numberOfButtons; i++) {
            var btn = buttons.get(i);
            panel.add(btn);
            btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            btn.addActionListener(new ClickAction());
        }

        pack();
        setTitle("Picture Puzzle");
        setResizable(true);
        setLocationRelativeTo(null);

    }

    private int getNewHeight(int w, int h) {
        double ratio = myWidth / (double) w;
        int newHeight = (int) (h * ratio);
        return newHeight;
    }

    private BufferedImage loadImage() throws IOException {
        var myImg = ImageIO.read(new File("src/image.jpg"));
        return myImg;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int w, int h, int type) {
        var resizeImage = new BufferedImage(w, h, type);
        var graphics = resizeImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, w, h, null);
        graphics.dispose();
        return resizeImage;
    }

    private class ClickAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkButton(e);
            checkSolution();
        }

        private void checkButton(ActionEvent e) {
            int i = 0;
            for (MyButton button : buttons) {
                if (button.isUsedButton()) {
                    i = buttons.indexOf(button);
                }
            }
            var button = (JButton) e.getSource();
            int newI = buttons.indexOf(button);

            if ((newI + 1 == i) || (newI - 1 == i) || (newI + 4 == i) || (newI - 4 == i)) {
                Collections.swap(buttons, newI, i);
                updateButtons();
            }

        }

        private void updateButtons() {
            panel.removeAll();
            for (JComponent btn : buttons) {
                panel.add(btn);
            }
            panel.validate();
        }
    }

    private void checkSolution() {
        var current = new ArrayList<Point>();

        for (JComponent btn : buttons) {
            current.add((Point) btn.getClientProperty("position"));
        }

        if (compareList(solution, current)) {
            JOptionPane.showMessageDialog(panel, "Puzzle solved", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    public static boolean compareList(List solution, List current) {
        return solution.toString().contentEquals(current.toString());

    }

    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(() -> {
            var puzzle = new Puzzle();
            puzzle.setVisible(true);

        });
    }

}