package galacticmail;

import java.awt.Graphics;

public abstract class State {

    protected Manager manager; // Manager object is created
    private static State currentState = null; //currentState is set to null initially
    // Manager object is initialized 
    public State(Manager manager) {
        this.manager = manager;
    }
    // Setter for currentState
    public static void setState(State state) {
        currentState = state;
    }
    // Getter for currentState
    public static State getState() {
        return currentState;
    }
    // Tick is used to update the states and have mouse control
    public abstract void tick();
    // After the updates, objects are drawn accordingly
    public abstract void draw(Graphics g);
}
