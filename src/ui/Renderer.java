package ui;

import core.GameManager;
import core.GameState;
import entities.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
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
        int x = (int) p.getX(), y = (int) p.getY();
        Color blue = new Color(0, 80, 220), darkRed = new Color(200, 20, 20), gray = new Color(128, 128, 128);

        g.setColor(blue);    g.fillRoundRect(x + 10, y - 10, 25, 25, 20, 20);
        g.setColor(gray);    g.fillPolygon(new int[]{x - 20, x + 5, x + 5, x - 30}, new int[]{y + 20, y + 20, y + 45, y + 45}, 4);
        g.setColor(gray);    g.fillPolygon(new int[]{x + 40, x + 65, x + 75, x + 40}, new int[]{y + 20, y + 20, y + 45, y + 45}, 4);
        g.setColor(darkRed); g.fillRoundRect(x - 5, y + 10, 10, 40, 8, 8);
        g.setColor(darkRed); g.fillRoundRect(x + 35, y + 10, 10, 40, 8, 8);
        g.setColor(gray);    g.fillRect(x - 5, y + 20, 55, 30);
        g.setColor(blue);    g.fillRoundRect(x + 5, y + 45, 35, 25, 10, 10);
        g.setColor(blue);    g.fillRoundRect(x + 5, y + 65, 12, 15, 5, 5);
        g.setColor(blue);    g.fillRoundRect(x + 28, y + 65, 12, 15, 5, 5);
        g.setColor(new Color(150, 220, 255)); g.fillRoundRect(x + 13, y + 2, 19, 12, 8, 8);

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawBoss(Graphics2D g, Boss b) {
        int x = (int) b.getX(), y = (int) b.getY();

        g.setColor(new Color(180, 0, 0));
        g.fillPolygon(new int[]{x+10, x+40, x+55, x+75, x+90, x+115, x+130, x+150, x+170, x+190, x+210, x+225, x+245, x+270, x+270, x+210, x+140, x+70, x+10},
                new int[]{y+60, y+40, y+60, y+40, y+60, y+40, y+40, y+80, y+80, y+40, y+40, y+60, y+40, y+60, y+80, y+100, y+120, y+100, y+80}, 19);

        g.setColor(new Color(255, 150, 0)); g.fillPolygon(new int[]{x+35, x+245, x+215, x+65}, new int[]{y+75, y+75, y+95, y+95}, 4);

        g.setColor(new Color(255, 180, 0)); int s = 25;
        g.fillOval(x + 20, y + 90, s, s);  g.fillOval(x + 240, y + 90, s, s);
        g.fillOval(x + 70, y + 115, s, s); g.fillOval(x + 180, y + 115, s, s);
        g.fillOval(x + 125, y + 125, s, s);
    }

    private void drawEnemy(Graphics2D g, Enemy e) {
        int x = (int) e.getX(), y = (int) e.getY(), cx = x + 15, cy = y + 15;

        if (e instanceof FastEnemy) {
            g.setColor(new Color(255, 120, 0)); g.fillOval(cx - 8, cy - 20, 16, 16);
            g.setColor(new Color(180, 0, 0));
            g.fillPolygon(new int[]{cx-25, cx-10, cx-5}, new int[]{cy-5, cy-10, cy+10}, 3);
            g.fillPolygon(new int[]{cx+25, cx+10, cx+5}, new int[]{cy-5, cy-10, cy+10}, 3);
            g.fillRoundRect(cx - 10, cy - 10, 20, 15, 5, 5);
            g.setColor(new Color(255, 215, 0));
            g.fillPolygon(new int[]{cx-12, cx+12, cx+8, cx-8}, new int[]{cy+5, cy+5, cy+25, cy+25}, 4);
            g.setColor(new Color(180, 0, 0));
            g.fillPolygon(new int[]{cx-8, cx+8, cx}, new int[]{cy+25, cy+25, cy+40}, 3);
        } else {
            int[] px = new int[12], py = new int[12];
            for (int i = 0; i < 12; i++) {
                double a = Math.toRadians(i * 30 - 90);
                int r = (i % 2 == 0) ? 25 : 10;
                px[i] = cx + (int) (Math.cos(a) * r);
                py[i] = cy + (int) (Math.sin(a) * r);
            }
            g.setColor(new Color(180, 0, 0)); g.fillPolygon(px, py, 12);
        }
    }

    private void drawBullet(Graphics2D g, Bullet b) {
        g.setColor(b.isFromPlayer() ? Color.YELLOW : Color.PINK);
        g.fillRect((int) b.getX(), (int) b.getY(), 4, 10);
    }
    private void drawPowerUp(Graphics2D g, PowerUp p) {
        switch (p.getType()) {
            case HEAL -> g.setColor(Color.GREEN);
            case SPEED -> g.setColor(Color.PINK);
            case FIRE_RATE -> g.setColor(Color.YELLOW);
            case SHIELD -> g.setColor(new Color(0, 255, 255));
        }
        g.fillRect((int) p.getX(), (int) p.getY(), 24, 24);
    }
}
