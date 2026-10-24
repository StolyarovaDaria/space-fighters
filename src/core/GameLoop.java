package core;
import ui.GamePanel;

public class GameLoop implements Runnable {
    private GameManager manager;
    private GamePanel panel;
    private boolean running = false;
    private Thread thread;

    private final int FPS = 60;
    private final long FRAME_TIME = 1000 / FPS;

    public GameLoop(GameManager manager, GamePanel panel) {
        this.manager = manager;
        this.panel = panel;
    }

    public void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running) {
            long startTime = System.currentTimeMillis();
            manager.update();
            panel.repaint();

            long elapsed = System.currentTimeMillis() - startTime;
            long waitTime = FRAME_TIME - elapsed;
            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
