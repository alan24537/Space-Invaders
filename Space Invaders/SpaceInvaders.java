// SpaceInvaders.java
// Jacob Gaisinsky
// Main class that runs the game

import javax.swing.*;

public class SpaceInvaders extends JFrame {
    SpaceInvadersPanel game = new SpaceInvadersPanel(); // panel that contains the game

    public SpaceInvaders() {
        super("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(game); // adds the game panel to the frame
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SpaceInvaders frame = new SpaceInvaders();
    }
}

// TODO 
// Make all variables private yayayyaayya done  1/2/2020
// Organize / comment code
// Fix the shooting sound effect
// Add background music
// Add high score screen
// Add instuctions screen
// Add pause screen

// 1. Header Comments
//     At the top of each program you should indicate:
//         i. Program Name (actual file name)
//         ii. Author's Name (That's you)
//         iii. What the program does. This last point should grow somewhat as the program grows. If your program finds the tax on a meal "finds tax on a meal" is probably good enough. 
//              If your program manages the inventory and Point of Sale for a store "manages the inventory and Point of Sale" is not enough. You need to explain exactly what features 
//              are included.

// 2. Comments for Variables
//     All key variables in your program need to be explained. You don't need to explain what the variable physically holds (integer, double, String) but you do need to clearly explain what 
//     the value logically represents. 
//     e.g.int shipX;// x position of the good guys ship in pixels.
//     The key is to short and concise but at the same time very specific. In this example I make it clear that it is the good guy I'm talking about and that the number is in pixels 
//     (as opposed to some grid or even sector value.) The more widely used a variable is in your program the more important it is to have good comments. If a variable is declared 
//     inside a 5 line function and it's use is obvious don't bother adding a comment.

// 3. Comments for Methods
//     All methods, even one-liners need some comments to explain how the method is used. Explain what the method does, what each parameter is used for, and what the method returns.

// 4. Comments for Classes
//     All classes, need header comments to explain why they exist and what they do.
    
// 5. Comments for Complicated Sections of Code
//     Whenever you do something even remotely tricky add a comment to explain what you are doing so that when you look at it later or someone else looks at it, it makes sense.
