package galacticmail;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyControl implements KeyListener {

    private final boolean[] keys;           // Array to store all the keys in the keyboard
    private boolean left, right, shoot;     // Player control keys
    // Used to update key value upon each tick in the games update loop.
    public void tick() {
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        shoot = keys[KeyEvent.VK_SPACE];
    }
    // Constructor to create new array of keys
    public KeyControl() {
        keys = new boolean[256];
    }

    @Override // When a key is pressed its boolean value is set to true.
    public void keyPressed(KeyEvent ke) {
        keys[ke.getKeyCode()] = true;
    }

    @Override // Not used
    public void keyTyped(KeyEvent ke) {
    }

    @Override // When a key is released its boolean value is set to false.
    public void keyReleased(KeyEvent ke) {
        keys[ke.getKeyCode()] = false;
    }
    // **** Getters for encapsulated data fields ****
    // Used mainly for moving the tank
    // get functions without a number, i.e. getF() are for player 1 keys
    // get functions with a number, i.e. getF2() are for player 2 keys
    public boolean getL() {
        return left;
    }

    public boolean getR() {
        return right;
    }
    
    public boolean getShoot(){
        return shoot;
    }
    // Used to set the launch key to false if rocket is in flight
    public void setShoot() {
        shoot = false;
    }
}
