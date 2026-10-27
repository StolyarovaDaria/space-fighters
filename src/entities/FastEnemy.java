package entities;

public class FastEnemy extends Enemy {
    public FastEnemy(double x, double y, double speed) {
        super(x, y, speed);
        width  = 28;
        height = 28;
        hp     = 20;
        dy     = speed + 0.5;
        dx     = (Math.random() - 0.5) * 1.5;
        canStop = false;                       // быстрый враг просто летит вниз
    }

    @Override
    public int getScoreValue() { return 15; }
}