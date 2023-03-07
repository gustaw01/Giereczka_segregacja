package main;

import Entity.Container;
import Entity.Trash;
import Entity.Player;
import Entity.Pointer;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GamePanel extends CustomPanel implements Runnable{

    // screen settings

    public final int tileSize = 32;
    final int maxScreenCol = 32;
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
    Font font = new Font("Arial", Font.PLAIN, 30);

    Pointer pointer;

    private final static int END_Y = 25; //pozycja y celownika
    private final static int TRASH_SIZE = 96; // rozmiar odpadu

    public GamePanel(){

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
                drawCount = 0;
                timer = 0;
            }

            if (countdownTimer.second <= 0) {
                saveScore();
                currentState = GoToEvent.END_PANEL;
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

    private void saveScore() {
        try {
            ScoreUtils.saveScore(score);
        }catch (IOException e) {
            e.printStackTrace();
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

    private boolean isRightContainer(Trash trash, Container container) {
        return container.getName().equals(trash.getName());
    }

    private boolean isAtRightPlace(Container c, Trash t) {
        return c.getX() < t.getX() + t.getWidth() && c.getX() + c.getWidth() > t.getX();
    }

    boolean missedContainer = false;
    private void ifScoredAddPoints(Trash trash) {
        boolean isShot = false;
        for (Container container : containers) {
            if (isRightContainer(trash, container) && isAtRightPlace(container, trash)) {
                isShot = true;
                break;
            }
        }

        if (isShot) {
            score ++;
            missedContainer = false;
            System.out.println(score);
            if (score > 5 && score % 10 == 2) {
                containers.forEach(Container::incrementSpeed);
                countdownTimer.second += 10;
            }
        } else {
            score--;
            System.out.println(score);
            missedContainer = true;
        }
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
        drawPoints(g2);
        drawError(g2);
    }

    public void drawPoints(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setFont(font);
        g2.drawString(score + "", 750, 55);
    }

    public void drawError(Graphics2D g2) {
        if(missedContainer) {
            g2.setColor(Color.BLACK);
            g2.setFont(font);
            g2.drawString("You missed the container! -1 point", 30, 700);
        }
        else {
            g2.setColor(Color.WHITE);
            g2.setFont(font);
            g2.drawString("You missed the container! -1 point", 30, 700);
        }
    }
}