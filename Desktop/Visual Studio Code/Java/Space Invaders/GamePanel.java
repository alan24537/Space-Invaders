import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    final int RIGHT = 1, LEFT = -1;

    Timer timer, enemy_timer;
    boolean state = false;
    int score = 0, timer_delay = 500;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    ArrayList<int[]> dead_enemies = new ArrayList<int[]>();
    Image alien_death;
    Tank tank;
    Fort[] forts;
    boolean[] keys = new boolean[1000];
    Font font;

    public GamePanel() {
        setPreferredSize(new Dimension(1200, 900));
        setBackground(Color.BLACK);

        fillEnemyList();

        alien_death = new ImageIcon("assets/alien_death.png").getImage();

        tank = new Tank();
        forts = new Fort[4];
        forts[0] = new Fort(150);
        forts[1] = new Fort(412);
        forts[2] = new Fort(674);
        forts[3] = new Fort(936);

        InputStream is = GamePanel.class.getResourceAsStream("assets/pixel_font.ttf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(40f);
        } 
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(20, this);
        enemy_timer = new Timer(timer_delay, this);
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            move();
            removeEnemy();
            removeFort();
            removeTank();
            repaint();
        }
        else if (e.getSource() == enemy_timer) {

            if (canMove()) {
                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).move();
                }
            }
            else {
                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).changeDir();
                }
                timer_delay *= 0.75;
                enemy_timer.setDelay((int) (timer_delay));
            }
        }
    }

    public void move() {
        tank.move(keys);
        tank.shoot(keys);
        for (int i = 0; i < enemies.size(); i ++) {
            enemies.get(i).shoot();
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("SCORE:", 10, 50);
        g.drawString("LIVES:", 900, 50);
        g.setColor(Color.GREEN);
        g.drawString("" + score, 200, 50);
        g.drawString("" + tank.lives, 1100, 50);

        tank.draw(g);
        for (int i = 0; i < enemies.size(); i ++) {
            enemies.get(i).draw(g);
        }
        for (int i = 0; i < forts.length; i ++) {
            forts[i].draw(g);
        }
        for (int i = dead_enemies.size() - 1; i >= 0; i --) {
            g.drawImage(alien_death, dead_enemies.get(i)[0], dead_enemies.get(i)[1], null);
            dead_enemies.get(i)[2] --;
            if (dead_enemies.get(i)[2] == 0) {
                dead_enemies.remove(i);
            }
        }
    }

    public void fillEnemyList() {
        for (int i = 0; i < 11; i ++) {
            enemies.add(new Enemy(0, 98 + (i * (38 + 30)), 200));
        }
        for (int i = 0; i < 11; i ++) {
            enemies.add(new Enemy(1, 98 + (i * (51 + 16)), 270));
        }
        for (int i = 0; i < 11; i ++) {
            enemies.add(new Enemy(1, 94 + (i * (51 + 16)), 340));
        }
        for (int i = 0; i < 11; i ++) {
            enemies.add(new Enemy(2, 90 + (i * (56 + 12)), 405));
        }
        for (int i = 0; i < 11; i ++) {
            enemies.add(new Enemy(2, 90 + (i * (56 + 12)), 475));
        }
    }
    public boolean canMove() {
        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).canMove()) {
                return false;
            }
        }
        return true;
    }
    public void removeEnemy() {
        for (int i = enemies.size() - 1; i >= 0; i --) {
            if (enemies.get(i).isHit(tank.bullet)) {  
                dead_enemies.add(new int[] {enemies.get(i).x, enemies.get(i).y, 5});
                if (enemies.get(i).type == 0) {
                    score += 40;
                }
                else if (enemies.get(i).type == 1) {
                    score += 20;
                }
                else if (enemies.get(i).type == 2) {
                    score += 10;
                }
                enemies.remove(i);
                tank.bullet.remove();
            }
        }
    }


    public void removeFort() {
        for (int i = forts.length - 1; i >= 0; i --) {
            if (forts[i].isHit(tank.bullet)) {
                tank.bullet.remove();
            }
        }
        for (int i = enemies.size() - 1; i >= 0; i --) {
            for (int j = forts.length - 1; j >= 0; j --) {
                if (forts[j].isHit(enemies.get(i).bullet)) {
                    enemies.get(i).bullet.remove();
                }
            }
        }
    }

    public void removeTank() {
        for (int i = enemies.size() - 1; i >= 0; i --) {
            if (enemies.get(i).isShooting()) {
                if (enemies.get(i).bullet.getHitbox().intersects(tank.getHitbox())) {
                    damageTank();
                    enemies.get(i).bullet.remove();
                }
            }
        }
        if (tank.lives == 0) {
            state = false;
        }
    }

    public void damageTank() {
        tank.damage();
    }

    public void setState(boolean s) {
        this.state = s;
    }
    public boolean getState() {
        return state;
    }
    public void startGame() {
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        timer.start();
        enemy_timer.start();
    }

}
