package core;

public class ComboManager {
    private int combo = 1;
    private long lastKillTime = 0;

    public void addKill() {
        long now = System.currentTimeMillis();
        if (now - lastKillTime < 2000) {
            combo++;
            if (combo > 5) combo = 5;
        } else {
            combo = 1;
        }
        lastKillTime = now;
    }

    public int getCombo() {
        if (System.currentTimeMillis() - lastKillTime > 2000) {
            combo = 1;
        }
        return combo;
    }
}
