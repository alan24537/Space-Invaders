// UFO.java
// Jacob Gaisinsky
// Class that contains the UFO object

import java.awt.*;
import javax.swing.*;

public class UFO {
    
    final int RIGHT = 1, LEFT = -1; // Constants for the direction of the UFO

    private int 
    speed, // speed of the UFO
    dir, // direction of the UFO
    score, // score of the UFO
    x, // x coordinate of the UFO
    y; // y coordinate of the UFO
    private int scores[] = {50, 100, 150, 200, 300}; // array of possible scores of the UFO
    private Image ufo; // image of the UFO

    public UFO() { // constructor
        speed = Util.randint(3, 8); // sets the speed of the UFO to a random integer between 3 and 8
        dir = Util.randint(0, 1) == 0 ? RIGHT : LEFT; // sets the direction of the UFO to a random direction
        score = scores[Util.randint(0, 4)]; // sets the score of the UFO to a random score from the scores array
        ufo = new ImageIcon("assets/ufo.png").getImage();
        x = (dir == RIGHT) ? -ufo.getWidth(null) : 1200; // sets the x coordinate of the UFO to the left or right side of the screen depending on the direction
        y = 100;
    }

    public void draw(Graphics g) { // draws the UFO
        g.drawImage(ufo, x, y, null);
    }

    public void move() { // moves the UFO
        x += speed * dir;
    }

    public boolean isHit(Bullet b) { // returns true if the UFO is hit by a bullet
        if (b != null) { // if there is a bullet
            return b.getHitbox().intersects(new Rectangle(x, y, ufo.getWidth(null), ufo.getHeight(null)));
        }
        return false;
    }

    public boolean isOffScreen() { // returns true if the UFO is off the screen
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
