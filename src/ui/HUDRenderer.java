package ui;

import core.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class HUDRenderer {

    private final Font font = new Font("Arial", Font.BOLD, 18);

    public void draw(Graphics2D g, GameManager manager) {
        g.setFont(font);
        g.setColor(Color.WHITE);

        g.drawString("Score: " + manager.getScoreManager().getScore(), 20, 30);
        g.drawString("High: " + manager.getScoreManager().getHighScore(), 20, 55);

        String waveText = "Wave: " + manager.getSpawnManager().getWave();
        g.drawString(waveText, 680, 30);

        int hp = manager.getPlayer().getHp();

        int maxHp = manager.getPlayer().getMaxHp();

        int hpWidth = (int) (200 * ((double) hp / maxHp));

        g.setColor(Color.RED);
        g.drawRect(20, 70, 200, 20);

        g.setColor(Color.GREEN);
        g.fillRect(20, 70, hpWidth, 20);

        g.setColor(Color.WHITE);
        g.drawString("HP: " + hp + "/" + maxHp, 230, 86);
    }
}