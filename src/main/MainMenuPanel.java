package main;

import javax.swing.*;

public class MainMenuPanel extends CustomPanel {

    public MainMenuPanel() {
        init();
    }

    private void init() {
        JButton goToGameButton = new JButton();
        goToGameButton.addActionListener(a -> {
            this.currentState = GoToEvent.GAME_PANEL;
        });
        add(goToGameButton);
        goToGameButton.setText("Rozpocznij Rozgrywkę");
    }

}
