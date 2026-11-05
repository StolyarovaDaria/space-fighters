package entities;

public class LaserBoss extends Boss {
    public LaserBoss(double x, double y) {
        super(x, y, 450, 12);
    }

    @Override
    protected void fire() {
        double center = x + width / 2.0;
        double by = y + height;
        for (int i = -2; i <= 2; i++) {
            double startX = center + (i * 15);
            pendingBullets.add(new Bullet(startX, by, i * 1.5,
                    5, false, damage));
        }
    }
}