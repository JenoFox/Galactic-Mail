package galacticmail;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;

public class Arena extends JApplet implements Runnable {

    // **** Game Set Up Objects ****
    private JFrame frame;
    private Thread thread;
    private Canvas background;
    private BufferStrategy bs;
    // **** Images , Sounds, and Graphics ****
    private Graphics2D g;
    private BufferedImage arenaBackground, menuBackground, logo;
    private Sounds menuClip, backgroundClip, launch;
    // **** Inputs ****
    private KeyControl keyManager;
    private MouseControl mouseManager;
    // **** States ****
    private State gameState;
    private State menuState;
    private State helpState;
    // **** Manager ****
    private Manager manager;
    // **** Booleans ****
    private boolean isRunning;
    private boolean gameOver;
    private boolean newLevel;
    // **** GameObjects ****
    private PlayerRocket p1;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Moon> moons;
    private ArrayList<Moon> startingMoon;
    private ArrayList<Explosion> explosions;
    // **** Collision Detector ****
    private Collision cd;
    private int level;

    // **** Methods Begins ****
    // Used to initialize all data fields and objects being affected in the game
    private void initialize() {
        level = 0; // start level is 0
        // Various background images loaded
        arenaBackground = loadImage("Resources/background-" + (level % 5 + 1) + ".png");
        menuBackground = loadImage("Resources/menubg.jpeg");
        logo = loadImage("Resources/logo.png");
        menuClip = new Sounds("Resources/menubs.wav");
        backgroundClip = new Sounds("Resources/gamebs.wav");
        manager = new Manager(this); // Manages states and gives access to arena methods
        gameState = new GameState(manager); // GameState is initiliazed, when game is being played
        menuState = new Menu(manager); // Menu is initiliazed
        helpState = new Help(manager);// HelpState gives information of the game
        gameOver = false; // Game over is false when game is first started
        newLevel = true; // Start of level one considered new level to announce level number
        cd = new Collision(); // New collision detector object
        p1 = new PlayerRocket(100, 300, 0); // New player at specific coordinates
        startingMoon = new ArrayList(); // New starting moon
        startingMoon.add(new Moon(80, 280)); // Add it to the location relative to the player
        asteroids = new ArrayList(); // Asteroids arraylist
        for (int i = 0; i < 11; i++) { // Add all asteroids in, starting with 11 asteroids
            asteroids.add(new Asteroid(1000 * Math.random() + 100, 550 * Math.random() + 50));
        }
        moons = new ArrayList(); // Moons arraylist, excluding starting moon
        for (int i = 0; i < 5; i++) { // Add all moons, max number of moons is 5
            moons.add(new Moon());
        }
        explosions = new ArrayList(); // Explosions arraylist
        Moon.setMoons(moons); // Set the moons to their locations
        State.setState(menuState); // Starts at the menu state
    }
    // **** Next Level ****
    // Reinitialize the objects based on the specific level
    private void nextLevel() {
        arenaBackground = loadImage("Resources/background-" + ((level % 5) + 1) + ".png");
        p1.nextLevel(100, 300, 0); // Reinitialize the player, do not create a new one so that score is saved
        startingMoon = new ArrayList();
        startingMoon.add(new Moon(80, 280));
        asteroids = new ArrayList();
        for (int i = 0; i < 11 + 2 * level; i++) {
            asteroids.add(new Asteroid(1200 * Math.random(), 650 * Math.random()));
        }
        moons = new ArrayList();
        for (int i = 0; i < 5; i++) {
            moons.add(new Moon());
        }
        explosions = new ArrayList();
        Moon.setMoons(moons);
    }
    // Used to Create BufferedImage object for visual representation of the objects
    private BufferedImage loadImage(String pathName) {
        try {
            return ImageIO.read(new File(pathName));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    // **** Update ****
    // Updates locations of objects, whether collisions occur,
    // If next level has been reached, updates score
    private void update() {
        // If the game is not over, update all objects except asteroids
        // Asteroids will be updated even after game over to keep the background dynamic and lively
        if (!gameOver) {
            keyManager.tick(); // Update keyControl values
            // If no more moons, level is passed, go to the nextLevel
            if (moons.size() == 0) {
                level++; // Increment level counter
                p1.setScore(p1.getScore() + 5000); // Update score for passing a level
                nextLevel();
                newLevel = true;
            }
            // Check collisions between objects, collision class handles what to do with the objects
            cd.playerVmoon(p1, startingMoon, keyManager);
            cd.playerVmoon(p1, moons, keyManager);
            // If an asteroid and player collide, game is now over and player explodes
            if (cd.playerVasteroid(p1, asteroids, keyManager, explosions)) {
                gameOver = true;
            }
            p1.move(keyManager); // Move the player according to key input
            // If game is not over and shoot is not false launch the player
            if (keyManager.getShoot() && !gameOver) {
                launch = new Sounds("Resources/Launch.wav"); // Create new launch sound when launching off the moon
                launch.playonce();
                newLevel = false; // New level is false until all moons are successfully landed on
                p1.setIsShot(true); // Is shot is true to show player has been launched
                p1.move(keyManager); // Move the player 
            }
        }
        // Not inside the game over if statement, asteroids always move even if game is over
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).move(keyManager);
        }
    }
    // **** Draw ****
    // Used to display necessary items to the jframe
    // Different states the game can be in, draw according to the state
    // Handles all images and graphics objects
    private void draw() {
        bs = background.getBufferStrategy(); // Prevent flickering, drawing to a buffer first, then put onto actual screen.
        if (bs == null) { // Create bufferStrategy if it is null
            background.createBufferStrategy(2);
            return;
        }
        g = (Graphics2D) bs.getDrawGraphics(); // Get the graphics object from the bs
        g.drawImage(arenaBackground, 0, 0, 1250, 700, null); // Draw the backgrounnd
        // If state is in the game playing state, draw all game objects
        if (State.getState() == gameState) {
             // Loop to draw asteroids
            for (int i = 0; i < asteroids.size(); i++) {
                asteroids.get(i).draw(g);
            }
            // Draw starting moon
            if (startingMoon.size() > 0) {
                startingMoon.get(0).draw(g);
            }
            // Loop draw other moons
            for (int i = 0; i < moons.size(); i++) {
                moons.get(i).draw(g);
            }
            // If the game is not over draw the player
            if (!gameOver) {
                p1.draw(g);
            }
            // Animate any explosions that occur
            for (int i = 0; i < explosions.size(); i++) {
                // If the explosion counter is less than 6, else remove the explosion object
                if (explosions.get(i).getCounter() < 6) {
                    explosions.get(i).draw(g);
                    explosions.get(i).setCounter(explosions.get(i).getCounter() + 1); // Update counter so that it can draw next image
                } else {
                    explosions.remove(i);
                    i--;
                }
            }
            // **** Game Over Display ****
            // Print that the game is over with the players score in larger white text
            if (gameOver) {
                g.setColor(Color.white);
                g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 150));
                g.drawString("GAME OVER", 140, 250);
                g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 50));
                g.drawString("SCORE: " + p1.getScore(), 460, 350);
                gameState.tick();
                gameState.draw(g);
            }
            // If a new level is reached announce the level number
            if (newLevel) {
                g.setColor(Color.white);
                g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 150));
                g.drawString("LEVEL: " + (level + 1), 300, 250);
            }
        }
        // If game is in the menu state, draw the menu
        if (State.getState() == menuState) {
            g.drawImage(menuBackground, 0, 0, 1220, 680, null);
            g.drawImage(logo, 475, 50, 300, 100, null);
            menuState.draw(g);
            menuState.tick();
        }
        // If game is in the help state, draw the help menu
        if (State.getState() == helpState) {
            helpState.tick();
            g.drawImage(menuBackground, 0, 0, 1220, 680, null);
            helpState.draw(g);
        }
        // End draw
        bs.show();
        g.dispose();
    }
    // **** Frame, Thread, and Game Setup ****
    @Override // **** Game LOOP ****
    public void run() {
        initialize(); // Function initialize called to initialize all necessary objects
        int fps = 60; // Number of frames wanted per second
        double timeperupdate = 1000000000 / fps;
        double delta = 0;
        long now;
        long timer = 0;
        int ticks = 0;
        long lastTime = System.nanoTime(); // Gets system time in nano-seconds
        while (isRunning) {
            // Controls the frame rate using delta and timer. 
            now = System.nanoTime();
            delta += (now - lastTime) / timeperupdate;
            timer += now - lastTime;
            lastTime = now;
            // Unlike tank game, update and draw is used even after game over to keep the animation and background going
            if (delta >= 1) {
                update();
                draw();
                ticks++;
                delta--;
            }
            if (timer >= 1000000000) {
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    @Override // USed to create the new thread when the game is started
    public synchronized void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override // Used to stop the thread and join it once the game is no longer in its running state
    public synchronized void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    // **** Frame and Canvas SetUP ****
    // Create the new frame, canvas, KeyControl, and MouseControl objects
    // Set dimensions of frame
    // Take care of any attributes that are necessary for the frame and canvas
    private void setUp() {
        frame = new JFrame("Galactic Mail Score: "); // create new frame with title.
        background = new Canvas(); // creating canvas to put within frame.
        background.setPreferredSize(new Dimension(1200, 650)); // setting background dimensions
        keyManager = new KeyControl();
        mouseManager = new MouseControl();
        frame.addMouseListener(mouseManager); // Add mouseListener
        frame.addKeyListener(keyManager); // Add keyListener
        background.addMouseListener(mouseManager);
        background.setFocusable(false);
        frame.setSize(1200, 650);
        frame.add(background); // adds canvas to the frame.
        frame.pack(); // Pack the canvas into the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); // Make sure frame is visible
        frame.setResizable(false); // Frame is not resizeable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on closing the window
    }
    // Used to return if the user is in the game playing state
    public State getGamestate() {
        return gameState;
    }
    // Used to return if the user is in the main menu
    public State getMenu() {
        return menuState;
    }
    // Used to return if the user is in the help menu
    public State getHelpState() {
        return helpState;
    }
    // Used to return the key control object
    public KeyControl getKeyControl() {
        return keyManager;
    }
    // Used to return the mouse control object
    public MouseControl getMouseControl() {
        return mouseManager;
    }
    // Used to return the background music
    public Sounds getBackgroundMusic() {
        return backgroundClip;
    }
    // Used to return the menu sound
    public Sounds getMenuMusic() {
        return menuClip;
    }
    // **** MAIN ****
    public static void main(String[] args) {
        Arena galacticMail = new Arena();
        galacticMail.setUp();
        galacticMail.start();
    }
}