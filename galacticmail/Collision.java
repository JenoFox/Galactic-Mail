package galacticmail;

import java.util.ArrayList;

public class Collision {

    private Sounds explosion, successful; // Sounds for when a player explodes and for a successful moon landing
    // **** Player vs Asteroid ****
    // If a players rectangle intersects an asteroids rectangle and player is not on a moon
    // Create new explosion sound and play it
    // Create a new explosion and add it to the explosion arraylist
    // Returns boolean to update whether the game is over or not
    public boolean playerVasteroid(PlayerRocket p1, ArrayList<Asteroid> asteroids, KeyControl keys, ArrayList<Explosion> x) {
        for (int i = 0; i < asteroids.size(); i++) {
            if (asteroids.get(i).getRec().getBounds2D().intersects(p1.getRec().getBounds2D())
                    && !p1.getOnMoon()) {
                explosion = new Sounds("Resources/Explosion.wav");
                explosion.playonce();
                x.add(new Explosion(p1.getCenterX() - 25, p1.getCenterY() - 25, 100, 100, "explosion1_"));
                return true;
            }
        }
        return false;
    }
    // **** Player vs Moon ****
    // If the Player rectangle intersects the moon rectangle and 
    // Is within 5 units of each side of the moon it is considered a successful landing.
    // Multiple cases to check for within the function
    public void playerVmoon(PlayerRocket p1, ArrayList<Moon> x, KeyControl keys) {
        for (int i = 0; i < x.size(); i++) {
            // Successful landing
            if (p1.getRec().getBounds2D().intersects(x.get(i).getRec().getBounds2D())
                    && p1.getRec().getBounds2D().getMinX() - 5 > x.get(i).getRec().getBounds2D().getMinX()
                    && p1.getRec().getBounds2D().getMaxX() + 5 < x.get(i).getRec().getBounds2D().getMaxX()
                    && p1.getRec().getBounds2D().getMinY() - 5 > x.get(i).getRec().getBounds2D().getMinY()
                    && p1.getRec().getBounds2D().getMaxY() + 5 < x.get(i).getRec().getBounds2D().getMaxY()) {
                // Check that the player is not already on the moon
                // If player was not on the moon already, set isShot to false
                // Play successful sound
                // Check to make sure it is not the first moon
                // Add points if moon was not the first moon
                // Set image to boosters off for rocket animation
                // Set a new speed at random chance between .5 and 4.5 and update that player is on the moon
                if (!x.get(i).getOnMoon()) {
                    p1.setIsShot(false);
                    successful = new Sounds("Resources/Bonus.wav");
                    successful.playonce();
                    if (!x.get(i).getFirstMoon()) {
                        p1.setScore(p1.getScore() + 1000);
                    }
                    p1.setImage("-off");
                    p1.setSpeed((4 * Math.random() + .5));
                    p1.setOnMoon(true);
                }
                x.get(i).setOnMoon(true); // Set that the moon has a player on it
                // Decrement score each tick that the player stays on the moon if it is not the starting moon
                if (p1.getScore() > 0 && !x.get(i).getFirstMoon()) {
                    p1.setScore(p1.getScore() - 1);
                }
            }
            // If player is no longer on the moon
            // Update player on moon and remove the moon
            if (x.get(i).getOnMoon() && !p1.getRec().getBounds2D().intersects(x.get(i).getRec().getBounds2D())) {
                p1.setOnMoon(false);
                x.remove(i);
                i--;
            }
        }
    }
}
