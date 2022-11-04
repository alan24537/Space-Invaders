import java.awt.*;

public class Bullet {

    int x, y, dir, speed;
    Rectangle hitbox;
    
    public Bullet(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        speed = 20;
        hitbox = new Rectangle(x, y, 5, 20);
    }

    public void move() {
        y += speed * dir;
        hitbox.translate(0, speed * dir);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 5, 20);
    }
    public boolean isOffScreen() {
        return y < 0 || y > 900;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public void remove() {
        y = -100;
    }
}
