package entities;

public class FastEnemy extends Enemy {
    public FastEnemy(double x, double y, double speed) {
        this(x, y, speed, 20, 5);
    }

    public FastEnemy(double x, double y, double speed, int hp, int damage) {
        super(x, y, speed, hp, damage);
        width = height = 28;
        dy = speed + 0.5;
        dx = (Math.random() - 0.5) * 1.5;
        canStop = false;
    }

    @Override
    public int getScoreValue() { return 15; }
}
