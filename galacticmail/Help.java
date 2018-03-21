package galacticmail;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Help extends State {

    private Graphics2D g2d;
    // Buttons are created using rectangles
    private final Rectangle playButton = new Rectangle(30, 50, 100, 50);
    private final Rectangle exitButton = new Rectangle(1100, 50, 100, 50);

    public Help(Manager manager) {
        super(manager);
    }

    @Override // Controls mouse input and updates accordingly
    public void tick() {
        // **** Play Button **** 
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 30 && manager.getMouseControl().getMouseX() <= 110) {
            if (manager.getMouseControl().getMouseY() >= 50 && manager.getMouseControl().getMouseY() <= 100) {
                State.setState(manager.getArena().getGamestate()); // CurrentState is set to gameState
                manager.getMenuMusic().stop(); // Menu music is stopped
                manager.getBackgroundMusic().play(); // Background music starts
            }
        }
        // **** Exit Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 1100 && manager.getMouseControl().getMouseX() <= 1200) {
            if (manager.getMouseControl().getMouseY() >= 50 && manager.getMouseControl().getMouseY() <= 100) {
                System.exit(0); // Exits the program
            }
        }
    }
    // Draws the buttons and displays the messages 
    @Override
    public void draw(Graphics g) {
        g2d = (Graphics2D) g;
        Font f1 = new Font("arial", Font.BOLD, 45);
        g2d.setFont(f1); // Font is set
        g2d.setColor(Color.WHITE); // Color is set
        g2d.drawString("RULES", 510, 50);
        Font f2 = new Font("arial", Font.BOLD, 30);
        g2d.setFont(f2); // Font is set
        g2d.drawString("PLAY", playButton.x + 10, playButton.y + 35);
        g2d.draw(playButton); // Play button is drawn
        g2d.drawString("EXIT", exitButton.x + 20, exitButton.y + 35);
        g2d.draw(exitButton); // Exit button is drawn

        // **** GAME RULES **** 
        Font f3 = new Font("arial", Font.BOLD, 24);
        g2d.setFont(f3); // Sets the font
        g2d.drawString("1) THE PLAYER MUST LAND THE ROCKET ONTO A NUMBER OF INHABITED MOONS", 30, 150);
        g2d.drawString("2) LANDING MUST BE ENCLOSED ON MOON. 1000 POINTS FOR A SUCCESSFUL LANDING", 30, 180);
        g2d.drawString("3) LONGER THE ROCKET WAITS ON MOON, SCORE IS REDUCED BY 1 FOR EVERY TICK", 30, 210);
        g2d.drawString("4) AFTER EVERY LEVEL, 2 ASTEROIDS ARE ADDED", 30, 240);
        g2d.drawString("5) ROCKETS CAN ONLY BE ROTATED IN COUNTER CLOCKWISE OR CLOCKWISE DIRECTION", 30, 270);
        g2d.drawString("6) ROCKET SPEED IS GENERATED RANDOMLY AFTER EVERY LAUNCH", 30, 300);

        // **** CONTROLS ****
        g2d.setFont(f1); // Sets the font
        g2d.drawString("CONTROLS", 460, 440);
        g2d.setFont(f3);
        g2d.drawString("A:          TURN LEFT", 460, 500);
        g2d.drawString("D:          TURN RIGHT", 460, 530);
        g2d.drawString("SPACE: LAUNCH", 460, 560);
    }
}
