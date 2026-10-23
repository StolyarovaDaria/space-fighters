package entities;

import java.awt.Rectangle;

public abstract class GameObject implements Entity {
    protected double x, y;
    protected double dx, dy;
    protected int width, height;
    protected int hp;
    protected boolean alive = true;

    protected GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.alive = true;
    }

    @Override
    public void update() {
        x += dx;
        y += dy;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            alive = false;
        }
    }
}