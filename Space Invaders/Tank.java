import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Tank {

    final int UP = -1, DOWN = 1;

    private Image tank;
    private int x, y, speed, lives;
    private Rectangle hitbox;
    private Bullet bullet;
    private SoundEffect shoot;
    
    public Tank(int x, int l) {
        tank = new ImageIcon("assets/tank.png").getImage();

        this.x = x;
        y = 850;
        speed = 10;
        lives = l;

        hitbox = new Rectangle(x, y, tank.getWidth(null), tank.getHeight(null));

        shoot = new SoundEffect("assets/sound_effects/tank_shoot.wav");

        bullet = null;
    }

    public void move(boolean[] keys) {
        if (keys[KeyEvent.VK_LEFT] && x > 10) {
            x -= speed;
        }
        if (keys[KeyEvent.VK_RIGHT] && x < 1200 - tank.getWidth(null) - 10) {
            x += speed;
        }
        hitbox.translate( x - (int) hitbox.getX(), 0);
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
                shoot.play();
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

    // Getters and Setters

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getLives() {
        return lives;
    }
    public Bullet getBullet() {
        return bullet;
    }
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
}
