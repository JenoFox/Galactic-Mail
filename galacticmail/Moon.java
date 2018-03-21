package galacticmail;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Moon extends CollidableObjects {

    private boolean onMoon;             // Boolean to check whether a player is on the moon
    private final boolean firstMoon;    // Boolean to check whether the moon is the starting moon
    // **** TWO Constructors ****
    // Default constructor for all moons except the start moon
    public Moon() {
        double random = Math.random(); // Random value to check which image to use
        // 3 different images have been used to add visual harmony
        if (random < .33) {
            image = loadImage("Resources/moon-1.png");
        } else if (random >= .33 && random < .66) {
            image = loadImage("Resources/moon-2.png");
        } else {
            image = loadImage("Resources/moon-3.png");
        }
        // X and Y location does not matter, will be updated in setMoons function
        myX = 0;
        myY = 0;
        centerX = myX + (image.getWidth() / 2);
        centerY = myY + (image.getHeight() / 2);
        onMoon = false; // No moon starts with a player on it except first one
        firstMoon = false; // No moon is the first moon, except the first moon
        direction = 0;
    }
    // 2 parameter constructor used for setting the first moon
    // Main difference is its x and y location will be set to the player rockets starting position,
    // And both boolean values will be true to start it, no points will be given for this moon
    public Moon(double myX, double myY) {
        image = loadImage("Resources/moon-2.png");
        this.myX = myX;
        this.myY = myY;
        centerX = myX + (image.getWidth() / 2);
        centerY = myY + (image.getHeight() / 2);
        direction = 0;
        collision = new Rectangle2D.Double(myX, myY, image.getWidth(), image.getHeight());
        movement = AffineTransform.getTranslateInstance(myY, myY);
        onMoon = true;
        firstMoon = true;
    }

    @Override // Not used, moons do not move
    public void move(KeyControl x) {
    }
    // Used to set the moons in a random Y locations but evenly spread out in the X direction throughout the level
    public static void setMoons(ArrayList<Moon> x) {
        double xPlacement = 200, yPlacement;
        for (int i = 0; i < x.size(); i++) {
            yPlacement = 600 * Math.random();
            x.get(i).setX(xPlacement * (i + 1)); // i + 1 due to i starting at 0
            x.get(i).setY(yPlacement);
        }
    }
    // Used to set the X location of the moon and create new shape transformation based on new location
    private void setX(double myX) {
        this.myX = myX;
        collision = new Rectangle2D.Double(myX, myY, image.getWidth(), image.getHeight());
        movement = AffineTransform.getTranslateInstance(myY, myY);
    }
    // Used to set the Y location of the moon and create new shape transformation based on new location
    private void setY(double myY) {
        this.myY = myY;
        collision = new Rectangle2D.Double(myX, myY, image.getWidth(), image.getHeight());
        movement = AffineTransform.getTranslateInstance(myY, myY);
    }
    // For accessing the shape used for collisions
    public Shape getRec() {
        return collision;
    }
    // Returns onMoon boolean value
    public boolean getOnMoon() {
        return onMoon;
    }
    // Used to set whether a player is on the moon or not
    public void setOnMoon(boolean onMoon) {
        this.onMoon = onMoon;
    }
    // Used to check whether the moon is the starting moon
    public boolean getFirstMoon() {
        return firstMoon;
    }
}
