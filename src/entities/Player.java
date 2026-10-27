package entities;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private static final double BASE_SPEED = 5.0;
    private static final double SPEED_MULT = 1.5;
    private static final long   SPEED_DURATION_MS = 5000;
    private static final long   SHIELD_DURATION_MS = 5000;
    private static final long   INVULN_MS = 1000;

    private static final long   INITIAL_SHOT_COOLDOWN = 250;
    private static final long   MIN_SHOT_COOLDOWN = 100;

    private static final int    WORLD_WIDTH = 800;
    private static final int    WORLD_HEIGHT = 600;

    private final int maxHp;

    private long lastShotTime = 0;
    private long shotCooldown = INITIAL_SHOT_COOLDOWN;

    private long shieldEndTime = 0;
    private long speedEndTime = 0;
    private long invulnEndTime = 0;

    public Player(double x, double y, int maxHp) {
        super(x, y);
        width = 40;
        height = 40;
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    private double currentSpeed() {
        return System.currentTimeMillis() < speedEndTime
                ? BASE_SPEED * SPEED_MULT
                : BASE_SPEED;
    }

    public void moveLeft()  { dx = -currentSpeed(); }
    public void moveRight() { dx =  currentSpeed(); }
    public void moveUp()    { dy = -currentSpeed(); }
    public void moveDown()  { dy =  currentSpeed(); }
    public void stopX()     { dx = 0; }
    public void stopY()     { dy = 0; }

    public List<Bullet> shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime < shotCooldown) return null;
        lastShotTime = now;

        int damage = 10;
        List<Bullet> bullets = new ArrayList<>(2);
        bullets.add(new Bullet(x + 8,          y - 14, 0, -15, true, damage)); // левая
        bullets.add(new Bullet(x + width - 14, y - 14, 0, -15, true, damage)); // правая
        return bullets;
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public void speedUpFireRate() {
        shotCooldown = MIN_SHOT_COOLDOWN;
    }

    public void activateShield() {
        shieldEndTime = System.currentTimeMillis() + SHIELD_DURATION_MS;
    }

    public void speedUp() {
        speedEndTime = System.currentTimeMillis() + SPEED_DURATION_MS;
    }

    public int getMaxHp() {
       return maxHp;
    }

    public boolean isInvulnerable() {
        return System.currentTimeMillis() < invulnEndTime;
    }

    @Override
    public void update() {
        super.update();

        if (x + width < 0)         x = WORLD_WIDTH;
        else if (x > WORLD_WIDTH)  x = -width;

        if (y + height < 0)        y = WORLD_HEIGHT;
        else if (y > WORLD_HEIGHT) y = -height;
    }

    @Override
    public void takeDamage(int damage) {
        long now = System.currentTimeMillis();
        if (now < shieldEndTime) return;
        if (now < invulnEndTime) return;

        super.takeDamage(damage);
        invulnEndTime = now + INVULN_MS;
    }
}