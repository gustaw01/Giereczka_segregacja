package Entity;

import java.awt.*;

public interface Drawable {
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();

    void draw(Graphics2D g2);

    void update();
}
