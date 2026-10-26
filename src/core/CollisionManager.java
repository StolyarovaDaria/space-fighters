package core;

import entities.*;

public class CollisionManager {

    public void check(GameManager manager) {
        java.util.List<Entity> entities = manager.getEntities();
        Player player = manager.getPlayer();
        ScoreManager scoreManager = manager.getScoreManager();
        ComboManager comboManager = manager.getComboManager();

        for (Entity a : entities) {
            if (!a.isAlive()) continue;

            for (Entity b : entities) {
                if (a == b || !b.isAlive()) continue;

                if (a instanceof Bullet && b instanceof Enemy) {
                    if (a.getBounds().intersects(b.getBounds())) {
                        Bullet bullet = (Bullet) a;
                        Enemy enemy = (Enemy) b;

                        if (bullet.isFromPlayer()) {
                            enemy.takeDamage(bullet.getDamage());
                            bullet.takeDamage(9999);

                            if (!enemy.isAlive()) {
                                comboManager.addKill();
                                scoreManager.addScore(enemy.getScoreValue() * comboManager.getCombo());

                                if (Math.random() < 0.15) {
                                    PowerUp.Type[] types = PowerUp.Type.values();
                                    PowerUp.Type randomType = types[(int) (Math.random() * types.length)];
                                    manager.addEntity(new PowerUp(enemy.getBounds().x, enemy.getBounds().y, randomType));
                                }
                            }
                        }
                    }
                }

                if (a instanceof Enemy && b == player) {
                    if (a.getBounds().intersects(b.getBounds())) {
                        player.takeDamage(20);
                        a.takeDamage(9999);
                    }
                }

                if (a instanceof PowerUp && b == player) {
                    if (a.getBounds().intersects(b.getBounds())) {
                        applyPowerUp(player, (PowerUp) a);
                        a.takeDamage(9999);
                    }
                }

                if (a instanceof Bullet && b == player) {
                    if (a.getBounds().intersects(b.getBounds())) {
                        Bullet bullet = (Bullet) a;
                        if (!bullet.isFromPlayer()) {
                            player.takeDamage(bullet.getDamage());
                            bullet.takeDamage(9999);
                        }
                    }
                }
            }
        }
    }

    private void applyPowerUp(Player player, PowerUp p) {
        switch (p.getType()) {
            case HEAL -> player.heal(30);
            case FIRE_RATE -> player.speedUpFireRate();
            case SHIELD -> player.activateShield();
            case SPEED -> player.speedUp();
        }
    }
}