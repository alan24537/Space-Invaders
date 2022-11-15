// EndPanel.java
// Jacob Gaisinsky
// class that contains the end screen

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EndPanel extends JPanel implements KeyListener, ActionListener {

    private boolean state = false; // state of the panel
    private boolean[] keys = new boolean[1000]; // array of keys (true if pressed, false if not)
    private Font font; // font used for the text
    private Timer timer; // timer that updates every 250 ms (used for the appearing text)
    private String message = ""; // message that is displayed on the screen (appears one letter at a time)
    private String[] message_letters = {"G", "A", "M", "E", " ", "O", "V", "E", "R"}; // letters that make up the message
    private int index = 0; // index of the letter that is currently being added to the message


    public EndPanel() { // constructor
        setPreferredSize(new Dimension(SpaceInvaders.WIDTH, SpaceInvaders.HEIGHT));
        setBackground(Color.BLACK);

        addKeyListener(this);

        // loading the font
        InputStream is = GamePanel.class.getResourceAsStream("assets/pixel_font.ttf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } 
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(250, this);

    }

    @Override
    public void paint(Graphics g) { // paints the panel
        // background
        g.setColor(Color.BLACK);
        g.fillRect(0, 100, getWidth(), 600);

        // Game Over message
        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(100f));
        g.drawString(message, 300, 300);

        // play again and quit message
        g.setFont(font.deriveFont(40f));
        g.drawString("Press Enter to Play Again", 310, 500);
        g.drawString("Press Escape to Quit", 350, 550);

    }

    public void startEndScreen() { // starts the end screen
        setFocusable(true);
        requestFocus();
        timer.start();
    }
    
    public void stopEndScreen() { // stops the end screen and resets all the variables
        setFocusable(false);
        timer.stop();
        message = "";
        index = 0;
        keys = new boolean[1000];
    }
    
    @Override
    public void keyPressed(KeyEvent e) { // activates when a key is pressed
        keys[e.getKeyCode()] = true; // sets the key to true when pressed
        if (keys[KeyEvent.VK_ENTER]) { // if the enter key is pressed, the game restarts
            state = false;
        }
        else if (keys[KeyEvent.VK_ESCAPE]) { // if the escape key is pressed, the game quits
            System.exit(0);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) { // activates when a key is released
        keys[e.getKeyCode()] = false; // sets the key to false when it is released
    }
    
    @Override
    public void actionPerformed(ActionEvent e) { // adds a letter to the message every 250 ms
        if (index < message_letters.length) { // if the index is less than the length of the message
            message += message_letters[index]; // add the letter at the index to the message
            index ++; // increment the index
        }
    }

    // Getters and Setters
    
    public void setState(boolean s) {
        this.state = s;
    }
    public boolean getState() {
        return state;
    }

    // Unused methods from KeyListener

    @Override
    public void keyTyped(KeyEvent e) {}
}
