package Entity;

import main.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Trash implements Drawable {

    MouseHandler mouseH;

    double desX, desY;

    double x,y;
    int width, height, speed;

    boolean destroyed;

    String name;

    BufferedImage img;

    public Trash(MouseHandler mouseH, int x, int y, int width, int height) {

        this.mouseH = mouseH;
        this.speed = 5;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        getObjectImage();
    }

    private void getObjectImage() {
        try {
            switch (new Random().nextInt(3)) {
                case 0:
                    img = ImageIO.read(getClass().getResourceAsStream("/assets/glass-256x256.png"));
                    name = "glass";
                    break;
                case 1:
                    img = ImageIO.read(getClass().getResourceAsStream("/assets/paper-256x256.png"));
                    name = "paper";
                    break;
                case 2:
                    img = ImageIO.read(getClass().getResourceAsStream("/assets/plastic-256x256.png"));
                    name = "plastic";
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDestination(int desX, int desY) {
        this.desX = desX;
        this.desY = desY;
    }

    public boolean isDestroyed() {
        return destroyed;
    }



    @Override
    public int getX() {
        return (int) x;
    }

    @Override
    public int getY() {
        return (int) y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    public void update() {
        if (!haveDes()) {
            return;
        }
        double x = this.x;
        double y = this.y;
        double desX = this.desX;
        double desY = this.desY;
        double speed = (double) this.speed;
        double dx = desX - x;
        double dy = desY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance > speed) {
            double angle = Math.atan2(dy, dx);
            double vx = speed * Math.cos(angle);
            double vy = speed * Math.sin(angle);
            x += vx;
            y += vy;
        } else {
            x = desX;
            y = desY;
        }

        this.x = (int) x;
        this.y = (int) y;
        if (this.x == desX && this.y == desY) {
            this.destroyed = true;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.drawImage(img, (int) this.x - 20, (int) this.y - 20, width, height, null);
    }

    public boolean haveDes() {
        return desX != 0 && desY != 0;
    }

    public String getName() {
        return name;
    }
}
