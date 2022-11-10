import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RespawnPanel extends JPanel implements ActionListener {

    private Image[] respawning = {new ImageIcon("assets/tank_death0.png").getImage(), new ImageIcon("assets/tank_death1.png").getImage()};
    private int state;
    private Timer timer, ending_timer;
    private GamePanel game;
    

    public RespawnPanel(GamePanel gp) {
        setPreferredSize(new Dimension(1200, 900));
        setBackground(Color.BLACK);

        setFocusable(true);
        requestFocus();

        game = gp;
        state = 0;
        timer = new Timer(105, this);
        ending_timer = new Timer(1050, this);
    }
    
    public void draw(Graphics g) {
        game.draw(g);
        g.setColor(Color.BLACK);
        g.fillRect(game.getTank().getX(), game.getTank().getY(), 70, 37);
        g.drawImage(respawning[state / 2], game.getTank().getX(), game.getTank().getY(), null);
        state = (state + 1) % 4;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.repaint();
        if (e.getSource() == ending_timer) {
            timer.stop();
            ending_timer.stop();
            game.startTimers();
            game.setRespawning(false);
            game.setTank(new Tank(171, game.getTank().getLives() - 1));
            if (game.getTank().getLives() == 0) {
                System.out.println("GAME OVER");
                game.setQueueDeath(true);
            }
        }
    }
    public void startRespawning() {
        timer.start();
        ending_timer.start();
        setVisible(true);
    }
}
