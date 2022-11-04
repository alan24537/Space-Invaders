import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EndPanel extends JPanel implements MouseListener {

    boolean state = false;

    public EndPanel() {
        setPreferredSize(new Dimension(1200, 1000));
        setBackground(Color.BLACK);
        addMouseListener(this);
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
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setState(boolean s) {
        this.state = s;
    }
    public boolean getState() {
        return state;
    }
}
