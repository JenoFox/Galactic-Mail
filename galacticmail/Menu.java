package galacticmail;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu extends State {

    private Graphics2D g2d;
    // Buttons are created using rectangles
    private final Rectangle playButton = new Rectangle(570, 175, 100, 50);
    private final Rectangle helpButton = new Rectangle(570, 275, 100, 50);
    private final Rectangle exitButton = new Rectangle(570, 375, 100, 50);

    public Menu(Manager manager) {
        super(manager);
        manager.getMenuMusic().play(); // Menumusic is played 
    }

    @Override // Controls mouse input and updates accordingly
    public void tick() {
        // **** Play Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 570 && manager.getMouseControl().getMouseX() <= 670) {
            if (manager.getMouseControl().getMouseY() >= 175 && manager.getMouseControl().getMouseY() <= 225) {
                State.setState(manager.getArena().getGamestate()); // Sets the currenState to gameState
                manager.getMenuMusic().stop(); // Stops the menu music
                manager.getBackgroundMusic().play(); // Background score starts
            }
        }
        // **** Help Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 570 && manager.getMouseControl().getMouseX() <= 670) {
            if (manager.getMouseControl().getMouseY() >= 275 && manager.getMouseControl().getMouseY() <= 325) {
                State.setState(manager.getArena().getHelpState()); // CurrentState is set to HelpState
            }
        }
        // **** Exit Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 570 && manager.getMouseControl().getMouseX() <= 670) {
            if (manager.getMouseControl().getMouseY() >= 375 && manager.getMouseControl().getMouseY() <= 425) {
                System.exit(0); // Exits the program
            }
        }
    }
    // Draws the button and displays messages
    @Override
    public void draw(Graphics g) {
        g2d = (Graphics2D) g;
        Font f2 = new Font("arial", Font.BOLD, 30);
        g2d.setFont(f2); // Sets the font
        g2d.setColor(Color.white); // Sets the color
        g2d.drawString("PLAY", playButton.x + 10, playButton.y + 35);
        g2d.draw(playButton); // Draws playButton
        g2d.drawString("HELP", helpButton.x + 15, helpButton.y + 35);
        g2d.draw(helpButton); // Draws helpButton
        g2d.drawString("EXIT", exitButton.x + 20, exitButton.y + 35);
        g2d.draw(exitButton); // Draws exitButton
    }
}
