package entities;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private double baseSpeed = 5.0;
    private static final double SPEED_MULT = 1.5;
    private static final long SPEED_DURATION_MS = 5000;
    private static final long SHIELD_DURATION_MS = 5000;
    private static final long INVULN_MS = 1000;
    private static final long INITIAL_SHOT_COOLDOWN = 250;
    private static final long MIN_SHOT_COOLDOWN = 100;
    private static final int WORLD_WIDTH = 800, WORLD_HEIGHT = 600;

    private int maxHp;
    private int bulletDamage = 10;
    private boolean hasArmor, hasLifesteal;
    private long lastShotTime, shotCooldown = INITIAL_SHOT_COOLDOWN;
    private long shieldEndTime, speedEndTime, invulnEndTime;
    private long fireRateEndTime = 0;

    public Player(double x, double y, int maxHp) {
        super(x, y);
        width = height = 40;
        this.maxHp = maxHp;
        hp = maxHp;
    }

    private double currentSpeed() {
        return System.currentTimeMillis() < speedEndTime
                ? baseSpeed * SPEED_MULT : baseSpeed;
    }

    public void moveLeft() { dx = -currentSpeed(); }
    public void moveRight() { dx = currentSpeed(); }
    public void moveUp() { dy = -currentSpeed(); }
    public void moveDown() { dy = currentSpeed(); }
    public void stopX() { dx = 0; }
    public void stopY() { dy = 0; }

    public List<Bullet> shoot() {
        if (fireRateEndTime > 0 && System.currentTimeMillis() > fireRateEndTime) {
            shotCooldown = INITIAL_SHOT_COOLDOWN;
            fireRateEndTime = 0;
        }

        long now = System.currentTimeMillis();
        if (now - lastShotTime < shotCooldown) return null;
        lastShotTime = now;
        List<Bullet> bullets = new ArrayList<>(2);
        bullets.add(new Bullet(x + 8, y - 14, 0, -15, true, bulletDamage));
        bullets.add(new Bullet(x + width - 14, y - 14, 0, -15, true, bulletDamage));
        return bullets;
    }

    public void heal(int amount) { hp = Math.min(maxHp, hp + amount); }

    public void speedUpFireRate() {
        shotCooldown = MIN_SHOT_COOLDOWN;                 
        fireRateEndTime = System.currentTimeMillis() + 5000;
    }

    public void activateShield() { shieldEndTime = System.currentTimeMillis() + SHIELD_DURATION_MS; }
    public void speedUp() { speedEndTime = System.currentTimeMillis() + SPEED_DURATION_MS; }
    public void upgradeMaxHp(int amount) {
        if (amount > 0) { maxHp += amount; hp += amount; }
    }
    public void upgradeDamage(int amount) {
        if (amount > 0) bulletDamage += amount;
    }
    public void upgradeSpeed(double amount) {
        if (amount > 0) baseSpeed += amount;
    }
    public boolean hasLifesteal() { return hasLifesteal; }
    public void activateArmor() { hasArmor = true; }
    public void activateLifesteal() { hasLifesteal = true; }
    public int getDamage() {return bulletDamage; }
    public double getSpeed() { return baseSpeed; }
    public int getMaxHp() { return maxHp; }
    public int getBulletDamage() { return bulletDamage; }
    public boolean isInvulnerable() { return System.currentTimeMillis() < invulnEndTime; }

    @Override
    public void update() {
        super.update();
        if (x + width < 0) x = WORLD_WIDTH;
        else if (x > WORLD_WIDTH) x = -width;
        if (y + height < 0) y = WORLD_HEIGHT;
        else if (y > WORLD_HEIGHT) y = -height;
    }

    @Override
    public void takeDamage(int damage) {
        long now = System.currentTimeMillis();
        if (now < shieldEndTime || now < invulnEndTime) return;
        if (hasArmor) damage = Math.max(1, damage - 5);
        super.takeDamage(damage);
        invulnEndTime = now + INVULN_MS;
    }
}