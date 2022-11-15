// IntroPanel.java
// Jacob Gaisinsky
// Panel that contains the intro screen

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class IntroPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    private boolean 
    state = false, // state of the panel, true if active, false if not
    inInstructions; // true if the instructions are being displayed

    private Button 
    start, // start button 
    instructions, // instructions button
    exitInstructions; // exit instructions button
    private Image title, point_values; // images for the start button and title
    private Font font;
    private Rectangle[] hiding_rects = new Rectangle[12]; // rectangles that hides the different parts of the point values image so that it can be animated
    private int hiding_rects_index; // what index of the hiding_rects array to draw up to
    private Timer timer; // timer that updates the hiding rectangles (every 250 ms)

    public IntroPanel() { // constructor
        setPreferredSize(new Dimension(SpaceInvaders.WIDTH, SpaceInvaders.HEIGHT));
        setBackground(Color.BLACK);

        addMouseListener(this);
        addMouseMotionListener(this);
        

        InputStream is = GamePanel.class.getResourceAsStream("assets/pixel_font.ttf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(50f);
        } 
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        start = new Button("PLAY SPACE INVADERS", Util.centreText("PLAY SPACE INVADERS", SpaceInvaders.WIDTH, font), 675, font, Color.WHITE); // creates the start button

        instructions = new Button("INSTRUCTIONS", Util.centreText("INSTRUCTIONS", SpaceInvaders.WIDTH, font), 750, font, Color.WHITE); // creates the instructions button

        exitInstructions = new Button("BACK", 10, 10, font, Color.WHITE); // creates the exit instructions button

        title = new ImageIcon("assets/title.png").getImage();
        point_values = new ImageIcon("assets/point_values.png").getImage();

        hiding_rects_index = 0;

        fillHideRects(); // adding the hiding rectangles to the hiding_rects array


        timer = new Timer(250, this);
        timer.setInitialDelay(1000);
        timer.start();
    }

    @Override
    public void mousePressed(MouseEvent e) { // Activates if the mouse is pressed
        if (!inInstructions) { // if the instructions are not being displayed
            if (start.contains(e.getX(), e.getY() + 5)) { // if the start button is clicked
                state = false; // sets the state to false, which will move from the intro panel to the game panel
            }
            if (instructions.contains(e.getX(), e.getY() + 5)) { // if the instructions button is clicked
                inInstructions = true;
            }
        }
        else {
            if (exitInstructions.contains(e.getX(), e.getY() + 5)) { // if the exit instructions button is clicked
                inInstructions = false;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) { // Activates if the mouse is moved
        if (!inInstructions) { // if the instructions are not being displayed
            if (start.contains(e.getX(), e.getY() + 5)) { // if the mouse is over the start button
                start.setImage("PLAY SPACE INVADERS", font, Color.GREEN);
            }
            else {
                start.setImage("PLAY SPACE INVADERS", font, Color.WHITE);
            }
            if (instructions.contains(e.getX(), e.getY() + 5)) { // if the mouse is over the instructions button
                instructions.setImage("INSTRUCTIONS", font, Color.GREEN);
            }
            else {
                instructions.setImage("INSTRUCTIONS", font, Color.WHITE);
            }
        }
        else {
            if (exitInstructions.contains(e.getX(), e.getY() + 5)) { // if the mouse is over the exit instructions button
                exitInstructions.setImage("BACK", font, Color.GREEN);
            }
            else {
                exitInstructions.setImage("BACK", font, Color.WHITE);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) { // Activates if the timer is triggered (every 250 ms)
        hiding_rects_index ++; // moves to the next hiding rectangle evectively removing one rectangle from the screen
        if (hiding_rects_index == 12) { // if there are no more hiding rectangles to draw
            timer.stop(); 
        }
        
    }

    @Override
    public void paint(Graphics g) { // paints the intro panel
        if (!inInstructions) { // if the instructions are not being displayed
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            start.draw(g);
            instructions.draw(g);
            g.drawImage(title, Util.getCentreX(title, 1200), 100, null);
            g.drawImage(point_values, Util.getCentreX(point_values, 1200), 400, null);
            g.setColor(Color.BLACK);
            for (int i = hiding_rects_index; i < 12; i ++) { // draws the hiding rectangles starting from hiding_rects_index
                g.fillRect(hiding_rects[i].x, hiding_rects[i].y, hiding_rects[i].width, hiding_rects[i].height);
            }
        }
        else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(font.deriveFont(30f));
            g.drawString("Use the arrow keys to move the player left and right", Util.centreText("Use the arrow keys to move the player left and right", SpaceInvaders.WIDTH, g.getFont()), 200);
            g.drawString("Use the space bar to shoot", Util.centreText("Use the space bar to shoot", SpaceInvaders.WIDTH, g.getFont()), 250);
            g.drawString("Use the escape key to pause the game", Util.centreText("Use the escape key to pause the game", SpaceInvaders.WIDTH, g.getFont()), 300);
            g.drawString("Press \"m\" to mute all sounds", Util.centreText("Press \"m\" to mute all sounds", SpaceInvaders.WIDTH, g.getFont()), 350);
            exitInstructions.draw(g);
        }
    }

    public void fillHideRects() { // fills the hiding rectangles array
        // first line
        hiding_rects[0] = Util.toRect(420, 380, 530, 447);
        hiding_rects[1] = Util.toRect(530, 380, 576, 442);
        hiding_rects[2] = Util.toRect(576, 380, 776, 442);

        // second line
        hiding_rects[3] = Util.toRect(420, 447, 530, 510);
        hiding_rects[4] = Util.toRect(530, 447, 576, 508);
        hiding_rects[5] = Util.toRect(576, 447, 776, 508);

        // third line
        hiding_rects[6] = Util.toRect(420, 510, 530, 578);
        hiding_rects[7] = Util.toRect(530, 510, 576, 576);
        hiding_rects[8] = Util.toRect(576, 510, 776, 576);

        // fourth line
        hiding_rects[9] = Util.toRect(420, 578, 530, 631);
        hiding_rects[10] = Util.toRect(530, 578, 576, 630);
        hiding_rects[11] = Util.toRect(576, 578, 776, 630);
    }

    
    public void startIntroScreen() { // starts the intro screen
        setFocusable(true);
        requestFocus();
        timer.start();
    }

    public void stopIntroScreen() { // stops the intro screen
        timer.stop(); // stops the timer
        hiding_rects_index = 0; // resets the hiding_rects_index
    }
    
    // Getters and setters 
    
    public void setState(boolean s) {
        this.state = s;
    }
    public boolean getState() {
        return state;
    }

    // Unused methods from MouseListener and MouseMotionListener

    @Override
    public void mouseClicked(MouseEvent e) {System.out.println(e.getX() + ", " + (e.getY() + 5));}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}


}
