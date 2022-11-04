import java.awt.*;

public class Button {
    
    private int x;
    private int y;
    private int width;
    private int height;
    private Rectangle hitbox;
    private Image image;
    
    public Button(Image i, int x, int y) {
        image = i;
        width = image.getWidth(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
        hitbox = new Rectangle(x, y, width, height);
        
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean contains(int x, int y) {
        return hitbox.contains(x, y);
    }

    public void setImage(Image i) {
        image = i;
        width = image.getWidth(null);
        height = image.getHeight(null);
        hitbox = new Rectangle(x, y, width, height);
    }
    public boolean isClicked(int x, int y) {
        if (hitbox.contains(x, y)) {
            return true;
        }
        return false;
    }
    public void draw(Graphics g) {
        g.drawImage(image, x, y + 5, null);
    }
}
