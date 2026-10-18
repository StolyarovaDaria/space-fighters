package core;

import entities.Entity;
import entities.Player;
import entities.Bullet;
import entities.Enemy;
import entities.PowerUp;
import ui.InputHandler;

import javax.swing.text.html.parser.Entity;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private GameState state = GameState.MENU;
    private int screenWidth;
    private int screenHeight;

    private Player player;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> toAdd = new ArrayList<>();

    private InputHandler input;
    private SpawnManager spawnManager;
    private ScoreManager scoreManager;

    public GameManager(InputHandler input, int screenWidth, int screenHeight) {
        this.input = input;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.spawnManager = new SpawnManager(screenWidth);
        this.scoreManager = new ScoreManager();
        this.player = new Player(screenWidth / 2.0, screenHeight - 100);

        scoreManager.loadHighScore();
        entities.add(player);
    }

    public void update() {
        switch (state) {
            case MENU:
                if (input.isStart()) startGame();
                break;

            case PLAYING:
                updatePlaying();
                break;

            case PAUSED:
                if (input.isPause()) state = GameState.PLAYING;
                break;

            case GAME_OVER:
                if (input.isStart()) restart();
                break;
        }
    }

    private void updatePlaying() {
        handlePlayerInput();

        for (Entity e : entities) {
            e.update();
        }

        checkCollisions();

        spawnManager.update(this);

        entities.removeIf(e -> !e.isAlive());
        entities.addAll(toAdd);
        toAdd.clear();

        if (!player.isAlive()) {
            gameOver();
        }

        if (input.isPause()) state = GameState.PAUSED;
    }

    private void handlePlayerInput() {
        if (input.isLeft()) player.moveLeft();
        else if (input.isRight()) player.moveRight();
        else player.stopX();

        if (input.isUp()) player.moveUp();
        else if (input.isDown()) player.moveDown();
        else player.stopY();

        if (input.isShoot()) {
            Bullet b = player.shoot();
            if (b != null) toAdd.add(b);
        }
    }

    private void checkCollisions() {
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
                                scoreManager.addScore(enemy.getScoreValue());
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
                        PowerUp p = (PowerUp) a;
                        applyPowerUp(p);
                        a.takeDamage(9999);
                    }
                }
            }
        }
    }

    private void applyPowerUp(PowerUp p) {
        switch (p.getType()) {
            case HEAL:
                player.heal(30);
                break;
            case WEAPON_UP:
                player.upgradeWeapon();
                break;
            case SHIELD:
                player.activateShield();
                break;
            case SPEED:
                player.speedUp();
                break;
        }
    }

    public void startGame() {
        entities.clear();
        toAdd.clear();
        player = new Player(screenWidth / 2.0, screenHeight - 100);
        entities.add(player);
        scoreManager.reset();
        spawnManager.reset();
        state = GameState.PLAYING;
    }

    public void restart() {
        startGame();
    }

    public void gameOver() {
        state = GameState.GAME_OVER;
        scoreManager.saveHighScore();
    }

    public void addEntity(Entity e) {
        toAdd.add(e);
    }

    public boolean hasEnemies() {
        for (Entity e : entities) {
            if (e instanceof Enemy) return true;
        }
        return false;
    }

    public void draw(Graphics2D g) {
        for (Entity e : entities) {
            e.draw(g);
        }
    }

    public GameState getState() {
        return state;
    }
    public Player getPlayer() {
        return player;
    }
    public ScoreManager getScoreManager() {
        return scoreManager;
    }
    public SpawnManager getSpawnManager() {
        return spawnManager;
    }
    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
}
