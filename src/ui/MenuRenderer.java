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

    public void drawPause(Graphics2D g, GameManager manager) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.WHITE);
        g.setFont(fontTitle);
        g.drawString("PAUSED", 310, 150);

        g.setFont(fontMenu);
        g.drawString("Press P to resume", 305, 220);
        g.drawString("Press M to return to Menu", 270, 260);

        g.setColor(Color.YELLOW);
        g.setFont(fontMenu);
        g.drawString("UPGRADES", 340, 330);

        g.setColor(Color.WHITE);
        g.setFont(fontSmall);

        g.drawString("Coins: " + manager.getUpgradeManager().getCoins(), 365, 365);

        g.drawString("7 - Upgrade HP (Cost 50)", 275, 410);
        g.drawString("8 - Upgrade Damage (Cost 100)", 260, 440);
        g.drawString("9 - Upgrade Speed (Cost 75)", 270, 470);
    }

    public void drawGameOver(Graphics2D g, GameManager manager) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.RED);
        g.setFont(fontTitle);
        g.drawString("GAME OVER", 250, 240);

        g.setColor(Color.WHITE);
        g.setFont(fontMenu);
        String scoreText = "Your Score: " + manager.getScoreManager().getScore();
        g.drawString(scoreText, 320, 320);

        String highText = "High Score: " + manager.getScoreManager().getHighScore();
        g.drawString(highText, 310, 360);

        g.setFont(fontSmall);
        g.drawString("Press ENTER to restart", 305, 430);
    }
}