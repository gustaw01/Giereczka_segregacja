package main;

import javax.swing.*;

public abstract class CustomPanel extends JPanel {

    public enum GoToEvent {
        MAIN_MENU,
        GAME_PANEL,
        END_PANEL,
        DO_NOTHING
    }

    protected GoToEvent currentState = GoToEvent.DO_NOTHING;

    public GoToEvent getEvent() {
        return currentState;
    }

}
