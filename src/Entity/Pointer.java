package Entity;

import main.GamePanel;
import main.MouseHandler;

import java.awt.*;

public class Pointer {

    MouseHandler mouseHandler;
    int startX, startY, endX, endY;

    public Pointer(MouseHandler mouseH, int startX, int startY, int endX, int endY) {
        this.mouseHandler = mouseH;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.RED);
        g2.fillRect(endX - 2, endY - 2, 5, 5);

        g2.setColor(Color.BLACK);
        g2.drawLine(startX,startY ,endX, endY);
    }

    public void update() {
        this.endX = mouseHandler.getCurrentX();
    }
}
