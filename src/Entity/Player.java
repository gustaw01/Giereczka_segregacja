package Entity;

import main.GamePanel;
import main.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
            papier = ImageIO.read(getClass().getResourceAsStream("/player/papier-160x160.png"));
            plastik = ImageIO.read(getClass().getResourceAsStream("/player/plastik-160x160.png"));
            szklo = ImageIO.read(getClass().getResourceAsStream("/player/szklo-160x160.png"));
            gracz = ImageIO.read(getClass().getResourceAsStream("/player/player-160x160.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {}

    public void draw(Graphics2D g2) {
        BufferedImage paperBin = papier, plasticBin = plastik, glassBin = szklo, player = gracz;

        g2.drawImage(paperBin, x, y, gp.tileSize*5, gp.tileSize*5, null);
        g2.drawImage(plasticBin, x + 332, y, gp.tileSize*5, gp.tileSize*5, null);
        g2.drawImage(glassBin, x + 664, y, gp.tileSize*5, gp.tileSize*5, null);
        g2.drawImage(player, playerX, playerY, gp.tileSize*8, gp.tileSize*8, null);
//        if(mouseH.lmbPressed) {
//            g2.drawImage();
//        }
    }
}
