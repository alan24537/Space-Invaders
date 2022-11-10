// IntroPanel.java
// Jacob Gaisinsky
// Panel that contains the intro screen

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IntroPanel extends JPanel implements MouseListener, MouseMotionListener {

    private boolean state = false; // state of the panel, true if active, false if not
    private Button start; // start button
    private Image start_image, start_image_hover, title, point_values; // images for the start button and title

    public IntroPanel() { // constructor
        setPreferredSize(new Dimension(1200, 900));
        setBackground(Color.BLACK);

        addMouseListener(this);
        addMouseMotionListener(this);
        
        start_image = new ImageIcon("assets/play_button.png").getImage();
        start_image_hover = new ImageIcon("assets/play_button_hover.png").getImage();
        start = new Button(start_image, Util.getCentreX(start_image, 1200), 700); // creates the start button centred on the x axis

        title = new ImageIcon("assets/title.png").getImage();
        point_values = new ImageIcon("assets/point_values.png").getImage();
    }

    
    @Override
    public void mousePressed(MouseEvent e) { // Activates if the mouse is pressed
        if (start.isClicked(e.getX(), e.getY())) { // if the start button is clicked
            state = false; // sets the state to false, which will move from the intro panel to the game panel
        }
        
    }

    @Override
    public void mouseMoved(MouseEvent e) { // Activates if the mouse is moved
        if (start.contains(e.getX(), e.getY())) { // if the mouse is over the start button
            start.setImage(start_image_hover);
        }
        else {
            start.setImage(start_image);
        }
    }

    @Override
    public void paint(Graphics g) { // paints the intro panel
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        start.draw(g);
        g.drawImage(title, Util.getCentreX(title, 1200), 100, null);
        g.drawImage(point_values, Util.getCentreX(point_values, 1200), 400, null);
    }

    
    public void startIntroScreen() { // starts the intro screen
        System.out.println("Intro screen started");
        
        setFocusable(true);
        requestFocus();
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
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
}
