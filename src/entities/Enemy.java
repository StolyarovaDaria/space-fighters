package entities;

public class Enemy extends GameObject {
    private static final int  STOP_Y = 300;
    private static final long STOP_DURATION_MS = 2500;

    protected boolean canStop = true;          // FastEnemy переопределит на false
    private final double originalDy;
    private boolean alreadyStopped  = false;
    private boolean currentlyStopped = false;
    private long    stopStartTime   = 0;

    public Enemy(double x, double y, double speed) {
        super(x, y);
        width  = 36;
        height = 36;
        hp     = 30;
        dy     = speed;
        this.originalDy = speed;
    }

    public int getScoreValue() { return 10; }

    @Override
    public void update() {
        if (canStop) {
            if (!alreadyStopped && y >= STOP_Y) {
                currentlyStopped = true;
                alreadyStopped   = true;
                stopStartTime    = System.currentTimeMillis();
                dy = 0;
            }
            if (currentlyStopped &&
                System.currentTimeMillis() - stopStartTime >= STOP_DURATION_MS) {
                currentlyStopped = false;
                dy = originalDy;                // снова летит вниз — не застрянет
            }
        }

        super.update();
        if (y > 600) alive = false;
    }
}