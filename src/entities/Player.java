package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends GameObject {
    private static final int MAX_HP = 100;
    private static final double BASE_SPEED = 5.0;
    private static final long SHOT_COOLDOWN_MS = 250;

    private long lastShotTime = 0;
    private int weaponLevel = 1;

    private long shieldEndTime = 0;
    private long speedEndTime = 0;

    public Player(double x, double y) {
        super(x, y);
        width = 40;
        height = 40;
        hp = MAX_HP;
    }

    private double currentSpeed() {
        return System.currentTimeMillis() < speedEndTime
                ? BASE_SPEED * 1.5
                : BASE_SPEED;
    }

    public void moveLeft() {
        dx = -currentSpeed();
    }

    public void moveRight() {
        dx = currentSpeed();
    }

    public void moveUp() {
        dy = -currentSpeed();
    }

    public void moveDown() {
        dy = currentSpeed();
    }

    public void stopX() {
        dx = 0;
    }

    public void stopY() {
        dy = 0;
    }

    public Bullet shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime < SHOT_COOLDOWN_MS) {
            return null;
        }

        lastShotTime = now;
        int damage = 10 + (weaponLevel - 1) * 5;

        return new Bullet(
                x + width / 2.0 - 3,
                y - 14,
                0,
                -10,
                true,
                damage
        );
    }

    public void heal(int amount) {
        hp = Math.min(MAX_HP, hp + amount);
    }

    public void upgradeWeapon() {
        if (weaponLevel < 3) {
            weaponLevel++;
        }
    }

    public void activateShield() {
        shieldEndTime = System.currentTimeMillis() + 5000;
    }

    public void speedUp() {
        speedEndTime = System.currentTimeMillis() + 5000;
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();

        if (shieldEndTime > 0 && now > shieldEndTime) {
            shieldEndTime = 0;
        }

        if (speedEndTime > 0 && now > speedEndTime) {
            speedEndTime = 0;
        }

        super.update();
    }

    @Override
    public void takeDamage(int damage) {
        if (System.currentTimeMillis() < shieldEndTime) {
            return;
        }

        super.takeDamage(damage);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(80, 160, 255));
        g.fillRect((int) x, (int) y, width, height);

        g.setColor(Color.WHITE);
        g.fillRect((int) x + width / 2 - 3, (int) y + 8, 6, height / 2);

        if (System.currentTimeMillis() < shieldEndTime) {
            g.setColor(new Color(0, 255, 255, 150));
            g.drawOval((int) x - 5, (int) y - 5, width + 10, height + 10);
        }
    }
}