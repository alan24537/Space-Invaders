import java.awt.*;
import java.util.ArrayList;

public class Fort {
    
    private Blocks[] fort;
    private int x, y;

    public Fort(int x) {
        this.x = x;
        y = 700;
        fort = new Blocks[10];
        fort[0] = new Blocks("out_left"   , this.x + (28 * 0), y);
        fort[1] = new Blocks("centre"     , this.x + (28 * 1), y);
        fort[2] = new Blocks("centre"     , this.x + (28 * 2), y);
        fort[3] = new Blocks("out_right"  , this.x + (28 * 3), y);
        fort[4] = new Blocks("centre"     , this.x + (28 * 0), y + 28);
        fort[5] = new Blocks("inner_left" , this.x + (28 * 1), y + 28);
        fort[6] = new Blocks("inner_right", this.x + (28 * 2), y + 28);
        fort[7] = new Blocks("centre"     , this.x + (28 * 3), y + 28);
        fort[8] = new Blocks("centre"     , this.x + (28 * 0), y + (2 * 28));
        fort[9] = new Blocks("centre"     , this.x + (28 * 3), y + (2 * 28));
    }

    public void draw(Graphics g) {
        for (int i = 0; i < fort.length; i++) {
            fort[i].draw(g);
        }
    }

    public boolean damage(Bullet b) {
        if (b != null) {
            for (int i = 0; i < fort.length; i ++) {
                if (fort[i].isHit(b)) {
                    fort[i].damage();
                    return true;
                }
            }
        }
        return false;
    }
    public boolean damage(ArrayList<Bullet> b) {
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
