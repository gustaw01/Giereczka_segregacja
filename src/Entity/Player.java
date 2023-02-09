package Entity;

import main.GamePanel;
import main.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    MouseHandler mouseH;

    public Player(GamePanel gp, MouseHandler mouseH) {

        this.gp = gp;
        this.mouseH = mouseH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        playerX = 284;
        playerY = 500;
    }

    public void getPlayerImage() {
        try {
            paperBin = ImageIO.read(getClass().getResourceAsStream("/assets/paperBin-160x160.png"));
            plasticBin = ImageIO.read(getClass().getResourceAsStream("/assets/plasticBin-160x160.png"));
            glassBin = ImageIO.read(getClass().getResourceAsStream("/assets/glassBin-160x160.png"));
            player = ImageIO.read(getClass().getResourceAsStream("/assets/player_sprite-256x256.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {}

    public void draw(Graphics2D g2) {

        g2.drawImage(paperBin, x, y, gp.tileSize*5, gp.tileSize*5, null);
        g2.drawImage(plasticBin, x + 332, y, gp.tileSize*5, gp.tileSize*5, null);
        g2.drawImage(glassBin, x + 664, y, gp.tileSize*5, gp.tileSize*5, null);
        g2.drawImage(player, playerX, playerY, gp.tileSize*8, gp.tileSize*8, null);
    }
}
