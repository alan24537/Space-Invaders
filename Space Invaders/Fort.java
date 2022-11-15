// Fort.java
// Jacob Gaisinsky
// Class that contains the fort object (which is made up of a bunch of Blocks)

import java.awt.*;
import java.util.ArrayList;

public class Fort {
    
    private Block[] fort; // array of blocks that make up the fort
    private int x, y; // coordinates of the fort

    public Fort(int x) { // constructor
        this.x = x;
        y = 700;

        // loading the different blocks of the fort
        fort = new Block[10];
        fort[0] = new Block("out_left"   , this.x + (28 * 0), y);
        fort[1] = new Block("centre"     , this.x + (28 * 1), y);
        fort[2] = new Block("centre"     , this.x + (28 * 2), y);
        fort[3] = new Block("out_right"  , this.x + (28 * 3), y);
        fort[4] = new Block("centre"     , this.x + (28 * 0), y + 28);
        fort[5] = new Block("inner_left" , this.x + (28 * 1), y + 28);
        fort[6] = new Block("inner_right", this.x + (28 * 2), y + 28);
        fort[7] = new Block("centre"     , this.x + (28 * 3), y + 28);
        fort[8] = new Block("centre"     , this.x + (28 * 0), y + (2 * 28));
        fort[9] = new Block("centre"     , this.x + (28 * 3), y + (2 * 28));
    }

    public void draw(Graphics g) { // draws the fort
        for (int i = 0; i < fort.length; i++) {
            fort[i].draw(g);
        }
    }

    public boolean damage(Bullet b) { // damages the block that was hit by the bullet (returns true if the bullet hit a block)
        if (b != null) { // if there is a bullet
            for (int i = 0; i < fort.length; i ++) { 
                if (fort[i].isHit(b)) { // if the bullet hit a block, we damage the block and return true
                    fort[i].damage();
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean damage(ArrayList<Bullet> b) { // overloading the damage method to take an ArrayList of bullets
        for (int i = 0; i < b.size(); i ++) {
            for (int j = 0; j < fort.length; j ++) {
                if (fort[j].isHit(b.get(i))) {
                    fort[j].damage();
                    return true;
                }
            }
        }
        return false;
    }
}
