// Bullet.java
// Jacob Gaisinsky
// Class that contains the bullet object

import java.awt.*;

public class Bullet {

    private int 
    x, // x coordinate of the bullet
    y, // y coordinate of the bullet
    dir, // direction of the bullet
    speed; // speed of the bullet
    private Rectangle hitbox; // hitbox of the bullet
    
    public Bullet(int x, int y, int dir) { // constructor
        this.x = x;
        this.y = y;
        this.dir = dir;
        speed = 20;
        hitbox = new Rectangle(x, y, 5, 20);
    }

    public void move() { // moves the bullet
        y += speed * dir;
        hitbox.translate(0, speed * dir);
    }

    public void draw(Graphics g) { // draws the bullet
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 5, 20);
    }
    public boolean isOffScreen() { // returns true if the bullet is off the screen
        return y < 0 || y > 900;
    }
    public Rectangle getHitbox() { // returns the hitbox of the bullet
        return hitbox;
    }
    public void remove() { // removes the bullet (by moving it off the screen)
        y = -100;
    }
}
