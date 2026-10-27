package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Enemy extends GameObject {
    private static final int  STOP_Y = 300;
    private static final long STOP_DURATION_MS = 2500;
    private static final long SHOOT_INTERVAL_MS = 3000;

    protected boolean canStop = true;
    private final double originalDy;
    private boolean alreadyStopped   = false;
    private boolean currentlyStopped = false;
    private long    stopStartTime    = 0;

    private final List<Bullet> pendingBullets = new ArrayList<>();
    private long lastShotTime = System.currentTimeMillis();

    public Enemy(double x, double y, double speed) {
        super(x, y);
        width  = 36;
        height = 36;
        hp     = 30;
        dy     = speed;
        this.originalDy = speed;
    }

    public int getScoreValue() { return 10; }

    @Override
    public void update() {
        if (canStop) {
            if (!alreadyStopped && y >= STOP_Y) {
                currentlyStopped = true;
                alreadyStopped   = true;
                stopStartTime    = System.currentTimeMillis();
                dy = 0;
            }
            if (currentlyStopped &&
                System.currentTimeMillis() - stopStartTime >= STOP_DURATION_MS) {
                currentlyStopped = false;
                dy = originalDy;            // снова летит вниз — не застрянет
            }
        }

        // стрельба раз в 3 секунды, только пока враг на/над экраном
        long now = System.currentTimeMillis();
        if (y >= 0 && now - lastShotTime >= SHOOT_INTERVAL_MS) {
            lastShotTime = now;
            fire();
        }

        super.update();
        if (y > 600) alive = false;
    }

    private void fire() {
        pendingBullets.add(
            new Bullet(x + width / 2.0 - 3, y + height, 0, 4, false)  // вниз, дефолтный урон 5
        );
    }

    public List<Bullet> getPendingBullets() {
        if (pendingBullets.isEmpty()) return Collections.emptyList();
        List<Bullet> out = new ArrayList<>(pendingBullets);
        pendingBullets.clear();
        return out;
    }
}