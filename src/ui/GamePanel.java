package ui;
import core.GameManager;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel {

    private final GameManager manager;
    private final Renderer renderer;

    public GamePanel(GameManager manager, InputHandler input) {
        this.manager  = manager;
        this.renderer = new Renderer();

        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(10, 10, 40));
        setFocusable(true);
        addKeyListener(input);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        renderer.draw(g2, manager);
    }
}