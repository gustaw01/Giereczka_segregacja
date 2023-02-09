package Entity;

import main.GamePanel;
import main.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player implements Drawable {
    MouseHandler mouseH;

    int x, y, width, height;

    private BufferedImage img;

    public Player(MouseHandler mouseH, int x, int y, int width, int height) {
        this.mouseH = mouseH;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            img = ImageIO.read(getClass().getResourceAsStream("/assets/player_sprite-256x256.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {}

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

    public void draw(Graphics2D g2) {
        g2.drawImage(img, (int) this.x, (int) this.y, width, height, null);
    }
}
