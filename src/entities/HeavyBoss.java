package entities;

public class HeavyBoss extends Boss {
    public HeavyBoss(double x, double y) {
        super(x, y, 500, 30);
    }

    @Override
    protected void fire() {
        pendingBullets.add(new Bullet(x + width / 2.0 - 9,
                y + height, 0, 4, false, damage, 18, 24));
    }
}
