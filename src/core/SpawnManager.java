package core;

import entities.Enemy;
import entities.FastEnemy;
import entities.Boss;

public class SpawnManager {
    private int screenWidth;
    private int wave = 0;
    private int enemiesToSpawn = 0;
    private int enemiesSpawned = 0;
    private long lastSpawnTime = 0;
    private int difficulty = 1;

    public SpawnManager(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setDifficulty(int diff) {
        this.difficulty = diff;
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
        if (now - lastSpawnTime >= getSpawnInterval()) {
            int x = (int) (Math.random() * (screenWidth - 60)) + 30;
            if (wave % 4 == 0) {
                manager.addEntity(new Boss(screenWidth / 2.0, -100));
            } else {
                double enemySpeed = (difficulty == 0) ? 1.0 : (difficulty == 2) ? 2.5 : 1.5;
                if (Math.random() > 0.5) {
                    manager.addEntity(new FastEnemy(x, 0, enemySpeed));
                } else {
                    manager.addEntity(new Enemy(x, 0, enemySpeed));
                }
            }

            enemiesSpawned++;
            lastSpawnTime = now;
        }
    }

    private long getSpawnInterval() {
        if (difficulty == 0) return 1500; // Easy
        if (difficulty == 2) return 600;  // Hard
        return 1000;                      // Normal
    }

    private void startNextWave() {
        wave++;
        enemiesSpawned = 0;
        if (wave % 4 == 0) {
            enemiesToSpawn = 1;
            return;
        }
        enemiesToSpawn = 5 + wave * 2;
    }

    public int getWave() {
        return wave;
    }
}
