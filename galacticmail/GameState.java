package galacticmail;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GameState extends State {
    // Creates buttons using rectangles
    private final Rectangle menuButton = new Rectangle(540, 375, 150, 50);
    private final Rectangle exitButton = new Rectangle(540, 475, 150, 50);

    GameState(Manager manager) {
        super(manager);
    }

    @Override // Controls mouse input and updates accordingly
    public void tick() {
        // **** Menu Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 540 && manager.getMouseControl().getMouseX() <= 690) {
            if (manager.getMouseControl().getMouseY() >= 375 && manager.getMouseControl().getMouseY() <= 425) {
                manager.getBackgroundMusic().stop(); // Stops the background score
                manager.reset(); // Resets and re-initializes the elements
            }
        }
        // **** Exit Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 540 && manager.getMouseControl().getMouseX() <= 690) {
            if (manager.getMouseControl().getMouseY() >= 475 && manager.getMouseControl().getMouseY() <= 525) {
                System.exit(0); // Exits the program
            }
        }
    }
    // Draws the buttons and displays messages
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Font f2 = new Font("arial", Font.BOLD, 30);
        g2d.setFont(f2); // Font is set
        g2d.drawString("RESTART", menuButton.x + 5, menuButton.y + 35);
        g2d.draw(menuButton); // MenuButton is drawn
        g2d.drawString("EXIT", exitButton.x + 45, exitButton.y + 35);
        g2d.draw(exitButton); // Exit button is drawn 
    }
}
