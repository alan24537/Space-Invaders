// SpaceInvaders.java
// Jacob Gaisinsky
// Main class that runs the game

import javax.swing.*;

public class SpaceInvaders extends JFrame {

    private SpaceInvadersPanel game = new SpaceInvadersPanel(); // panel that contains the game
    public static final int WIDTH = 1200, HEIGHT = 900; // width and height of the game window

    public SpaceInvaders() {
        super("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(game); // adds the game panel to the frame
        pack();
        setVisible(true);
    }

    @Override
    public String toString() {
        return "Space Invaders";
    }

    public static void main(String[] args) {
        SpaceInvaders frame = new SpaceInvaders();
        System.out.println(frame); // printing because I don't like the yellow squiggly line
    }
}