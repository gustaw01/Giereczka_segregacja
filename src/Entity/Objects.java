package Entity;

import main.GamePanel;
import main.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Objects extends Entity {

    GamePanel gp;
    MouseHandler mouseH;

    int desX, desY;

    boolean destroyed;

    public Objects(GamePanel gp, MouseHandler mouseH, int desX, int desY) {

        this.gp = gp;
        this.mouseH = mouseH;
        this.desX = desX;
        this.desY = desY;
        this.speed = 5;

        setDefaultValues();
        getObjectImage();
    }

    public void setDefaultValues() {
        x = 480;
        y = 550;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void getObjectImage() {
        try {
            glass = ImageIO.read(getClass().getResourceAsStream("/assets/glass-256x256.png"));
            plastic = ImageIO.read(getClass().getResourceAsStream("/assets/plastic-256x256.png"));
            paper = ImageIO.read(getClass().getResourceAsStream("/assets/paper-256x256.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
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

    public void draw(Graphics2D g2) {

        g2.drawImage(glass, this.x, this.y, gp.tileSize*3, gp.tileSize*3, null);
        g2.drawImage(paper, this.x, this.y, gp.tileSize*3, gp.tileSize*3, null);
        g2.drawImage(plastic, this.x, this.y, gp.tileSize*3, gp.tileSize*3, null);
    }
}
