package entities;

import java.awt.Color;
import java.awt.Graphics2D;

public class PowerUp extends GameObject {
    public enum Type {
        HEAL,
        WEAPON_UP,
        SHIELD,
        SPEED
    }

    private Type type;

    public PowerUp(double x, double y, Type type) {
        super(x, y);

        this.type = type;
        width = 24;
        height = 24;
        hp = 1;
        dy = 1.5;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void update() {
        super.update();

        if (y > 2000) {
            alive = false;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        switch (type) {
            case HEAL:
                g.setColor(Color.GREEN);
                break;
            case WEAPON_UP:
                g.setColor(Color.ORANGE);
                break;
            case SHIELD:
                g.setColor(Color.CYAN);
                break;
            case SPEED:
                g.setColor(Color.MAGENTA);
                break;
        }

        g.fillRect((int) x, (int) y, width, height);

        g.setColor(Color.BLACK);
        g.drawRect((int) x, (int) y, width, height);
    }
}