package entities;

public class LaserBoss extends Boss {
    public LaserBoss(double x, double y) {
        super(x, y, 150, 12);
    }

    @Override
    protected void fire() {
        double center = x + width / 2.0;
        double by = y + height;
        for (int i = -2; i <= 2; i++) {
            pendingBullets.add(new Bullet(center - 3, by, i * 2.0,
                    5, false, damage));
        }
    }
}
