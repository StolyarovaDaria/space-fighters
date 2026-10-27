package ui;

import core.GameManager;
import core.GameState;
import entities.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;

public class Renderer {
    private final Starfield background = new Starfield();
    private final HUDRenderer hud = new HUDRenderer();
    private final MenuRenderer menu = new MenuRenderer();

    public void draw(Graphics2D g, GameManager manager) {
        background.draw(g);

        switch (manager.getState()) {
            case PLAYING -> {
                drawEntities(g, manager.getEntities());
                hud.draw(g, manager);
            }
            case PAUSED -> {
                drawEntities(g, manager.getEntities());
                hud.draw(g, manager);
                menu.drawPause(g);
            }
            case MENU -> menu.drawMenu(g);
            case GAME_OVER -> menu.drawGameOver(g, manager);
        }
    }

    private void drawEntities(Graphics2D g, List<Entity> entities) {
        for (Entity e : entities) {
            if (e instanceof Player) drawPlayer(g, (Player) e);
            else if (e instanceof Boss) drawBoss(g, (Boss) e);
            else if (e instanceof Enemy) drawEnemy(g, (Enemy) e);
            else if (e instanceof Bullet) drawBullet(g, (Bullet) e);
            else if (e instanceof PowerUp) drawPowerUp(g, (PowerUp) e);
        }
    }

    private void drawPlayer(Graphics2D g, Player p) {
        if (p.isInvulnerable() && (System.currentTimeMillis() / 100) % 2 == 0) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        int x = (int) p.getX();
        int y = (int) p.getY();

        g.setColor(Color.GRAY);
        g.fillPolygon(new int[]{x - 15, x + 10, x + 5, x - 30}, new int[]{y + 15, y + 15, y + 45, y + 35}, 4);
        g.fillPolygon(new int[]{x + 40, x + 65, x + 80, x + 45}, new int[]{y + 15, y + 15, y + 35, y + 45}, 4);

        g.setColor(Color.RED);
        g.fillPolygon(new int[]{x - 5, x + 5, x + 2, x - 15}, new int[]{y + 15, y + 15, y + 40, y + 30}, 4);
        g.fillPolygon(new int[]{x + 45, x + 55, x + 65, x + 52}, new int[]{y + 15, y + 15, y + 30, y + 40}, 4);

        g.setColor(Color.BLUE);
        g.fillRoundRect(x + 5, y - 10, 25, 15, 10, 10);  // Рюкзак
        g.fillRoundRect(x, y + 10, 35, 30, 10, 10);      // Тело
        g.fillRoundRect(x + 5, y + 35, 10, 15, 5, 5);    // Левая нога
        g.fillRoundRect(x + 20, y + 35, 10, 15, 5, 5);   // Правая нога

        g.setColor(new Color(150, 220, 255));
        g.fillRoundRect(x + 8, y + 5, 15, 10, 5, 5);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawBoss(Graphics2D g, Boss b) {
        int x = (int) b.getX();
        int y = (int) b.getY();

        g.setColor(new Color(180, 0, 0));
        int[] px = {x, x+20, x+40, x+60, x+80, x+100, x+120, x+140, x+160, x+130, x+80, x+30};
        int[] py = {y+30, y+10, y+10, y+20, y+10, y+10, y+20, y+10, y+30, y+70, y+90, y+70};
        g.fillPolygon(px, py, 12);
        g.setColor(Color.BLACK);
        g.drawPolygon(px, py, 12);

        g.setColor(new Color(200, 100, 0));
        g.fillPolygon(new int[]{x + 20, x + 140, x + 110, x + 50}, new int[]{y + 35, y + 35, y + 60, y + 60}, 4);

        g.setColor(new Color(220, 100, 0));
        g.fillOval(x + 10, y + 60, 15, 10);
        g.fillOval(x + 40, y + 75, 15, 10);
        g.fillOval(x + 72, y + 80, 15, 10);
        g.fillOval(x + 105, y + 75, 15, 10);
        g.fillOval(x + 135, y + 60, 15, 10);
    }

    private void drawEnemy(Graphics2D g, Enemy e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        int cx = x + 15;
        int cy = y + 15;

        if (e instanceof FastEnemy) {
            g.setColor(new Color(180, 0, 0));
            g.fillPolygon(new int[]{cx - 15, cx - 5, cx}, new int[]{cy, cy - 10, cy}, 3);
            g.fillPolygon(new int[]{cx + 15, cx + 5, cx}, new int[]{cy, cy - 10, cy}, 3);
            g.setColor(Color.RED);
            g.fillRoundRect(cx - 6, cy - 10, 12, 30, 10, 10);
            g.setColor(Color.YELLOW);
            g.fillOval(cx - 5, cy - 2, 10, 12);
            g.setColor(Color.ORANGE);
            g.fillOval(cx - 4, cy - 15, 8, 8);
        } else {
            int[] px = new int[10];
            int[] py = new int[10];
            for (int i = 0; i < 10; i++) {
                double angle = Math.toRadians(i * 36 - 90);
                int r = (i % 2 == 0) ? 18 : 7;
                px[i] = cx + (int) (Math.cos(angle) * r);
                py[i] = cy + (int) (Math.sin(angle) * r);
            }
            g.setColor(new Color(160, 0, 0));
            g.fillPolygon(px, py, 10);
            g.setColor(Color.BLACK);
            g.drawPolygon(px, py, 10);
        }
    }

    private void drawBullet(Graphics2D g, Bullet b) {
        g.setColor(b.isFromPlayer() ? Color.YELLOW : Color.PINK);
        g.fillRect((int) b.getX(), (int) b.getY(), 4, 10);
    }

    private void drawPowerUp(Graphics2D g, PowerUp p) {
        g.setColor(Color.GREEN);
        g.fillRect((int) p.getX(), (int) p.getY(), 15, 15);
    }
}