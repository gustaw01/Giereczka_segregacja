package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    public boolean lmbPressed;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        int button = e.getButton();
        if(button == MouseEvent.BUTTON1){
            lmbPressed = true;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int button = e.getButton();
        if(button == MouseEvent.BUTTON1){
            lmbPressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
