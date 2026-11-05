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

    private BufferedImage imgPlayer, imgBoss, imgLaser, imgHeavy;
    private BufferedImage imgStar, imgFast;
    private BufferedImage imgHeal, imgShield, imgFire, imgSpeed, imgArmor, imgLifesteal;

    public Renderer() {
        imgPlayer = loadImage("/player.png");
        imgBoss = loadImage("/boss.png");
        imgLaser = loadImage("/laser_boss.png");
        imgHeavy = loadImage("/heavy_boss.png");

        imgStar = loadImage("/enemy_star.png");
        imgFast = loadImage("/enemy_fast.png");

        imgHeal = loadImage("/powerup_heal.png");
        imgShield = loadImage("/powerup_shield.png");
        imgFire = loadImage("/powerup_fire.png");
        imgSpeed = loadImage("/powerup_speed.png");
        imgArmor = loadImage("/powerup_armor.png");
        imgLifesteal = loadImage("/powerup_lifesteal.png");
    }

    private BufferedImage loadImage(String filename) {
        try {
            // Ищем картинку в папке src/resources
            java.io.File file = new java.io.File("src/resources/" + filename);
            if (file.exists()) {
                return ImageIO.read(file);
            }
            System.out.println("Нет файла: " + file.getAbsolutePath());
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public void draw(Graphics2D g, GameManager manager) {
        background.draw(g);
        switch (manager.getState()) {
            case PLAYING -> { drawEntities(g, manager.getEntities()); hud.draw(g, manager); }
            case PAUSED -> { drawEntities(g, manager.getEntities()); hud.draw(g, manager); menu.drawPause(g, manager); }
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
                    g.drawImage(imgPlayer, x - 20, y - 15, 80, 80, null);
                } else {
                    g.setColor(Color.CYAN); g.fillRect(x, y, 40, 40);
                }
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            } else if (e instanceof Boss) {
                if (e instanceof LaserBoss && imgLaser != null) {
                    g.drawImage(imgLaser, x, y, 270, 140, null);
                } else if (e instanceof HeavyBoss && imgHeavy != null) {
                    g.drawImage(imgHeavy, x, y, 270, 140, null);
                } else if (imgBoss != null) {
                    g.drawImage(imgBoss, x, y, 270, 140, null);
                } else {
                    g.setColor(Color.RED); g.fillRect(x, y, 270, 140);
                }

            } else if (e instanceof Enemy) {
                if (e instanceof FastEnemy && imgFast != null) {
                    g.drawImage(imgFast, x - 15, y - 15, 60, 60, null);
                } else if (imgStar != null) {
                    g.drawImage(imgStar, x - 15, y - 15, 60, 60, null);
                } else {
                    g.setColor(Color.RED); g.fillRect(x, y, 30, 30);
                }

            } else if (e instanceof Bullet) {
                Bullet b = (Bullet) e;
                g.setColor(b.isFromPlayer() ? Color.YELLOW : Color.PINK);
                g.fillRect(x, y, 4, 10);

            } else if (e instanceof PowerUp) {
                PowerUp p = (PowerUp) e;
                BufferedImage img = null;
                switch (p.getType()) {
                    case HEAL -> img = imgHeal;
                    case SHIELD -> img = imgShield;
                    case FIRE_RATE -> img = imgFire;
                    case SPEED -> img = imgSpeed;
                    case RARE_ARMOR -> img = imgArmor;
                    case RARE_LIFESTEAL -> img = imgLifesteal;
                }

                if (img != null) {
                    int size = (p.getType() == PowerUp.Type.RARE_ARMOR || p.getType() == PowerUp.Type.RARE_LIFESTEAL) ? 32 : 24;
                    g.drawImage(img, x, y, size, size, null);
                } else {

                    switch (p.getType()) {
                        case HEAL -> g.setColor(Color.GREEN);
                        case SHIELD -> g.setColor(Color.CYAN);
                        case FIRE_RATE -> g.setColor(Color.YELLOW);
                        case SPEED -> g.setColor(Color.PINK);
                        case RARE_ARMOR -> g.setColor(Color.BLUE);
                        case RARE_LIFESTEAL -> g.setColor(Color.RED);
                    }
                    g.fillRect(x, y, 24, 24);
                }
            }
        }
    }
}