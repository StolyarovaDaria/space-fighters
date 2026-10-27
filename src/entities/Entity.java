package entities;

import java.awt.Rectangle;

public interface Entity {
    void update();
    Rectangle getBounds();
    boolean isAlive();
    void takeDamage(int damage);
}