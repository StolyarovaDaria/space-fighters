package ui;

import core.GameManager;
import core.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;

public class Renderer {

    private final int[] starX = new int[100];
    private final int[] starY = new int[100];
    private final int[] starSpeed = new int[100];
    private final Random random = new Random();

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final Font fontHUD = new Font("Arial", Font.BOLD, 18);
    private final Font fontTitle = new Font("Arial", Font.BOLD, 48);
    private final Font fontMenu = new Font("Arial", Font.PLAIN, 24);

    private final Color spaceBlue = new Color(10, 10, 40);

    public Renderer() {
        for (int i = 0; i < starX.length; i++) {
            starX[i] = random.nextInt(WIDTH);
            starY[i] = random.nextInt(HEIGHT);
            starSpeed[i] = 1 + random.nextInt(3);
        }
    }
    public void draw(Graphics2D g, GameManager manager) {
        drawBackground(g);
        GameState state = manager.getState();

        switch (state) {
            case PLAYING -> {
                manager.draw(g);
                drawHUD(g, manager);
            }
            case MENU -> drawMenu(g);
            case PAUSED -> {
                manager.draw(g);
                drawHUD(g, manager);
                drawPause(g);
            }
            case GAME_OVER -> {
                manager.draw(g);
                drawGameOver(g, manager);
            }
        }
    }

    private void drawBackground(Graphics2D g) {
        g.setColor(spaceBlue);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        for (int i = 0; i < starX.length; i++) {
            int size = (starSpeed[i] == 1) ? 1 : (starSpeed[i] == 2) ? 2 : 3;
            g.fillOval(starX[i], starY[i], size, size);

            starY[i] += starSpeed[i];

            if (starY[i] > HEIGHT) {
                starY[i] = 0;
                starX[i] = random.nextInt(WIDTH);
            }
        }
    }
    private void drawHUD(Graphics2D g, GameManager manager) {
        g.setFont(fontHUD);
        g.setColor(Color.WHITE);

        g.drawString("Score: " + manager.getScoreManager().getScore(), 20, 30);
        g.drawString("High: " + manager.getScoreManager().getHighScore(), 20, 55);

        String waveText = "Wave: " + manager.getSpawnManager().getWave();
        int waveWidth = g.getFontMetrics().stringWidth(waveText);
        g.drawString(waveText, WIDTH - waveWidth - 20, 30);
    }

    private void drawMenu(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(fontTitle);

        String title = "SPACE BATTLE";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (WIDTH - titleWidth) / 2, 250);

        g.setFont(fontMenu);
        String startText = "Press ENTER to start";
        int startWidth = g.getFontMetrics().stringWidth(startText);
        g.drawString(startText, (WIDTH - startWidth) / 2, 350);
    }

    private void drawPause(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(fontTitle);
        String pauseText = "PAUSED";
        int pauseWidth = g.getFontMetrics().stringWidth(pauseText);
        g.drawString(pauseText, (WIDTH - pauseWidth) / 2, HEIGHT / 2);
    }
    private void drawGameOver(Graphics2D g, GameManager manager) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.RED);
        g.setFont(fontTitle);
        String goText = "GAME OVER";
        int goWidth = g.getFontMetrics().stringWidth(goText);
        g.drawString(goText, (WIDTH - goWidth) / 2, HEIGHT / 2 - 40);

        g.setColor(Color.WHITE);
        g.setFont(fontMenu);

        String scoreText = "Your Score: " + manager.getScoreManager().getScore();
        int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, (WIDTH - scoreWidth) / 2, HEIGHT / 2 + 30);

        String restartText = "Press ENTER to restart";
        int restartWidth = g.getFontMetrics().stringWidth(restartText);
        g.drawString(restartText, (WIDTH - restartWidth) / 2, HEIGHT / 2 + 80);
    }
}