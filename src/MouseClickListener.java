import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
custom mouse listener class
 */
public class MouseClickListener implements MouseListener {

    private final GameLoop game;

    public MouseClickListener(GameLoop game) {
        this.game = game;
    }

    /*
    it controls the pause and instruction states of the game
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (game.instructionsState) {
            game.resumeMove();
            game.pauseState = false;
            game.instructionsState = false;

        } else {

            game.stop();
            game.pauseState = true;
            game.instructionsState = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
