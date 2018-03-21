package galacticmail;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class PlayerRocket extends CollidableObjects {

    private double speed;               // Rotation and movement speed
    private boolean isShot, onMoon;     // Booleans to check whether the player is on a moon and if it has been launched
    private int score;                  // Player score

    public PlayerRocket(double myX, double myY, double direction) {
        image = loadImage("Resources/spaceship-off.png");
        this.myX = myX;
        this.myY = myY;
        speed = 3;  // Starting speed
        centerX = myX + (image.getWidth() / 2);
        centerY = myY + (image.getHeight() / 2);
        this.direction = direction;
        collision = new Rectangle2D.Double(myX, myY, image.getWidth(), image.getHeight());
        isShot = false; // Starts on a moon, so launch is false
        onMoon = true;  // Starts on a moon, so on moon is true
        score = 0;      // Starts with 0 score
    }

    @Override // For moving the player rocket based on which keys are pressed
    public void move(KeyControl x) {
        // **** Rotating the player left or right ****
        // Update direction the player is facing
        // Get the transformation
        // Create the new shape from the transformation
        if (x.getL()) {
            direction = direction - speed;
            if (direction < -360) { // If it becomes less than -360 set it back to 0.
                direction = direction + 360;
            }
            movement = AffineTransform.getRotateInstance(Math.toRadians(-speed), centerX, centerY);
            collision = movement.createTransformedShape(collision);
        }
        if (x.getR()) {
            direction = direction + speed;
            if (direction > 360) {
                direction = direction - 360;
            }
            movement = AffineTransform.getRotateInstance(Math.toRadians(speed), centerX, centerY);
            collision = movement.createTransformedShape(collision);
        }
        // **** Launching Player ****
        // If isShot is true
        // Launch the rocket and keep shoot key as false
        // If player goes off the screen place him on the opposite side with same
        // Y coordinate if player went off the side and X coordinate if player went off top or bottom
        // Update the center location
        // Update image to create animation, if launched boosters will appear, if on moon no boosters
        if (isShot) {
            launchRocket();
            x.setShoot();
            if (myX > 1200) {
                myX = 0;
                collision = new Rectangle2D.Double(myX, myY, image.getWidth(), image.getHeight());
            } else if (myY > 650) {
                myY = 0;
                collision = new Rectangle2D.Double(myX, myY, image.getWidth(), image.getHeight());
            } else if (myX < 0) {
                myX = 1199;
                collision = new Rectangle2D.Double(myX, myY, image.getWidth(), image.getHeight());
            } else if (myY < 0) {
                myY = 649;
                collision = new Rectangle2D.Double(myX, myY, image.getWidth(), image.getHeight());
            }
            centerX = myX + (image.getWidth() / 2);
            centerY = myY + (image.getHeight() / 2);
            this.setImage("");
        }
    }
    // Used to launch the rocket depending on which direction its facing. Very similar to bullet class in tankgame
    public void launchRocket() {
        myX = myX + speed * Math.cos(Math.toRadians(direction));
        myY = myY + speed * Math.sin(Math.toRadians(direction));
        centerX = centerX + speed * Math.cos(Math.toRadians(direction));
        centerY = centerY + speed * Math.sin(Math.toRadians(direction));
        movement = AffineTransform.getTranslateInstance(speed * Math.cos(Math.toRadians(direction)), speed * Math.sin(Math.toRadians(direction)));
        collision = movement.createTransformedShape(collision);
    }
    // Returns the center X coordinate
    public double getCenterX() {
        return centerX;
    }
    // Returns the center Y coordinate
    public double getCenterY() {
        return centerY;
    }
    // Returns the direction the player is facing
    public double getDirection() {
        return direction;
    }
    // Returns the X coordinate
    public double getMyX() {
        return myX;
    }
    // Returns the Y coordinate
    public double getMyY() {
        return myY;
    }
    // For accessing the shape used for collisions
    public Shape getRec() {
        return collision;
    }
    // To set the isShot boolean of playerRocket object when launching off a moon
    public void setIsShot(boolean isShot) {
        this.isShot = isShot;
    }
    // Used for setting a new speed for the player rocket before launching
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    // Used to change the image to create an animation
    public void setImage(String attribute) {
        image = loadImage("Resources/spaceship" + attribute + ".png");
    }
    // Returns the onMoon boolean to check if player is on a moon or not
    public boolean getOnMoon() {
        return onMoon;
    }
    // Used for setting when a player lands on a moon or launches off one
    public void setOnMoon(boolean onMoon) {
        this.onMoon = onMoon;
    }
    // Used for getting the player score
    public int getScore() {
        return score;
    }
    // Used for updating the player score when interacting with moons
    public void setScore(int score) {
        this.score = score;
    }
    // Used for reinitializing the player when the next level is reached
    public void nextLevel(double myX, double myY, double direction) {
        this.myX = myX;
        this.myY = myY;
        this.speed = 3;
        this.centerX = myX + (image.getWidth() / 2);
        this.centerY = myY + (image.getHeight() / 2);
        this.direction = direction;
        collision = new Rectangle2D.Double(myX, myY, image.getWidth(), image.getHeight());
        isShot = false;
        onMoon = true;
    }
}
