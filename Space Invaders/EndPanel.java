import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EndPanel extends JPanel implements KeyListener, ActionListener {

    private boolean state = false;
    private boolean[] keys = new boolean[1000];
    private Font font;
    private Timer timer;
    private String message = "";
    private String[] message_letters = {"G", "A", "M", "E", " ", "O", "V", "E", "R"};
    private int index = 0;


    public EndPanel() {
        setPreferredSize(new Dimension(1200, 900));
        setBackground(Color.BLACK);

        addKeyListener(this);

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
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 100, getWidth(), 600);
        g.setColor(Color.WHITE);
        g.setFont(font.deriveFont(100f));
        g.drawString(message, 300, 300);
        g.setFont(font.deriveFont(40f));
        g.drawString("Press Enter to Play Again", 310, 500);
        g.drawString("Press Escape to Quit", 350, 550);

    }

    public void setState(boolean s) {
        this.state = s;
    }
    public boolean getState() {
        return state;
    }
    public void startEndScreen() {
        System.out.println("Starting end screen");
        setFocusable(true);
        requestFocus();
        timer.start();
    }

    public void stopEndScreen() {
        System.out.println("Stopping end screen");
        setFocusable(false);
        timer.stop();
        message = "";
        index = 0;
        keys = new boolean[1000];
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        if (keys[KeyEvent.VK_ENTER]) {
            state = false;
        }
        else if (keys[KeyEvent.VK_ESCAPE]) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (index < message_letters.length) {
            message += message_letters[index];
            index++;
        }
        repaint();
    }
}
