package ui;

import core.GameManager;
import core.GameState;
import entities.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class Renderer {
    private final Starfield background = new Starfield();
    private final HUDRenderer hud = new HUDRenderer();
    private final MenuRenderer menu = new MenuRenderer();

    private BufferedImage imgPlayer, imgBoss, imgStar, imgFast;

    public Renderer() {

        imgPlayer = loadImage("/player.png");
        imgBoss = loadImage("/boss.png");
        imgStar = loadImage("/enemy_star.png");
        imgFast = loadImage("/enemy_fast.png");
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException | NullPointerException e) {
            System.err.println("Ошибка: Не найдена картинка " + path);
            return null;
        }
    }

    public void draw(Graphics2D g, GameManager manager) {
        background.draw(g);
        switch (manager.getState()) {
            case PLAYING -> { drawEntities(g, manager.getEntities()); hud.draw(g, manager); }
            case PAUSED -> { drawEntities(g, manager.getEntities()); hud.draw(g, manager); menu.drawPause(g); }
            case MENU -> menu.drawMenu(g);
            case GAME_OVER -> menu.drawGameOver(g, manager);
        }
    }

    private void drawEntities(Graphics2D g, List<Entity> entities) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Entity e : entities) {
            int x = (int) e.getBounds().getX();
            int y = (int) e.getBounds().getY();

            if (e instanceof Player) {
                Player p = (Player) e;

                if (p.isInvulnerable() && (System.currentTimeMillis() / 100) % 2 == 0) {
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                }

                if (imgPlayer != null) {

                    g.drawImage(imgPlayer, x - 20, y - 10, 80, 80, null);
                } else {
                    g.setColor(Color.CYAN);
                    g.fillRect(x, y, 40, 40);
                }
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            } else if (e instanceof Boss) {
                if (imgBoss != null) {
                    g.drawImage(imgBoss, x, y, 270, 140, null);
                } else {
                    g.setColor(Color.RED);
                    g.fillRect(x, y, 270, 140);
                }

            } else if (e instanceof Enemy) {
                if (e instanceof FastEnemy) {
                    if (imgFast != null) g.drawImage(imgFast, x - 15, y - 15, 60, 60, null);
                } else {
                    if (imgStar != null) g.drawImage(imgStar, x - 15, y - 15, 60, 60, null);
                }

            } else if (e instanceof Bullet) {
                Bullet b = (Bullet) e;
                g.setColor(b.isFromPlayer() ? Color.YELLOW : Color.PINK);
                g.fillRect(x, y, 4, 10);

            } else if (e instanceof PowerUp) {
                switch (((PowerUp) e).getType()) {
                    case HEAL -> g.setColor(Color.GREEN);
                    case SPEED -> g.setColor(Color.PINK);
                    case FIRE_RATE -> g.setColor(Color.YELLOW);
                    case SHIELD -> g.setColor(new Color(0, 255, 255));
                }
                g.fillRect(x, y, 24, 24);
            }
        }
    }
}