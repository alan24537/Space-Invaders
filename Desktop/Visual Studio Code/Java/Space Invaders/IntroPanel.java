import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IntroPanel extends JPanel implements MouseListener, MouseMotionListener {

    boolean state = false;
    Button start;
    Image start_image, start_image_hover, title, point_values;

    public IntroPanel() {
        setPreferredSize(new Dimension(1200, 900));
        setBackground(Color.BLACK);
    
        setFocusable(true);
        requestFocus();
        addMouseListener(this);
        addMouseMotionListener(this);

        start_image = new ImageIcon("assets/play_button.png").getImage();
        start_image_hover = new ImageIcon("assets/play_button_hover.png").getImage();
        start = new Button(start_image, Util.getCentreX(start_image, 1200), 700);

        title = new ImageIcon("assets/title.png").getImage();
        point_values = new ImageIcon("assets/point_values.png").getImage();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (start.isClicked(e.getX(), e.getY())) {
            state = false;
        }
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (start.contains(e.getX(), e.getY())) {
            start.setImage(start_image_hover);
        }
        else {
            start.setImage(start_image);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        start.draw(g);
        g.drawImage(title, Util.getCentreX(title, 1200), 100, null);
        g.drawImage(point_values, Util.getCentreX(point_values, 1200), 400, null);
    }

    public void setState(boolean s) {
        this.state = s;
    }
    public boolean getState() {
        return state;
    }
}
