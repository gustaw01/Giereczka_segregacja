package Entity;

import main.GamePanel;
import main.MouseHandler;

import java.awt.*;

public class Pointer {

    public final int y = 50;

    public int x = 0;

    GamePanel gamePanel;

    MouseHandler mouseHandler;

    public Pointer(GamePanel gamePanel, MouseHandler mouseH) {
        this.gamePanel = gamePanel;
        this.mouseHandler = mouseH;
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.RED);
        g2.fillRect(x, y, 5, 5);

        g2.setColor(Color.BLACK);
        g2.drawLine(x,y ,gamePanel.getWidth() /2, gamePanel.getHeight() - 100);
    }

    public void update() {
        this.x = mouseHandler.getCurrentX();
    }
}
