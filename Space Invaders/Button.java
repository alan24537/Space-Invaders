// Button.java
// Jacob Gaisinsky
// Custom buttom class that takes in an image

import java.awt.*;

public class Button {
    
    private int 
    x, // x coordinate of the button
    y, // y coordinate of the button
    width, // width of the button
    height; // height of the button
    private Rectangle hitbox; // hitbox of the button
    private Image image; // image used for the button
    
    public Button(Image i, int x, int y) { // constructor
        this.x = x;
        this.y = y;
        setImage(i);
        
    }

    public boolean contains(int x, int y) { // returns true if (x, y) is in the hitbox
        return hitbox.contains(x, y);
    }

    public void setImage(Image i) { // sets the image of the button
        image = i;
        width = image.getWidth(null);
        height = image.getHeight(null);
        hitbox = new Rectangle(x, y, width, height);
    }
    public void draw(Graphics g) { // draws the button
        g.drawImage(image, x, y + 5, null);
    }
}
