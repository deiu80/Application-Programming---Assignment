import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class GameLoop extends JPanel implements ActionListener {
    // dimensions
    private static final int BOX_WIDTH = 800;
    private static final int BOX_HEIGHT = 600;
    private final Timer t;

    // collections data structures for storing game objects and URLs

    private final Map<String, URL> ppeMap = new HashMap<>(); // used for storing PPE names and urls
    private final Set<URL> rightFacing = new HashSet<>();
    private final ArrayList<URL> peopleURLS = new ArrayList<>();
    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<PPE> protectiveGear = new ArrayList<>();

    // self explanatory game variables
    private boolean inGame;
    public boolean pauseState = false;
    public boolean instructionsState = false;
    private int levelOfProtection = 0;
    public int score = 0;
    private int lives = 3;
    private Circle player;
    public double velx = 0, vely = 0; // velocity variables used by the player

    /*
    In the below constructor,
    - links of images are stored ,
    - The player and PPE , Person objects are initialized
    - custom mouse and key listeners are added
    - timer is started
     */
    public GameLoop() throws MalformedURLException {

        this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        this.setBackground(Color.BLACK);

        inGame = true;

        t = new Timer(7, this);

        // THE SOURCE OF THE IMAGES IS pngall.com
        // AND ARE COPYRIGHT FREE
        ppeMap.put("Mask", new URL("http://www.pngall.com/wp-content/uploads/5/N95-Face-Mask-PNG-HD-Image.png"));

        ppeMap.put("Mask 2", new URL("http://www.pngall.com/wp-content/uploads/5/N95-Face-Mask-PNG-Image.png"));
        ppeMap.put("Hand gel", new URL("http://www.pngall.com/wp-content/uploads/5/Hand-Sanitizer-PNG-Free-Download.png"));

        ppeMap.put("Hand gel pink", new URL("http://www.pngall.com/wp-content/uploads/5/Alcohol-Hand-Sanitizer-PNG-Clipart.png"));
        ppeMap.put("Gloves", new URL("http://www.pngall.com/wp-content/uploads/2016/05/Gloves-PNG-File.png"));


        // left facing people
        peopleURLS.add(new URL("http://www.pngall.com/wp-content/uploads/2016/04/People.png"));
        peopleURLS.add(new URL("http://www.pngall.com/wp-content/uploads/2/Walk-PNG-File-Download-Free.png"));
        peopleURLS.add(new URL("http://www.pngall.com/wp-content/uploads/2/Walk-PNG-Images.png"));
        peopleURLS.add(new URL("http://www.pngall.com/wp-content/uploads/2/Walk-PNG-Image-File.png"));
        peopleURLS.add(new URL("http://www.pngall.com/wp-content/uploads/2/Walk-PNG-Image-HD.png"));

        // right facing people
        peopleURLS.add(new URL("http://www.pngall.com/wp-content/uploads/2/Walk.png"));

        peopleURLS.add(new URL("http://www.pngall.com/wp-content/uploads/2/Walk-PNG-Free-Image.png"));

        peopleURLS.add(new URL("http://www.pngall.com/wp-content/uploads/2/Walk-PNG.png"));

        rightFacing.add(new URL("http://www.pngall.com/wp-content/uploads/2/Walk-PNG-Free-Image.png"));
        rightFacing.add(new URL("http://www.pngall.com/wp-content/uploads/2/Walk.png"));
        rightFacing.add(new URL("http://www.pngall.com/wp-content/uploads/2/Walk-PNG.png"));


        initPeople();
        initPPE();

        player = new Circle(this, BOX_WIDTH / 2, BOX_HEIGHT / 2, 30);

        this.addKeyListener(new MyKeyListener(this));
        this.addMouseListener(new MouseClickListener(this));
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        t.start();


    }

    /*
    initPeople() and initPPE() methods are used for
    creating a random number of objects and adding them to their list
     */
    private void initPeople() {


        int randomNR = getRandom(4, 2);

        for (int i = 0; i < randomNR; i++) {
            // RIGHT SIDE
            //set of coordinates of objects facing left and moving to the left
            int xCoord = getRandom(BOX_WIDTH, 700);
            int yCoord = getRandom(BOX_HEIGHT - 165, 70);

            // LEFT SIDE
            // set of coordinates for objects facing right and moving to the right
            int lxCoord = getRandom(100, 1);
            int lyCoord = getRandom(BOX_HEIGHT - 165, 70);

            Person newPerson;
            int urlIndex = getRandom(peopleURLS.size());
            if (rightFacing.contains(peopleURLS.get(urlIndex))) {
                newPerson = new Person(this, lxCoord, lyCoord);
                while (peopleCollision(newPerson, people)) {
                    lxCoord = getRandom(100, 1);
                    lyCoord = getRandom(BOX_HEIGHT - 165, 70);

                    newPerson = new Person(this, lxCoord, lyCoord);

                }
                newPerson.setFacingRight(true);
            } else {
                newPerson = new Person(this, xCoord, yCoord);
                while (peopleCollision(newPerson, people)) {
                    xCoord = getRandom(BOX_WIDTH, 700);
                    yCoord = getRandom(BOX_HEIGHT - 165, 70);
                    newPerson = new Person(this, xCoord, yCoord);
                }
            }


            newPerson.loadImage(peopleURLS.get(urlIndex));
            people.add(newPerson);
        }


    }

    private void initPPE() {

        File faceImage = new File("src/face_shield.png");
        if (faceImage.exists()) {
            int xCoord = getRandom(BOX_WIDTH - 90, 30);
            int yCoord = getRandom(BOX_HEIGHT - 60, 90);
            PPE newPPE = new PPE(this, xCoord, yCoord, 60, 60, "Face shield");
            while (ppeCollision(newPPE, protectiveGear)) {
                xCoord = getRandom(BOX_WIDTH - 90, 30);
                yCoord = getRandom(BOX_HEIGHT - 60, 90);

                newPPE.setX(xCoord);
                newPPE.setY(yCoord);

            }
            newPPE.loadImageLoc("src/face_shield.png");
            protectiveGear.add(newPPE);
        }

        int nrOfPPE = getRandom(3);

        Random generators = new Random();
        Object[] types = ppeMap.keySet().toArray();

        for (int i = 0; i < nrOfPPE; i++) {
            String gearName = (String) types[generators.nextInt(types.length)];

            int xCoord = getRandom(BOX_WIDTH - 60, 30);
            int yCoord = getRandom(BOX_HEIGHT - 60, 70);
            PPE newPPE = new PPE(this, xCoord, yCoord, 60, 60, gearName);
            while (ppeCollision(newPPE, protectiveGear)) {
                xCoord = getRandom(BOX_WIDTH - 60, 30);
                yCoord = getRandom(BOX_HEIGHT - 60, 70);

                newPPE.setX(xCoord);
                newPPE.setY(yCoord);

            }
            newPPE.loadImage(ppeMap.get(gearName));

            protectiveGear.add(newPPE);
        }
    }

    /*
this and playerPeopleCollision checks for the intersection of the player with
a PPE or a person object
     */
    private void playerPpeCollison() {
        Iterator<PPE> it = protectiveGear.listIterator();
        while (it.hasNext()) {
            PPE gear = it.next();
            if (player.getBounds(player.getRadius(), player.getRadius()).intersects(gear.getBounds(gear.getWidth(), gear.getHeight()))) {
                levelOfProtection += gear.getProtectionLevel();
                it.remove();
            }
        }

    }

    private boolean playerPeopleCollision() {
        for (Person p : people)
            if (p.getBounds(60, p.getHeight()).intersects(player.getBounds(player.getRadius(), player.getRadius()))) {
                p.setVisible(false);
                return true;
            }
        return false;
    }

    /*
ppeCollision and peopleCollision
methods are for checking if a new ppe/person object position intersects another one
Used in initPPE/initPeople method , making sure there are no collisions
when creating new objects
 */
    private boolean ppeCollision(PPE newOne, ArrayList<PPE> set) {
        if (set.isEmpty())
            return false;

        for (PPE c : set)
            if (newOne.getBounds(newOne.getWidth(), newOne.getHeight()).intersects(c.getBounds(c.getWidth(), c.getHeight())))
                return true;
        return false;
    }

    private boolean peopleCollision(Person newOne, ArrayList<Person> set) {
        if (set.isEmpty())
            return false;

        for (Person c : set)
            if (newOne.getBounds(newOne.getWidth(), newOne.getHeight()).intersects(c.getBounds(c.getWidth(), c.getHeight())))
                return true;
        return false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            try {
                drawObjects(g);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }


    /*
    this method is responsible for drawing all the game objects and text on the screen
     */
    private void drawObjects(Graphics g) throws IOException {

        Graphics2D g2 = (Graphics2D) g;

        if (lives == 3)
            g2.setColor(Color.GREEN);
        else if (lives == 2)
            g2.setColor(Color.YELLOW);
        else if (lives == 1)
            g2.setColor(Color.RED);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// to make objects sharper


        if (pauseState) {
            int yct = 10;
            String text = "PAUSED \n Press space to resume";
            for (String line : text.split("\n")) {
                g2.drawString(line, 400, 30 + yct);
                yct += 20;
            }

        }
        if (instructionsState == false)
            g2.drawString("Click for instructions", 200, 30);
        else {
            g2.drawRect(100, 20, 180, 70);
            int yct = 10;
            String text = "Use arrow keys for movement \n Space - Pause\n Mouse click - Instructions\n";
            for (String line : text.split("\n")) {
                g2.drawString(line, 110, 30 + yct);
                yct += 20;
            }
        }
        g2.drawString("SCORE " + score, 700, 30);


        g2.drawString("PROTECTION LEVEL " + levelOfProtection, 650, 50);

        player.paintComponent(g2);

        g.drawString("LIVES " + lives, 700, 70);


        for (Person p : people) {
            if (p.isVisible()) {
                p.paintComponent(g2);

            }
        }


        for (PPE gear : protectiveGear) {
            if (gear.isVisible()) {
                gear.paintComponent(g2);
                g2.drawString("+ " + gear.getProtectionLevel(), gear.getX(), gear.getY() - 10);
            }
        }

        g.dispose();

    }

    /*
      this gets called when the inGame variable is false
      and it draws the player's score and top 5 scores of all time
       */
    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 17);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(msg, (BOX_WIDTH - fm.stringWidth(msg)) / 2 - 50,
                BOX_HEIGHT / 2 - 100);
        score--;
        if(score >= 0)
            g.drawString("Your score " + score + " saved in scores.txt", BOX_WIDTH / 2 - 100, BOX_HEIGHT / 2 + 50);
        else
            g.drawString("Your score " + 0 + " saved in scores.txt", BOX_WIDTH / 2 - 100, BOX_HEIGHT / 2 + 50);
        WritetToFile f = new WritetToFile();
        f.top5(g);
        g.drawString("Please close the window to exit", BOX_WIDTH / 2 - 150, BOX_HEIGHT - 30);
    }

    /*
    updatePPE and updatePeople methods will check for the respective object
    list size and will call the init method to respawn PPE and Person objects
     */
    private void updatePPE() {
        if (protectiveGear.size() <= 1) {
            // populate more gear
            initPPE();
        }

    }

    private void updatePeople() {
        if (people.size() <= 1) {
            // populate more people
            initPeople();

            score++;
            return;
        }


        Iterator<Person> it = people.listIterator();
        while (it.hasNext()) {
            Person p = it.next();

            if (p.isVisible())
                p.move();
            else
                it.remove();
        }

    }

    /*
    in case the game was stopped, once restarted the Person objects
     have to resume their movement
     */
    public void resumeMove() {
        for (Person p : people)
            p.resume();
    }

    /*
    method that repaints, updates objects and checks for collisions
     */
    public void actionPerformed(ActionEvent e) {
        inGame();
        if (!inGame)
            writeScore();

        checkLives();
        updatePPE();
        updatePeople();

        player.move();
        playerPpeCollison();

        if (playerPeopleCollision()) {
            lives--;

            if (lives != 1)
                levelOfProtection -= 50;
        }
        repaint();

    }

    /*
    the game ends when the player has 0 lives so the
    inGame variable is set to false.
     */
    private void checkLives() {
        if (lives < 1)
            inGame = false;
    }

    /*
    methods below are for changing velocity variables velx and vely
    according to the keyboard input
    these get called in the MyKeyListener class
     */
    public void up() {
        vely = -2;
        velx = 0;

    }

    public void down() {
        vely = 2;
        velx = 0;

    }

    public void left() {
        velx = -2;
        vely = 0;
    }

    public void right() {
        velx = 2;
        vely = 0;
    }

    public void stop() {
        velx = 0;
        vely = 0;
        for (Person p : people)
            p.stop();

        pauseState = true;
    }



    /*
    method that creates a WritetToFile object calls teh write function
    for the current score
     */
    private void writeScore() {

        if (levelOfProtection > 0)
            score += levelOfProtection;

        WritetToFile f = new WritetToFile();
        f.write(score);

    }

    /*
    method that checks the inGame variable and stops the Timer
     */
    private void inGame() {

        if (!inGame) {
            t.stop();
        }
    }


    /*
    2 auxiliary functions for generating a random integer
    First one is a variation with an upper limit only
    Second one has both upper and lower limits passed in as arguments
     */
    private static int getRandom(int max) {
        return (int) (Math.random() * max);
    }

    private static int getRandom(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }
}
