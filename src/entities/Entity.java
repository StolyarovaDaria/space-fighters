package entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface Entity {
    void update();
    void draw(Graphics2D g);
    Rectangle getBounds();
    boolean isAlive();
    void takeDamage(int damage);
}