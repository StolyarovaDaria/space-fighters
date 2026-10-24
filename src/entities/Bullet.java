package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet extends GameObject {
    private int damage;
    private boolean fromPlayer;

    public Bullet(double x, double y, double dx, double dy, boolean fromPlayer) {
        this(x, y, dx, dy, fromPlayer, fromPlayer ? 10 : 5);
    }

    public Bullet(double x, double y, double dx, double dy,
                  boolean fromPlayer, int damage) {
        super(x, y);

        this.dx = dx;
        this.dy = dy;
        this.fromPlayer = fromPlayer;
        this.damage = damage;

        width = 6;
        height = 12;
        hp = 1;
    }

    @Override
    public void update() {
        super.update();

        if (y < -100 || y > 2000 || x < -100 || x > 2000) {
            alive = false;
        }
    }

    public int getDamage() {
        return damage;
    }

    public boolean isFromPlayer() {
        return fromPlayer;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(fromPlayer ? Color.YELLOW : Color.ORANGE);
        g.fillRect((int) x, (int) y, width, height);
    }
}