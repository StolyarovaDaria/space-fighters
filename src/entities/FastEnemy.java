package entities;

public class FastEnemy extends Enemy {
    public FastEnemy(double x, double y) {
        super(x, y);

        width = 28;
        height = 28;
        hp = 20;
        dy = 3.0;
        dx = (Math.random() - 0.5) * 1.5;
    }

    @Override
    public int getScoreValue() {
        return 15;
    }
}