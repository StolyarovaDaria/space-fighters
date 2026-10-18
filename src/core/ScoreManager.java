package core;

import java.io.*;

public class ScoreManager {
    private int score = 0;
    private int highScore = 0;
    private static final String FILE_NAME = "highscore.txt";

    public void addScore(int points) {
        score += points;
    }

    public void reset() {
        score = 0;
    }

    public void saveHighScore() {
        if (score > highScore) {
            highScore = score;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter((FILE_NAME)))) {
                writer.write(String.valueOf(highScore));
            } catch (IOException e) {
                System.err.println("Не удалось сохранить рекорд: " + e.getMessage());
            }
        }
    }

    public void loadHighScore() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                highScore = Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Не удалось загрузить рекорд: " + e.getMessage());
        }
    }

    public int getScore() { return score; }
    public int getHighScore() { return highScore; }
}
