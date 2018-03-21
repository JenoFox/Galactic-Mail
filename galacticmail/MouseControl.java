package galacticmail;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseControl implements MouseListener {

    private boolean leftPressed; // Boolean to check if left button is pressed
    private int mouseX, mouseY;  // To hold x and y co-ordinates of mouse cursor

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        // Assigns the position of the mouse cursor
        mouseX = me.getX(); 
        mouseY = me.getY();
        if (me.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true; // Sets it to true, if button pressed
        } 
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        if (me.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false; // Sets it to false, if button released
        } 
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    // Returns the boolean
    public boolean isLeftPressed() {
        return leftPressed;
    }
    // Returns x position of the cursor
    public int getMouseX() {
        return mouseX;
    }
    // Returns y position of the curson
    public int getMouseY() {
        return mouseY;
    }
}
