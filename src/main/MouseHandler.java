package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    public boolean lmbPressed, lmbReleased;
    public int pointerX;

    private int currentX = 0;

    boolean clicked = false;

    public boolean isClicked() {
        if (clicked) {
            clicked = false;
            return true;
        }
        return false;
    }

    public int getCurrentX() {
        return currentX;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        int button = e.getButton();
        if(button == MouseEvent.BUTTON1){
            lmbPressed = true;
        }
        pointerX = e.getPoint().x;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int button = e.getButton();
        if(button == MouseEvent.BUTTON1){
            lmbPressed = false;
            lmbReleased = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.currentX = e.getX();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.currentX = e.getX();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.currentX = e.getX();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.currentX = e.getX();
    }
}
