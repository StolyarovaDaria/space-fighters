package core;

import entities.Enemy;
import entities.FastEnemy;

public class SpawnManager {
    private int screenWidth;
    private int wave = 0;
    private int enemiesToSpawn = 0;
    private int enemiesSpawned = 0;
    private long lastSpawnTime = 0;
    private long spawnInterval = 1000;

    public SpawnManager(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void reset() {
        wave = 0;
        startNextWave();
    }

    public void update(GameManager manager) {
        if (enemiesSpawned >= enemiesToSpawn) {
            if (!manager.hasEnemies()) {
                startNextWave();
            }
            return;
        }

        long now = System.currentTimeMillis();
        if (now - lastSpawnTime >= spawnInterval) {
            int x = (int) (Math.random() * (screenWidth - 60)) + 30;

            if (Math.random() > 0.5) {
                manager.addEntity(new FastEnemy(x,0));
            } else {
                manager.addEntity(new Enemy(x,0));
            }

            enemiesSpawned++;
            lastSpawnTime = now;
        }
    }

    private void startNextWave() {
        wave++;
        enemiesToSpawn = 5 + wave * 2;
        enemiesSpawned = 0;
    }

    public int getWave() {
        return wave;
    }
}
