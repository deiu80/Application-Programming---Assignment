import java.awt.*;
import java.awt.geom.Ellipse2D;

/*
Used to represent the player
 */
public class Circle extends GameObject {
    private final int radius;


    public Circle(GameLoop gl, double x, double y, int radius) {

        super(gl, x, y);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void paintComponent(Graphics2D g2) {

        Ellipse2D circle = new Ellipse2D.Double(x, y, radius, radius);
        g2.fill(circle);

    }

    @Override
    public Rectangle getBounds(int width, int height) {
        return new Rectangle((int) x, (int) y, radius, radius);
    }

    public int getX() {
        return (int) super.x;
    }

    public int getY() {
        return (int) super.y;
    }

    @Override
    public void move() {

        if (x < 0 || x >= 800 - 40) {
            gameLoop.velx = -gameLoop.velx;

        }
        if (y < 0 || y >= 600 - 40) {
            gameLoop.vely = -gameLoop.vely;
        }


        x += gameLoop.velx;
        y += gameLoop.vely;
    }


}
