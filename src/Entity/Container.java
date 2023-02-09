package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Container implements Drawable{

    private int x, y, width, height;
    String name;
    private BufferedImage img;

    int speed = 0;
    int direction = 1;

    public Container(int x, int y, int width, int height, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        getObjectImage(name);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void draw(Graphics2D g2) {
        switch (name) {
            case "glass":
                g2.setColor(Color.GREEN);
                break;
            case "plastic":
                g2.setColor(Color.YELLOW);
                break;
            case "paper":
                g2.setColor(Color.BLUE);
                break;
            default:
                throw new RuntimeException("wrong name");
        }
        g2.fillRect(x, y, width, height);
        g2.drawImage(img, x, y, width, height, null);
    }

    @Override
    public void update() {

    }

    public void incrementSpeed() {
        speed += 2;
    }
    public void changeDirection() {
        this.direction *= -1;
    }

    public void move() {
        if (speed != 0) {
            this.x += speed * direction;
        }

    }

    private void getObjectImage(String name) {
        try {
            switch (name) {
                case "glass":
                    img = ImageIO.read(getClass().getResourceAsStream("/assets/glassBin-160x160.png"));
                    break;
                case "plastic":
                    img = ImageIO.read(getClass().getResourceAsStream("/assets/plasticBin-160x160.png"));
                    break;
                case "paper":
                    img = ImageIO.read(getClass().getResourceAsStream("/assets/paperBin-160x160.png"));
                    break;
                default:
                    throw new RuntimeException("wrong name");
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
