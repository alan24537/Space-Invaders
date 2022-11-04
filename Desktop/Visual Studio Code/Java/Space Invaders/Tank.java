import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Tank {

    final int UP = -1, DOWN = 1;

    Image tank;
    int x, y, speed, lives;
    Rectangle hitbox;
    Bullet bullet;
    
    public Tank() {
        tank = new ImageIcon("assets/tank.png").getImage();

        x = Util.getCentreX(tank, 1200);
        y = 850;
        speed = 10;
        lives = 3;

        hitbox = new Rectangle(x, y, tank.getWidth(null), tank.getHeight(null));

        bullet = null;
    }

    public void move(boolean[] keys) {
        if (keys[KeyEvent.VK_LEFT] && x > 10) {
            x -= speed;
        }
        if (keys[KeyEvent.VK_RIGHT] && x < 1200 - tank.getWidth(null) - 10) {
            x += speed;
        }
        if (isShooting()) {
            bullet.move();
            if (bullet.isOffScreen()) {
                bullet = null;
            }
        }
    }

    public void draw(Graphics g) {
        g.drawImage(tank, x, y, null);
        if (isShooting()) {
            bullet.draw(g);
        }
    }

    public void shoot(boolean[] keys) {
        if (keys[KeyEvent.VK_SPACE]) {
            if (!isShooting()) {
                bullet = new Bullet(x + tank.getWidth(null) / 2, y, UP);
            }
        }
    }
    public boolean isShooting() {
        return !(bullet == null);
    }

    public boolean isHit(ArrayList<Bullet> bullets) {
        for (int i = 0; i < bullets.size(); i ++) {
            if (hitbox.intersects(bullets.get(i).getHitbox())) {
                return true;
            }
        }
        return false;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public void damage() {
        lives --;
    }
}
