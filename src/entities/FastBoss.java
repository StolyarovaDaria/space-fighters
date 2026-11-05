package entities;

public class FastBoss extends Boss {
    private static final int FAST_BOSS_HP = 110;
    private static final int FAST_BOSS_DAMAGE = 8;

    public FastBoss(double x, double y) {
        super(x, y, FAST_BOSS_HP, FAST_BOSS_DAMAGE);
        dx = 4.0;
        dy = 3.0;
        width = 75;
        height = 65;
    }

    @Override
    protected long getShootInterval() {
        return hp < 50 ? 450 : 750;
    }

    @Override
    protected void fire() {
        double center = x + width / 2.0;
        double by = y + height;
        pendingBullets.add(new Bullet(center - 3, by, -1.5, 7,
                false, damage));
        pendingBullets.add(new Bullet(center - 3, by, 0, 7,
                false, damage));
        pendingBullets.add(new Bullet(center - 3, by, 1.5, 7,
                false, damage));
    }
}
