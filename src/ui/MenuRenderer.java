package ui;

import core.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MenuRenderer {

    private final Font fontTitle = new Font("Arial", Font.BOLD, 48);
    private final Font fontMenu = new Font("Arial", Font.PLAIN, 24);
    private final Font fontSmall = new Font("Arial", Font.PLAIN, 18);

    public void drawMenu(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(fontTitle);
        g.drawString("SPACE BATTLE", 200, 200);

        g.setFont(fontMenu);
        g.drawString("Press ENTER to start", 280, 280);

        g.setFont(fontSmall);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Select Difficulty:", 320, 450);

        g.setColor(Color.GREEN);
        g.drawString("1 - Easy", 250, 490);

        g.setColor(Color.WHITE);
        g.drawString("2 - Normal", 350, 490);

        g.setColor(Color.RED);
        g.drawString("3 - Hard", 460, 490);
    }

    public void drawPause(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.WHITE);
        g.setFont(fontTitle);
        g.drawString("PAUSED", 320, 280);

        g.setFont(fontMenu);
        g.drawString("Press P to resume", 305, 340);
        g.drawString("Press M to return to Menu", 270, 380);
    }

    public void drawGameOver(Graphics2D g, GameManager manager) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.RED);
        g.setFont(fontTitle);
        g.drawString("GAME OVER", 230, 240);

        g.setColor(Color.WHITE);
        g.setFont(fontMenu);
        String scoreText = "Your Score: " + manager.getScoreManager().getScore();
        g.drawString(scoreText, 300, 320);

        String highText = "High Score: " + manager.getScoreManager().getHighScore();
        g.drawString(highText, 300, 360);

        g.setFont(fontSmall);
        g.drawString("Press ENTER to restart", 320, 430);
    }
}