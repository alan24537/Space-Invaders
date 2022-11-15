// Block.java
// Jacob Gaisinsky
// Class that contains the block object (which is used to make up the fort)

import java.awt.*;
import javax.swing.*;

public class Block {
    
    private Image block; // image of the block
    private int state; // state of the block (as it increases, the block gets more destroyed)
    private Rectangle hitbox; // hitbox of the block
    private int x, y; // coordinates of the block
    private String type; // type of the block (used for the loading the image)

    public Block(String t, int x, int y) { // constructor
        type = t;
        state = 0;
        this.x = x;
        this.y = y;
        block = new ImageIcon("assets/TileSet/" + type + state + ".png").getImage();
        hitbox = new Rectangle(x, y, block.getWidth(null), block.getHeight(null));
    }

    public void draw(Graphics g) { // draws the block
        g.drawImage(block, x, y, null);
    }

    public boolean isHit(Bullet b) { // returns true if the block is hit by the bullet
        if (b != null) {
            return hitbox.intersects(b.getHitbox());
        }
        return false;
    }

    public void damage() { // damages the block by increasing its state
        state ++;
        if (state == 4) { // if the block is destroyed, it is removed
            x = -100;
            y = -100;
            hitbox = new Rectangle(x, y, block.getWidth(null), block.getHeight(null));
        }
        else {
            block = new ImageIcon("assets/TileSet/" + type + state + ".png").getImage(); // if the block is not destroyed, it is updated to a more damaged version
        }
    }
}
