package main;

import javax.swing.*;

public class Main extends JFrame{

    private CustomPanel currentPanel;

    public Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Recycling game");

        currentPanel = new MainMenuPanel();
        this.add(currentPanel);

        new Thread(() -> {
            while (true){
                switch (currentPanel.getEvent()) {
                    case GAME_PANEL: {
                        GamePanel gamePanel = new GamePanel();
                        PointCounter pointCounter = new PointCounter();
                        gamePanel.add(pointCounter);
                        gamePanel.startGameThread();
                        replaceContainer(gamePanel);
                        currentPanel = gamePanel;
                        break;
                    }
                    case END_PANEL: {
                        EndPanel endPanel = new EndPanel();
                        replaceContainer(endPanel);
                        currentPanel = endPanel;
                    }
                }
                try {
                    Thread.sleep(5L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void replaceContainer(CustomPanel panel) {
        remove(currentPanel);
        add(panel);
        revalidate();
        repaint();
        pack();
    }


    public static void main(String[] args) {
        new Main();
    }
}