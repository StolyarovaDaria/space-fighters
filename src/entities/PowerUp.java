package entities;

public class PowerUp extends GameObject {
    public enum Type {
        HEAL,
        FIRE_RATE,   // было WEAPON_UP
        SHIELD,
        SPEED
    }

    private final Type type;

    public PowerUp(double x, double y, Type type) {
        super(x, y);
        this.type = type;
        width = 24;
        height = 24;
        hp = 1;
        dy = 1.5;
    }

    public Type getType() { return type; }

    @Override
    public void update() {
        super.update();
        if (y > 600) alive = false;
    }
}