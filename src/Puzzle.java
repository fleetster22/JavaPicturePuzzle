import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import Functions.ClickAction;
import Functions.MyButton;

public class Puzzle extends JFrame {

    private JPanel panel;
    private BufferedImage source;
    private BufferedImage resizedImage;
    private Image image;
    private int width, height;
    private final int myWidth = 300;
    private final int numberOfButtons = 12;
    private List<Point> solution;
    private List<MyButton> buttons;

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

        panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panel.setLayout(new GridLayout(4, 3, 0, 0)); // rows, cols, horizonatalgap, verticalgap

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

        // Collections.shuffle(buttons);
        // for (int i = 0; i < numberOfButtons; i++) {
        // var btn = buttons.get(i);
        // panel.add(btn);
        // btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        // btn.addActionListener(new ClickAction());
        // }

        pack();
        setTitle("Picture Puzzle");
        setResizable(false);
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

    private void checkSolution() {
        var current = new ArrayList<Point>();

        // for(JComponent btn : buttons){
        // }

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