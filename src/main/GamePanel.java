package main;

import Entity.Container;
import Entity.Trash;
import Entity.Player;
import Entity.Pointer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GamePanel extends JPanel implements Runnable{

    // screen settings

    public final int tileSize = TRASH_SIZE;
    final int maxScreenCol = TRASH_SIZE;
    final int maxScreenRow  = 24;
    final int screenWidth = tileSize * maxScreenCol; // 1024px
    final int screenHeight = tileSize * maxScreenRow; // 768px

    int score = 0;

    int FPS = 60;

    MouseHandler mouseH = new MouseHandler();
    Thread gameThread;
    Player player = new Player(mouseH, screenWidth / 2, screenHeight - 160, 160, 160);
    List<Trash> trashes = Collections.synchronizedList(new ArrayList<>()); // lista bezpiecznia dla wielu wątków
    List<Container> containers = new ArrayList<>();
    CountdownTimer countdownTimer =  new CountdownTimer();

    Pointer pointer;

    private final static int END_Y = 25; //pozycja y celownika
    private final static int TRASH_SIZE = 32; // rozmiar odpadu

    public GamePanel() {

        initPanel();
        initGameVariables();
    }

    private void initGameVariables() {
        containers.add(new Container(1, 100, 160, 160, "glass"));
        containers.add(new Container((screenWidth / 2) - 50, 100, 160, 160, "paper"));
        containers.add(new Container(screenWidth - 161, 100, 160, 160, "plastic"));
        createNewTrash();
        pointer = new Pointer(mouseH, player.getX() + player.getWidth(), player.getY(), 0, END_Y);
    }

    private void createNewTrash() {
        trashes.add(new Trash(mouseH, player.getX() + player.getWidth(), player.getY(), TRASH_SIZE, TRASH_SIZE));
    }

    private void initPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
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
        pointer.update();
        trashes.forEach(Trash::update);
        addScoreIfTrashAtDestination();
        throwTrash();
        moveContainer();
    }

    private void moveContainer() {
        for (Container container : containers) {
            if (isContainerOffScreen(container)) {
                container.changeDirection();
            }
            container.move();
        }
    }

    private boolean isContainerOffScreen(Container container) {
        return container.getX() <= 0 || container.getX() + container.getWidth() >= screenWidth;
    }

    private void addScoreIfTrashAtDestination() {
        var toDelete = trashes.stream()
                .filter(Trash::isDestroyed) //wybiera tylko elementy które dotarły do punktu docelowego
                .peek(this::ifScoredAddPoints) //dla każdego z nich wykonuje metode
                .collect(Collectors.toList()); // tworzy z nich liste

        trashes.removeAll(toDelete); //usuwanie wszystkie które były w liście
    }

    private void ifScoredAddPoints(Trash trash) {
        for (Container container : containers) {
            if (isRightContainer(trash, container) && isAtRightPlace(container, trash)) {
                if (score > 0 && score % 10 == 0) {
                    containers.forEach(Container::incrementSpeed);
                    countdownTimer.second += 10;
                }
                score++;
                countdownTimer.showText();
                countdownTimer.repaint();
            }
        }
    }

    private static boolean isRightContainer(Trash trash, Container container) {
        return container.getName().equals(trash.getName());
    }

    private boolean isAtRightPlace(Container c, Trash t) {
        return c.getX() < t.getX() + t.getWidth() && c.getX() + c.getWidth() > t.getX();
    }

    private void throwTrash() {
        if (mouseH.isClicked()) {
            Optional<Trash> optionalTrash = trashes.stream().filter(t -> !t.haveDes()).findFirst(); //wybiera pierwszy element bez desX i desY
            optionalTrash.ifPresent(trash -> trash.setDestination(mouseH.pointerX, END_Y)); //jeżeli taki jest to ustawia mu te wartości
            createNewTrash();
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.clearRect(0,0,screenWidth, screenHeight);
        player.draw(g2);
        trashes.forEach(o -> o.draw(g2));
        containers.forEach(c -> c.draw(g2));
        pointer.draw(g2);
        countdownTimer.repaint();
        drawPoints(g2);
    }

    private void drawPoints(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.drawString(score + "", 10, 10);
    }
}