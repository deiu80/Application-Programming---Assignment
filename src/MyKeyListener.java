import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
custom key listener class
 */
class MyKeyListener implements KeyListener {
    private final GameLoop game;

    MyKeyListener(GameLoop g) {
        game = g;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (game.pauseState == false) {
            if (code == KeyEvent.VK_UP) {
                this.game.up();
            } else if (code == KeyEvent.VK_DOWN) {
                this.game.down();
            } else if (code == KeyEvent.VK_LEFT) {
                this.game.left();
            } else if (code == KeyEvent.VK_RIGHT) {
                this.game.right();
            } else if (code == KeyEvent.VK_SPACE) {
                game.stop();
                game.pauseState = true;
            }
        } else if (code == KeyEvent.VK_SPACE) {
            game.resumeMove();
            game.pauseState = false;
            game.instructionsState = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
