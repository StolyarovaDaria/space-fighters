package entities;

public class Bullet extends GameObject {
    private final int damage;
    private final boolean fromPlayer;

    public Bullet(double x, double y, double dx, double dy, boolean fromPlayer) {
        this(x, y, dx, dy, fromPlayer, fromPlayer ? 10 : 5);
    }

    public Bullet(double x, double y, double dx, double dy,
                  boolean fromPlayer, int damage) {
        super(x, y);
        this.dx = dx;
        this.dy = dy;
        this.fromPlayer = fromPlayer;
        this.damage = damage;
        width = 6;
        height = 12;
        hp = 1;
    }

    @Override
    public void update() {
        super.update();
        if (y < -100 || y > 700 || x < -100 || x > 900) alive = false;
    }

    public int getDamage()      { return damage; }
    public boolean isFromPlayer() { return fromPlayer; }
}