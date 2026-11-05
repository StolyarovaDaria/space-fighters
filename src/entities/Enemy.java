package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Enemy extends GameObject {
    private static final int STOP_Y = 300;
    private static final long STOP_DURATION_MS = 2500, SHOOT_INTERVAL_MS = 3000;
    protected boolean canStop = true;
    protected int damage;
    protected final List<Bullet> pendingBullets = new ArrayList<>();
    private final double originalDy;
    private boolean alreadyStopped, currentlyStopped;
    private long stopStartTime, lastShotTime = System.currentTimeMillis();

    public Enemy(double x, double y, double speed) {
        this(x, y, speed, 30, 5);
    }

    public Enemy(double x, double y, double speed, int hp, int damage) {
        super(x, y);
        width = height = 36;
        this.hp = hp;
        this.damage = damage;
        dy = speed;
        originalDy = speed;
    }

    public int getScoreValue() { return 10; }

    @Override
    public void update() {
        if (canStop) {
            if (!alreadyStopped && y >= STOP_Y) {
                currentlyStopped = alreadyStopped = true;
                stopStartTime = System.currentTimeMillis();
                dy = 0;
            }
            if (currentlyStopped &&
                System.currentTimeMillis() - stopStartTime >= STOP_DURATION_MS) {
                currentlyStopped = false;
                dy = originalDy;
            }
        }
        long now = System.currentTimeMillis();
        if (y >= 0 && now - lastShotTime >= SHOOT_INTERVAL_MS) {
            lastShotTime = now;
            fire();
        }
        super.update();
        if (y > 600) alive = false;
    }

    protected void fire() {
        pendingBullets.add(new Bullet(x + width / 2.0 - 3,
                y + height, 0, 4, false, damage));
    }

    public List<Bullet> getPendingBullets() {
        if (pendingBullets.isEmpty()) return Collections.emptyList();
        List<Bullet> out = new ArrayList<>(pendingBullets);
        pendingBullets.clear();
        return out;
    }
}
