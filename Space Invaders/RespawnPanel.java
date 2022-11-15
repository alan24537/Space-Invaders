// RespawnPanel.java
// Jacob Gaisinsky
// Panel that contains the respawn screen when the player takes damage

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RespawnPanel extends JPanel implements ActionListener {

    private Image[] respawning = {new ImageIcon("assets/tank_death0.png").getImage(), new ImageIcon("assets/tank_death1.png").getImage()}; // images for the respawning animation
    private int state; // state of the animation (used for indexing the respawning array; 0, 1 = first image, 2, 3 = second image)
    private Timer 
    timer, // timer that updates the animation (every 105 ms)
    ending_timer; // timer that ends the animation (after 1050 ms)
    private GamePanel game; // game panel so that the respawn panel can access the game panel's methods
    

    public RespawnPanel(GamePanel gp) { // constructor
        setPreferredSize(new Dimension(SpaceInvaders.WIDTH, SpaceInvaders.HEIGHT));
        setBackground(Color.BLACK);

        setFocusable(true);
        requestFocus();

        game = gp;
        state = 0;
        timer = new Timer(105, this);
        ending_timer = new Timer(1050, this);
    }
    
    public void draw(Graphics g) { // draws the respawning animation
        game.draw(g); // draws the game panel

        // draws the respawning animation
        g.setColor(Color.BLACK);
        g.fillRect(game.getTank().getX(), game.getTank().getY(), 70, 37); // draws a black rectangle over the tank to hide it
        g.drawImage(respawning[state / 2], game.getTank().getX(), game.getTank().getY(), null); // draws the respawning animation
        state = (state + 1) % 4; // increments the state of the animation
    }

    @Override
    public void actionPerformed(ActionEvent e) { // when a timer is triggered
        if (e.getSource() == ending_timer) { // if the ending timer is triggered
            // stop the respawning animation and start the game panel
            timer.stop();
            ending_timer.stop();
            state = 0;
            game.startTimers();
            game.setRespawning(false);
            game.setTank(new Tank(171, game.getTank().getLives() - 1));
            if (game.getMuted()) {
                game.getTank().getSound().mute();
            }
            if (game.getTank().getLives() == 0) { // if the player has no lives left, end the game
                game.setQueueDeath(true);
            }
        }
    }
    public void startRespawning() { // starts the respawning animation
        timer.start();
        ending_timer.start();
        setVisible(true);
    }
}
