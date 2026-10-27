package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boss extends Enemy {
    private static final int  WORLD_WIDTH = 800;
    private static final long SHOOT_INTERVAL_MS        = 2500;
    private static final long ENRAGE_SHOOT_INTERVAL_MS = 1200;
    private static final int  ENRAGE_HP = 150;   // 50% от 300
    private static final int  STOP_Y    = 100;

    private final List<Bullet> pendingBullets = new ArrayList<>();
    private long lastShotTime = 0;

    public Boss(double x, double y) {
        super(x, y, 1.0);
        width  = 100;
        height = 80;
        hp     = 300;
        dy     = 2.0;
        dx     = 2.0;
    }

    @Override
    public int getScoreValue() { return 100; }

    @Override
    public void update() {
    
        if (y < STOP_Y) y += dy;

        x += dx;
        if (x < 0)                   { x = 0;                       dx =  Math.abs(dx); }
        if (x + width > WORLD_WIDTH) { x = WORLD_WIDTH - width;     dx = -Math.abs(dx); }

        long now = System.currentTimeMillis();
        long interval = (hp < ENRAGE_HP) ? ENRAGE_SHOOT_INTERVAL_MS : SHOOT_INTERVAL_MS;
        if (now - lastShotTime >= interval) {
            lastShotTime = now;
            fire();
        }
    }

    private void fire() {
        double cx = x + width / 2.0;
        double by = y + height;             

        pendingBullets.add(new Bullet(x + 22,          by, -2, 5, false, 10)); // спереди-лево
        pendingBullets.add(new Bullet(cx - 3,          by,  0, 5, false, 10)); // центр
        pendingBullets.add(new Bullet(x + width - 28,  by,  2, 5, false, 10)); // спереди-право

        double sideY = y + height / 2.0 - 6;
        pendingBullets.add(new Bullet(x - 3,           sideY, -4, 3, false, 10)); // левый бок
        pendingBullets.add(new Bullet(x + width - 3,   sideY,  4, 3, false, 10)); // правый бок
    }

    public List<Bullet> getPendingBullets() {
        if (pendingBullets.isEmpty()) return Collections.emptyList();
        List<Bullet> out = new ArrayList<>(pendingBullets);
        pendingBullets.clear();
        return out;
    }
}