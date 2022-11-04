import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpaceInvadersPanel extends JPanel implements ActionListener {
    Timer timer;
    final int INTRO = 0, GAME = 1, END = 2;
    int currentState;
    IntroPanel intro = new IntroPanel();
    GamePanel game = new GamePanel();
    EndPanel end = new EndPanel();

    public SpaceInvadersPanel() {
        setPreferredSize(new Dimension(1200, 900));
        setBackground(Color.BLACK);

        setFocusable(true);
		requestFocus();

        add(intro);
        add(game);
        add(end);

        intro.setState(true);

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateMenuState();
        repaint();
        
    }

    public void paint(Graphics g) {
        if (currentState == INTRO) {
            intro.paint(g);
        } else if (currentState == GAME) {
            game.paint(g);
        } else if (currentState == END) {
            end.paint(g);
        }
    }

    public void updateMenuState() {
        if (statusChanged()) {
            if (intro.getState() == true) {
                currentState = INTRO;
            }
            else {
                game.setState(true);
            
                if (game.getState() == true) {
                    currentState = GAME;
                    game.startGame();
                }
                else {
                    end.setState(true);
                
                    if (end.getState() == true) {
                        game.timer.stop();
                        currentState = END;
                    }
                }
            }
        }
    }
    public boolean statusChanged() {
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
