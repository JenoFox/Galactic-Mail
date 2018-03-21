package galacticmail;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Explosion {

    private final ArrayList<BufferedImage> images; // Every explosion is an arraylist of images, not just a single image
    private final double myX, myY;      // X and Y locations
    private final int sizeX, sizeY;     // X and Y dimensions of images
    private int counter;                // Counter used to tick through each image to create an animation

    public Explosion(double myX, double myY, int sizeX, int sizeY, String image) {
        this.myX = myX;
        this.myY = myY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        counter = 0; // Starting image is 0
        images = new ArrayList(6);
        for (int i = 0; i < 6; i++) { // For loop to load images into the ArrayList
            images.add(loadImage("Resources/" + image + (i + 1) + ".png")); // i + 1 due to image names starting with 1.
        }
    }
    // Used to Create BufferedImage object for visual representation of the objects
    private BufferedImage loadImage(String pathName) {
        try {
            return ImageIO.read(new File(pathName)); // Return image if it is created without error
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null; // Return null if error occurred
    }
    // Used for graphics to draw explosion image based on the counter, counter is updated within draw in arena to draw correct image sequence.
    protected void draw(Graphics2D g) {
        g.drawImage(images.get(counter), (int) (myX - images.get(counter).getWidth() / 2), (int) (myY - images.get(counter).getHeight() / 2), sizeX, sizeY, null);
    }
    // For updating counter in draw 
    public void setCounter(int counter) {
        this.counter = counter;
    }
    // For accessing counter
    public int getCounter() {
        return counter;
    }
}
