import java.awt.*;
import javax.swing.*;

public class Enemy {

    final int UP = -1, DOWN = 1;

    Image[] enemy = new Image[2];
    int state = 0, dir, x, y, type;
    double speed;
    Rectangle hitbox;
    Bullet bullet;
    
    public Enemy(int type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
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
            if (Math.random() <= 0.001) {
                bullet = new Bullet(x + enemy[state].getWidth(null) / 2, y + enemy[state].getHeight(null), DOWN);
            }
            if (isShooting()) {
                bullet.move();
                if (bullet.isOffScreen()) {
                    bullet = null;
                }
            }
        }
    }
    public boolean isShooting() {
        return !(bullet == null);
    }
}
