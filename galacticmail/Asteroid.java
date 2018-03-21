package galacticmail;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Asteroid extends CollidableObjects {

    private double speed, rotSpeed; // Asteroids have a rotation speed and movement speed, spacelike feature

    public Asteroid(double myX, double myY) {
        image = loadImage("Resources/asteroid.png");
        this.myX = myX;
        this.myY = myY;
        // Set a random moevment speed, going left or right
        if (Math.random() < .5) {
            speed = 2 * Math.random() + .5;
        } else {
            speed = -2 * Math.random() - .5;
        }
        centerX = myX + (image.getWidth() / 2);
        centerY = myY + (image.getHeight() / 2);
        direction = Math.random();
        // Set a random rotation speed, rotating left or right
        if (Math.random() < .5) {
            rotSpeed = 5 * Math.random();
        } else {
            rotSpeed = (-5) * Math.random();
        }
        collision = new Rectangle2D.Double(myX + (image.getWidth() * .2), myY + (image.getHeight() * .2), (image.getWidth() * .6), (image.getHeight()) * .6);
        movement = AffineTransform.getTranslateInstance(myY, myY);
    }

    @Override // For moving the asteroids 
    public void move(KeyControl x) {
        // If asteroids leave the screen
        // Update the X or Y coordinate based on if they left the top or bottom, or sides of the screen.
        // Used image width or height to make the animation completely smooth, without it asteroids could potentially
        // Appear already on the screen instead of looking as through they are floating from the outside of the screen
        if (myX > 1200) {
            myX = -image.getWidth() + 1;
            centerX = myX + (image.getWidth() / 2);
            collision = new Rectangle2D.Double(myX + (image.getWidth() * .2), myY + (image.getHeight() * .2), (image.getWidth() * .6), (image.getHeight()) * .6);
            movement = AffineTransform.getTranslateInstance(myY, myY);
        } else if (myY > 650) {
            myY = -image.getHeight() + 1;
            centerY = myY + (image.getHeight() / 2);
            collision = new Rectangle2D.Double(myX + (image.getWidth() * .2), myY + (image.getHeight() * .2), (image.getWidth() * .6), (image.getHeight()) * .6);
            movement = AffineTransform.getTranslateInstance(myY, myY);
        } else if (myX < -image.getWidth()) {
            myX = 1199;
            centerX = myX + (image.getWidth() / 2);
            collision = new Rectangle2D.Double(myX + (image.getWidth() * .2), myY + (image.getHeight() * .2), (image.getWidth() * .6), (image.getHeight()) * .6);
            movement = AffineTransform.getTranslateInstance(myY, myY);
        } else if (myY < -image.getHeight()) {
            myY = 649;
            centerY = myY + (image.getHeight() / 2);
            collision = new Rectangle2D.Double(myX + (image.getWidth() * .2), myY + (image.getHeight() * .2), (image.getWidth() * .6), (image.getHeight()) * .6);
            movement = AffineTransform.getTranslateInstance(myY, myY);
        }
        // **** Asteroid Movment ****
        // Update X and Y coordinate based on movement speed
        // Update Center
        // Rotate the asteroid
        // Get transformation based on how it is moving
        // Create the new shape based on the transformation
        myX = myX + speed;
        myY = myY + speed;
        centerX = centerX + speed;
        centerY = centerY + speed;
        direction = direction + rotSpeed;
        movement = AffineTransform.getTranslateInstance(speed, speed);
        collision = movement.createTransformedShape(collision);
    }
    // Used to get the center X coordinate
    public double getCenterX() {
        return centerX;
    }
    // Used to get the center Y coordinate
    public double getCenterY() {
        return centerY;
    }
    // Used to get the direction the asteroid is facing
    public double getDirection() {
        return direction;
    }
    // Used to get the X coordinate
    public double getMyX() {
        return myX;
    }
    // Used to get the Y coordinate
    public double getMyY() {
        return myY;
    }
    // For accessing the shape used for collisions
    public Shape getRec() {
        return collision;
    }
    // Used to set the speed of the asteroid
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    // Used to set the rotation speed of the asteroid
    public void setRotSpeed(int rotSpeed) {
        this.rotSpeed = rotSpeed;
    }
}
