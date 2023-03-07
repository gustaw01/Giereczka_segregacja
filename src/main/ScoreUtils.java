package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ScoreUtils {

    public static synchronized List<Integer> getScores() throws IOException {
        List<Integer> scores = new ArrayList<>();
        File file = new File("score.txt");
        if (!file.exists()) {
            file.createNewFile();
        } else {
            Scanner scanner= new Scanner(file);
            while (scanner.hasNextLine()) {
                scores.add(Integer.valueOf(scanner.nextLine()));
            }
            scanner.close();
        }
        return scores;
    }

    public static synchronized void saveScore(int score) throws IOException {
        var scoreList = getScores();
        scoreList.add(score);
        BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt",false));
        String resultToSave = scoreList.stream()
                .sorted((i1, i2) -> i2.compareTo(i1)) //sortowanie typu DESC
                .distinct().map(i-> i + "\n") //zamiana w stringa i dodanie nowej lini
                .collect(Collectors.joining()); //zapis do jednego stringa
        writer.append(resultToSave);
        writer.close();
    }

}
