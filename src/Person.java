import javax.swing.*;
import java.awt.*;
import java.net.URL;

/*
Person class is used to represent people in the game
 */
public class Person extends GameObject {
    private Image image;
    private boolean visible; // visibility variable
    private double dx = 1; // movement speed
    private boolean stopped = false;
    private int xP; // x coordinate
    private final int yP; // y coordinate

    // variable determining which direction the image is facing
    // false means it is facing left, true means it is facing right
    private boolean facingRight;

    // width and height of the image
    private final int width = 120, height = 165;

    Person(GameLoop gl, int x, int y) {
        super(gl, x, y);
        xP = x;
        yP = y;
        visible = true;
        if (gl.score>=5 && gl.score % 5 == 0)
            dx = 2;
    }

    /*
    allows the image to be loaded from an URL
     */
    public void loadImage(URL url) {

        ImageIcon ii = new ImageIcon(url);
        image = ii.getImage();
    }

    /*
    Method for painting the person's image on the screen
     */
    @Override
    public void paintComponent(Graphics2D g) {

        g.drawImage(image, getX(), getY(), width, height, this);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return xP;
    }

    public int getY() {
        return yP;
    }

    @Override
    public Rectangle getBounds(int width, int height) {

        return new Rectangle(xP + 40, yP, 60, height);
    }

    public void setFacingRight(boolean f) {
        facingRight = f;
    }

    /*
    Method for person movement
     */
    public void move() {

        if (facingRight) {
            if (xP > 700 || yP < 0)
                visible = false;


            if (stopped == false)
                xP += dx;
        } else {
            if (xP < 0 || yP < 0)
                visible = false;


            if (stopped == false)
                xP -= dx;
        }
    }

    public void stop() {

        stopped = true;
    }

    public void resume() {
        stopped = false;
    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
