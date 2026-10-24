package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends GameObject {
    public Enemy(double x, double y) {
        super(x, y);
        width = 36;
        height = 36;
        hp = 30;
        dy = 1.5;
    }

    public int getScoreValue() {
        return 10;
    }

    @Override
    public void update() {
        super.update();

        if (y > 2000) {
            alive = false;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(220, 60, 60));
        g.fillRect((int) x, (int) y, width, height);

        g.setColor(Color.WHITE);
        g.fillRect((int) x + 8, (int) y + 10, 6, 6);
        g.fillRect((int) x + width - 14, (int) y + 10, 6, 6);
    }
}