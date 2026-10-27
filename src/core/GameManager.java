package core;

import entities.Entity;
import entities.Player;
import entities.Bullet;
import entities.Enemy;
import entities.PowerUp;
import ui.InputHandler;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private GameState state = GameState.MENU;
    private int screenWidth;
    private int screenHeight;
    private int difficulty = 1; // 0 = Easy, 1 = Normal, 2 = Hard
    private Player player;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> toAdd = new ArrayList<>();
    private InputHandler input;
    private CollisionManager collisionManager = new CollisionManager();
    private ComboManager comboManager = new ComboManager();
    private SpawnManager spawnManager;
    private ScoreManager scoreManager;

    public GameManager(InputHandler input, int screenWidth, int screenHeight) {
        this.input = input;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.spawnManager = new SpawnManager(screenWidth);
        this.scoreManager = new ScoreManager();
        int maxHp = (difficulty == 0) ? 150 : (difficulty == 2) ? 70 : 100;
        this.player = new Player(screenWidth / 2.0, screenHeight - 100, maxHp);
        scoreManager.loadHighScore();
        entities.add(player);
    }

    public void update() {
        switch (state) {
            case MENU:
                if (input.isDiff1()) setDifficulty(0); // Easy
                if (input.isDiff2()) setDifficulty(1); // Normal
                if (input.isDiff3()) setDifficulty(2); // Hard
                if (input.isStart()) startGame();
                break;
            case PLAYING:
                updatePlaying();
                break;
            case PAUSED:
                if (input.isPause()) state = GameState.PLAYING;
                if (input.isMenu()) state = GameState.MENU;
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
            if (e instanceof entities.Enemy) {
                toAdd.addAll(((entities.Enemy) e).getPendingBullets());
            }
        }
        collisionManager.check(this);
        spawnManager.update(this);
        entities.removeIf(e -> !e.isAlive() || e.getBounds().y > screenHeight);
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
            List<Bullet> bullets = player.shoot();
            if (bullets != null) toAdd.addAll(bullets);
        }
    }

    public void setDifficulty(int diff) {
        this.difficulty = diff;
        if (spawnManager != null) spawnManager.setDifficulty(diff);
    }

    public void startGame() {
        entities.clear();
        toAdd.clear();
        int maxHp = (difficulty == 0) ? 150 : (difficulty == 2) ? 70 : 100;
        player = new Player(screenWidth / 2.0, screenHeight - 100, maxHp);
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
    public List<Entity> getEntities() { return entities; }
    public ComboManager getComboManager() { return comboManager; }
}
