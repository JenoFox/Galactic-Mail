package galacticmail;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class CollidableObjects {
    // **** ABSTRACT CLASS ****
    // All collidable objects extend this
    protected BufferedImage image;          // BufferedImage object obtained from loadImage()
    protected double myX, myY;              // X and Y location
    protected double centerX, centerY;      // Center object X and Y location
    protected double direction;             // Direction the object is facing
    protected AffineTransform movement;     // Transformation object
    protected AffineTransform original = new AffineTransform(); // Default transformation to clean graphics object after it transforms the objects
    protected Shape collision;              // Shape for collision detection
    // Used to Create BufferedImage object for visual representation of the objects
    protected final BufferedImage loadImage(String pathName) {
        try {
            return ImageIO.read(new File(pathName)); // Returns the image if it is created without error
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null; // Returns null if error occurred
    }
    // Draw function used by all collidable objects except bullet, bullet overides it slightly
    protected void draw(Graphics2D g) {
        g.setTransform(original); // Make sure graphics object is clean.
        g.rotate(Math.toRadians(direction), centerX, centerY); // rotate image to direction tank is facing.
        g.drawImage(image, (int) myX, (int) myY, null); // draw the image.
        g.setTransform(original); // Clean up the transformation.
    }
    // **** ABSTRACT METHOD, every collidable object must override and move in a unique way ****
    protected abstract void move(KeyControl x);
}
