package entities;

import java.awt.Rectangle;

public class Boss extends Enemy {
    private static final int WORLD_WIDTH = 800, STOP_Y = 100;
    private static final long SHOOT_INTERVAL_MS = 2500, ENRAGE_SHOOT_INTERVAL_MS = 1200;
    private static final int ENRAGE_HP = 150;
    private long lastBossShotTime;

    public Boss(double x, double y) {
        this(x, y, 900, 10);          // было 300 — теперь 3x
    }

    protected Boss(double x, double y, int hp, int damage) {
        super(x, y, 1.0, hp, damage);
        width = 270;
        height = 140;
        dy = dx = 2.0;
        canStop = false;
    }

    @Override
    public Rectangle getBounds() {
        // Убираем прозрачные края картинки: по 20px с каждой стороны
        return new Rectangle((int) x + 20, (int) y + 20, width - 40, height - 40);
    }

    @Override
    public int getScoreValue() { return 100; }

    @Override
    public void update() {
        if (y < STOP_Y) y += dy;
        x += dx;
        if (x < 0) { x = 0; dx = Math.abs(dx); }
        if (x + width > WORLD_WIDTH) {
            x = WORLD_WIDTH - width;
            dx = -Math.abs(dx);
        }
        long now = System.currentTimeMillis();
        if (now - lastBossShotTime >= getShootInterval()) {
            lastBossShotTime = now;
            fire();
        }
    }

    protected long getShootInterval() {
        return hp < ENRAGE_HP ? ENRAGE_SHOOT_INTERVAL_MS : SHOOT_INTERVAL_MS;
    }

    protected void fire() {
        double cx = x + width / 2.0, by = y + height;
        pendingBullets.add(new Bullet(x + 50, by, -2, 5, false, damage));
        pendingBullets.add(new Bullet(cx - 3, by, 0, 5, false, damage));
        pendingBullets.add(new Bullet(x + width - 50, by, 2, 5, false, damage));
        double sideY = y + height / 2.0 - 6;
        pendingBullets.add(new Bullet(x - 3, sideY, -4, 3, false, damage));
        pendingBullets.add(new Bullet(x + width - 3, sideY, 4, 3, false, damage));
    }
}