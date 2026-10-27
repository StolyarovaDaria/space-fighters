package ui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class InputHandler implements KeyListener {
    private boolean up, down, left, right, shoot;
    private boolean pause, start;
    private boolean diff1, diff2, diff3;
    private boolean pauseConsumed = true;
    private boolean startConsumed = true;
    public boolean isUp()    { return up;    }
    public boolean isDown()  { return down;  }
    public boolean isLeft()  { return left;  }
    public boolean isRight() { return right; }
    public boolean isShoot() { return shoot; }
    public boolean isDiff1() { return diff1; }
    public boolean isDiff2() { return diff2; }
    public boolean isDiff3() { return diff3; }
    public boolean isPause() {
        if (pause && pauseConsumed) {
            pauseConsumed = false;
            return true;
        }
        return false;
    }
    public boolean isStart() {
        if (start && startConsumed) {
            startConsumed = false;
            return true;
        }
        return false;
    }
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> up    = true;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> down  = true;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> left  = true;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_SPACE -> shoot = true;
            case KeyEvent.VK_P, KeyEvent.VK_ESCAPE -> pause = true;
            case KeyEvent.VK_ENTER -> start = true;
            case KeyEvent.VK_1 -> diff1 = true;
            case KeyEvent.VK_2 -> diff2 = true;
            case KeyEvent.VK_3 -> diff3 = true;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP    -> up    = false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN  -> down  = false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT  -> left  = false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = false;
            case KeyEvent.VK_SPACE -> shoot = false;
            case KeyEvent.VK_1 -> diff1 = false;
            case KeyEvent.VK_2 -> diff2 = false;
            case KeyEvent.VK_3 -> diff3 = false;
            case KeyEvent.VK_P, KeyEvent.VK_ESCAPE -> { pause = false; pauseConsumed = true; }
            case KeyEvent.VK_ENTER -> { start = false; startConsumed = true; }

        }
    }
    public void keyTyped(KeyEvent e) {
    }
}



