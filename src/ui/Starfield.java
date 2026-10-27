package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Starfield {

    private final int[] x = new int[100];
    private final int[] y = new int[100];
    private final int[] speed = new int[100];

    private final Random rnd = new Random();

    public Starfield() {
        for (int i = 0; i < 100; i++) {
            x[i] = rnd.nextInt(800);
            y[i] = rnd.nextInt(600);
            speed[i] = 1 + rnd.nextInt(3);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(new Color(10, 10, 40));
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            int size = (speed[i] == 1) ? 3 : (speed[i] == 2) ? 4 : 6;
            g.setColor(Color.WHITE);
            g.fillRect(x[i], y[i], size, size);
            y[i] += speed[i];
            if (y[i] > 600) {
                y[i] = 0;
                x[i] = rnd.nextInt(800);
            }
        }
    }
}