package Functions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyButton extends JButton {

    public MyButton() {
        super();
        compute();
    }

    public MyButton(Image image) {
        super(new ImageIcon(image));
        compute();
    }

    private void compute() {
        BorderFactory.createLineBorder(Color.BLUE);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse entered");
            }

            public void mouseExited(MouseEvent e) {
                System.out.println("Mouse exited");
            }
        });

    }

}
