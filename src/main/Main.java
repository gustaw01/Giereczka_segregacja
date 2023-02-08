package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Giereczka");

        GamePanel gamePanel = new GamePanel();
        CountdownTimer countdownTimer = new CountdownTimer();
        PointCounter pointCounter = new PointCounter();
        gamePanel.add(pointCounter);
        gamePanel.add(countdownTimer);
        window.add(gamePanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}