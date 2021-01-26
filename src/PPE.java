import javax.swing.*;
import java.awt.*;
import java.net.URL;

/*
PPE class is used to represent protective gear
 */
class PPE extends GameObject {
    private final int width;
    private final int height;
    private Image image;
    private String name;
    private int protectionLevel;
    private final boolean visible = true;

    PPE(GameLoop gl, int x, int y, int w, int h, String name) {
        super(gl, x, y);
        this.name = name;
        width = w;
        height = h;
        if (name.contains("Mask") || name.contains("Face shield"))
            protectionLevel = 20;
        else if (name.contains("Hand gel"))
            protectionLevel = 10;
        else if (name.equals("Gloves"))
            protectionLevel = 5;

    }

    @Override
    public void paintComponent(Graphics2D g) {
        g.drawImage(image, getX(), getY(), width, height, this);
    }


    @Override // used for collision detection
    public Rectangle getBounds(int width, int height) {
        return new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public void move() {

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public int getX() {
        return (int) super.x;
    }

    public int getY() {
        return (int) super.y;
    }

    public void setX(int x) {
        super.x = x;}

    public void setY(int y) {
        super.y = y;}

    /*
    loading the image from a URL
     */
    public void loadImage(URL url) {

        ImageIcon ii = new ImageIcon(url);
        image = ii.getImage();
    }

    /*
    loading the image from a source path/ locally
     */
    public void loadImageLoc(String sourcePath) {
        ImageIcon ii = new ImageIcon(sourcePath);
        image = ii.getImage();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getProtectionLevel() {
        return protectionLevel;
    }
}
