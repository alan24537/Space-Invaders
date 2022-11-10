import java.awt.*;
import javax.swing.*;

public class Blocks {
    
    private Image block;
    private int state;
    private Rectangle hitbox;
    private int x, y;
    private String type;

    public Blocks(String t, int x, int y) {
        type = t;
        state = 0;
        this.x = x;
        this.y = y;
        block = new ImageIcon("assets/TileSet/" + type + state + ".png").getImage();
        hitbox = new Rectangle(x, y, block.getWidth(null), block.getHeight(null));
    }

    public void draw(Graphics g) {
        g.drawImage(block, x, y, null);
    }

    public boolean isHit(Bullet b) {
        if (b != null) {
            return hitbox.intersects(b.getHitbox());
        }
        return false;
    }

    public void damage() {
        state ++;
        if (state == 4) {
            x = -100;
            y = -100;
            hitbox = new Rectangle(x, y, block.getWidth(null), block.getHeight(null));
        }
        else {
            block = new ImageIcon("assets/TileSet/" + type + state + ".png").getImage();
        }
    }
}
