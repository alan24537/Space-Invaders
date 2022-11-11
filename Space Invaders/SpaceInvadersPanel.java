// SpaceInvadersPanel.java
// Jacob Gaisinsky
// Panel that contains the all the panels need to run the game (intro, game, ending) and draws them to this panel

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpaceInvadersPanel extends JPanel implements ActionListener {

    final int INTRO = 0, GAME = 1, END = 2; // Constants for the different states of the game
    private Timer timer; // Timer that updates the game (every 20 ms)
    private int currentState; // Current state of the game
    private IntroPanel intro = new IntroPanel(); 
    private GamePanel game = new GamePanel();
    private EndPanel end = new EndPanel();

    public SpaceInvadersPanel() {
        setPreferredSize(new Dimension(1200, 900));
        setBackground(Color.BLACK);

        setFocusable(true);
		requestFocus();


        // Adds the panels to this panel
        add(intro);
        add(game);
        add(end);

        intro.setState(true); // the game starts on the intro panel so its state is set to true

        timer = new Timer(20, this); 
        timer.start(); // starts the timer
    }

    @Override
    public void actionPerformed(ActionEvent e) { // updates the game every time the timer is triggered (every 20 ms)
        updateMenuState();
        repaint();
        
    }

    public void paint(Graphics g) { // paints the current panel
        if (currentState == INTRO) {
            intro.paint(g);
        } else if (currentState == GAME) {
            game.paint(g);
        } else if (currentState == END) {
            end.paint(g);
        }
    }

    public void updateMenuState() { // updates the current state of the game
        if (statusChanged()) { // if the state of the game has changed
            if (intro.getState() == true) { // if the intro panel is active, we start the intro panel
                currentState = INTRO;
                intro.startIntroScreen();
            }
            else if (intro.getState() == false && currentState == INTRO) { // if the program thinks the intro panel is active but it is not, we stop the intro panel and start the game panel       
                game.setState(true);
                currentState = GAME;
                game.startGame();
            }
            else if (game.getState() == false && currentState == GAME) { // if the program thinks the game panel is active but it is not, we stop the game panel and start the end panel
                game.stopGame();
                end.setState(true);
                currentState = END;
                end.startEndScreen();
            }
            
            else if (end.getState() == false && currentState == END) { // if the program thinks the end panel is active but it is not, we stop the end panel and go back to the intro panel
                end.stopEndScreen();
                intro.setState(true);
                currentState = INTRO;
                intro.setState(true);
                intro.startIntroScreen();
            }
        }
    }
    public boolean statusChanged() { // returns true if the state of the game has changed
        if (!intro.getState() && currentState == INTRO) {
            return true;
        }
        if (!game.getState() && currentState == GAME) {
            return true;
        }
        if (!end.getState() && currentState == END) {
            return true;
        }
        return false;
    }

}
