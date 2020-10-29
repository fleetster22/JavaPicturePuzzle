package MyButton;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyButton extends JButton {
    private boolean isUsedButton;

    public MyButton() {
        super();
        computeMyButton();
    }

    public MyButton(Image image) {
        super(new ImageIcon(image));
        computeMyButton();
    }

    private void computeMyButton() {
        isUsedButton = false;
        BorderFactory.createLineBorder(Color.GRAY);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            }

            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        });

    }

    public boolean isUsedButton() {
        return isUsedButton;
    }

    public void setUsedButton() {
        isUsedButton = true;
    }

}
