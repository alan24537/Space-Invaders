import java.awt.*;
import javax.swing.*;

public class Enemy {

    final int UP = -1, DOWN = 1;

    private Image[] enemy = new Image[2];
    private int state = 0, dir, x, y, type;
    private double speed, fire_rate;
    private Rectangle hitbox;
    private Bullet bullet;
    
    public Enemy(int type, int x, int y, double f_r) {
        this.type = type;
        this.x = x;
        this.y = y;
        fire_rate = f_r;
        speed = 10;
        dir = 1;
        bullet = null;
        enemy[0] = new ImageIcon("assets/alien" + type + "_0.png").getImage();
        enemy[1] = new ImageIcon("assets/alien" + type + "_1.png").getImage();
        hitbox = new Rectangle(x, y, enemy[0].getWidth(null), enemy[0].getHeight(null));
    }
      
    
    public void draw(Graphics g) {
        g.drawImage(enemy[state], x, y, null);
        if (isShooting()) {
            bullet.draw(g);
        }
    }
    public boolean canMove() {
        return x + (speed * dir) < 1200 - 20 - enemy[state].getWidth(null) && x + (speed * dir) > 0 + 15;
    }
    public void move() {
        x += speed * dir;
        state = (state + 1) % 2;
        hitbox.translate((int) speed * dir, 0);
    }
    public void changeDir() {
        dir *= -1;
        y += 20;
        state = (state + 1) % 2;
        hitbox.translate(0, 20);
    }

    public int getDir() {
        return dir;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }
    public boolean isHit(Bullet b) {
        if (b != null) {
            return hitbox.intersects(b.getHitbox());
        }
        return false;
    }

    public void shoot() {
        if (type == 0) {
            if (isShooting()) {
                bullet.move();
                if (bullet.isOffScreen()) {
                    bullet = null;
                }
            }
            else if (Math.random() <= fire_rate) {
                bullet = new Bullet(x + enemy[state].getWidth(null) / 2, y + enemy[state].getHeight(null), DOWN);
            }
        }
    }
    public boolean isShooting() {
        return !(bullet == null);
    }

    public boolean isBelowForts() {
        return y + enemy[state].getHeight(null) > 784;
    }

    // Getters and Setters

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getType() {
        return type;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public Bullet getBullet() {
        return bullet;
    }
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
}
