package ui;

import core.GameManager;
import core.GameState;
import entities.*;
import java.awt.*;
import java.util.List;

public class Renderer {
    private final Starfield background = new Starfield();
    private final HUDRenderer hud = new HUDRenderer();
    private final MenuRenderer menu = new MenuRenderer();

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
                int cx = x + 20; int cy = y + 20;


                g.setColor(new Color(150, 150, 170));
                g.fillPolygon(new int[]{cx - 25, cx - 5, cx - 5}, new int[]{cy + 10, cy - 10, cy + 20}, 3);
                g.fillPolygon(new int[]{cx + 25, cx + 5, cx + 5}, new int[]{cy + 10, cy - 10, cy + 20}, 3);

                g.setColor(new Color(220, 30, 30));
                g.fillPolygon(new int[]{cx - 18, cx - 8, cx - 8}, new int[]{cy + 8, cy, cy + 15}, 3);
                g.fillPolygon(new int[]{cx + 18, cx + 8, cx + 8}, new int[]{cy + 8, cy, cy + 15}, 3);

                g.setColor(new Color(0, 100, 255));
                g.fillRoundRect(cx - 10, cy - 15, 20, 40, 15, 15);

                g.setColor(new Color(100, 200, 255));
                g.fillRoundRect(cx - 5, cy - 10, 10, 15, 5, 5);

                g.setColor(new Color(220, 30, 30));
                g.fillRect(cx - 8, cy + 20, 4, 8); g.fillRect(cx + 4, cy + 20, 4, 8);

                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            } else if (e instanceof Boss) {
                g.setColor(new Color(80, 80, 80));
                g.fillPolygon(new int[]{x, x + 40, x + 40, x + 10}, new int[]{y + 30, y + 10, y + 60, y + 80}, 4);
                g.fillPolygon(new int[]{x + 260, x + 220, x + 220, x + 250}, new int[]{y + 30, y + 10, y + 60, y + 80}, 4);

                g.setColor(new Color(160, 0, 0));
                g.fillRoundRect(x + 30, y + 20, 200, 60, 20, 20);

                g.setColor(new Color(200, 0, 0));
                g.fillRect(x + 100, y, 60, 40);
                g.fillPolygon(new int[]{x + 100, x + 160, x + 140, x + 120}, new int[]{y, y, y - 20, y - 20}, 4);

                g.setColor(new Color(255, 120, 0));
                g.fillRect(x + 40, y + 40, 180, 10);

                g.setColor(new Color(60, 60, 60));
                g.fillRect(x + 10, y + 50, 30, 20); g.fillRect(x + 220, y + 50, 30, 20);
                g.setColor(new Color(255, 215, 0));
                g.fillOval(x + 15, y + 65, 20, 20); g.fillOval(x + 225, y + 65, 20, 20);

                g.fillOval(x + 70, y + 75, 16, 16); g.fillOval(x + 174, y + 75, 16, 16);
                g.fillOval(x + 110, y + 85, 16, 16); g.fillOval(x + 140, y + 85, 16, 16);

            } else if (e instanceof Enemy) {
                if (e instanceof FastEnemy) {
                    int cx = x + 15, cy = y + 15;
                    g.setColor(new Color(255, 120, 0)); g.fillOval(cx - 8, cy - 20, 16, 16);
                    g.setColor(new Color(180, 0, 0));
                    g.fillPolygon(new int[]{cx - 25, cx - 10, cx - 5}, new int[]{cy - 5, cy - 10, cy + 10}, 3);
                    g.fillPolygon(new int[]{cx + 25, cx + 10, cx + 5}, new int[]{cy - 5, cy - 10, cy + 10}, 3);
                    g.fillRoundRect(cx - 10, cy - 10, 20, 15, 5, 5);
                    g.setColor(new Color(255, 215, 0));
                    g.fillPolygon(new int[]{cx - 12, cx + 12, cx + 8, cx - 8}, new int[]{cy + 5, cy + 5, cy + 25, cy + 25}, 4);
                    g.setColor(new Color(180, 0, 0));
                    g.fillPolygon(new int[]{cx - 8, cx + 8, cx}, new int[]{cy + 25, cy + 25, cy + 40}, 3);
                } else {
                    int cx = x + 15, cy = y + 15;
                    int[] px = new int[12], py = new int[12];
                    for (int i = 0; i < 12; i++) {
                        double a = Math.toRadians(i * 30 - 90);
                        int r = (i % 2 == 0) ? 25 : 10;
                        px[i] = cx + (int) (Math.cos(a) * r);
                        py[i] = cy + (int) (Math.sin(a) * r);
                    }
                    g.setColor(new Color(180, 0, 0));
                    g.fillPolygon(px, py, 12);
                }

            } else if (e instanceof Bullet) {
                Bullet b = (Bullet) e;
                if (b.isFromPlayer()) {
                    g.setColor(Color.YELLOW); g.fillRoundRect(x, y, 6, 12, 4, 4);
                    g.setColor(Color.ORANGE); g.fillRect(x + 1, y + 12, 4, 4);
                } else {
                    g.setColor(Color.RED); g.fillRoundRect(x, y, 6, 12, 4, 4);
                    g.setColor(Color.RED); g.fillRect(x + 1, y - 4, 4, 4);
                }

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
