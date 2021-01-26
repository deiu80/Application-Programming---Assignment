import java.awt.*;

/*
General gameObject class
containing coordinates variables and
methods for moving and displaying the object on the screen
 */

abstract class GameObject extends Component {

    public double x, y;
    public GameLoop gameLoop;

    public GameObject(GameLoop gl, double posX, double posY) {
        this.x = posX;
        this.y = posY;
        gameLoop = gl;
    }
    /*
    the below method will draw each game object differently
     */
    public abstract void paintComponent(Graphics2D g);
    /*
    helps to identify the collisions between objects
     */
    public abstract Rectangle getBounds(int width, int height);

    public abstract void move();


}
