import java.awt.*;
import javax.swing.*;

public class UFO {
    
    final int RIGHT = 1, LEFT = -1;

    private int speed, dir, score, x, y;
    private int scores[] = {50, 100, 150, 200, 300};
    private Image ufo;

    public UFO() {
        speed = Util.randint(3, 8);
        dir = Util.randint(0, 1) == 0 ? RIGHT : LEFT;
        score = scores[Util.randint(0, 4)];
        ufo = new ImageIcon("assets/ufo.png").getImage();
        x = (dir == RIGHT) ? -ufo.getWidth(null) : 1200;
        y = 100;
    }

    public void draw(Graphics g) {
        g.drawImage(ufo, x, y, null);
    }

    public void move() {
        x += speed * dir;
    }

    public boolean isHit(Bullet b) {
        if (b != null) {
            return b.getHitbox().intersects(new Rectangle(x, y, ufo.getWidth(null), ufo.getHeight(null)));
        }
        return false;
    }

    public boolean isOffScreen() {
        return (x > 1200) || (x < -ufo.getWidth(null));
    }

    // Getters and Setters

    public int getScore() {
        return score;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
