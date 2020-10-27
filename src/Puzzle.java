import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Puzzle extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = -1493106885863084743L;
    private JPanel panel;
    private BufferedImage source;
    private BufferedImage resizedImage;
    private Image image;
    private int width, height;
    private final int myWidth = 300;

    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(() -> {
            var puzzle = new Puzzle();
            puzzle.setVisible(true);

        });
    }

    public Puzzle() {
        compute();

    }

    private void compute() {
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

        width = resizedImage.getWidth();
        height = resizedImage.getHeight();

        add(panel, BorderLayout.CENTER);

        pack();
        setTitle("Picture Puzzle");

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
        graphics.dispose();
        return resizeImage;
    }

}