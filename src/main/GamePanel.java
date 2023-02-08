package main;

import Entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // screen settings

    public final int tileSize = 32;
    final int maxScreenCol = 32;
    final int maxScreenRow  = 24;
    final int screenWidth = tileSize * maxScreenCol; // 1024px
    final int screenHeight = tileSize * maxScreenRow; // 768px

    int FPS = 60;

    MouseHandler mouseH = new MouseHandler();
    Thread gameThread;
    Player player = new Player(this, mouseH);
    CountdownTimer countdownTimer =  new CountdownTimer();

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseH);
        this.setFocusable(true);
        this.setLayout(null);
        this.add(countdownTimer);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            update();
            repaint();
            drawCount++;

            if(timer >= 1000000000) {
                System.out.println("FPS: " +  drawCount);
                drawCount = 0;
                timer = 0;
            }

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update() {
        player.update();
    }
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
    }
}
