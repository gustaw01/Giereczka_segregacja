package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EndPanel extends CustomPanel {

    public EndPanel() {

        List<Integer> bestScores = null;

        try {
            bestScores = ScoreUtils.getScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
        var layout = new GridLayout(bestScores.size(),1);
        for (int score : bestScores) {
            add(new JLabel("Score: " + score));
        }
        setLayout(layout);

    }
}
