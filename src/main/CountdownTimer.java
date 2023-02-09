package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CountdownTimer extends JLabel {

    Font font = new Font("Arial", Font.PLAIN, 30);
    Timer timer;
    int second = 60;
    String ddSecond;
    DecimalFormat dFormat = new DecimalFormat("00");

    public CountdownTimer() {

        this.setFont(font);
        this.setVisible(true);
        this.setBounds(800, 30, 200, 30);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.repaint();
        this.setText("Time: 60");

        countdownTimer();
        timer.start();
    }

    public void countdownTimer() {

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                second--;
                ddSecond = dFormat.format(second);
                CountdownTimer.this.setText("Czas: " + ddSecond);
                if(second==0) {
                    timer.stop();
                }
            }
        });
    }
}
