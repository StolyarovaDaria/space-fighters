package ui;

import core.GameLoop;
import core.GameManager;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameWindow {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final JFrame frame;
    private final InputHandler input;
    private final GameManager manager;
    private final GamePanel panel;
    private final GameLoop loop;

    public GameWindow() {
        input = new InputHandler();
        manager = new GameManager(input, WIDTH, HEIGHT);
        panel = new GamePanel(manager, input);
        loop = new GameLoop(manager, panel);

        frame = new JFrame("Space Battle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void start() {
        panel.requestFocusInWindow();
        loop.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.start();
        });
    }
}