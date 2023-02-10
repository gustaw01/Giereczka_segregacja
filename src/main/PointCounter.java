package main;

import javax.swing.*;
import java.awt.*;

public class PointCounter extends JLabel {

    Font font = new Font("Arial", Font.PLAIN, 30);
    public PointCounter () {

        this.setFont(font);
        this.setVisible(true);
        this.setBounds(600, 30, 200, 30);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.repaint();
        this.setText("Points: ");
    }
}
