// Button.java
// Jacob Gaisinsky
// Custom buttom class that takes in text and draws it on the screen

import java.awt.*;
import java.awt.image.*;

public class Button {
    
    private int 
    x, // x coordinate of the button
    y, // y coordinate of the button
    width, // width of the button
    height; // height of the button

    private Rectangle hitbox; // hitbox of the button
    private Image image; // image used for the button

    public Button(String message, int x, int y, Font font, Color color) { // constructor that takes in a message, x coordinate, y coordinate, font and colour
        this.x = x;
        this.y = y;
        setImage(message, font, color);
    }

    public boolean contains(int x, int y) { // returns true if (x, y) is in the hitbox
        return hitbox.contains(x, y);
    }

    public void setImage(String message, Font font, Color color) { // overloads the setImage method to take in a message, font and colour
        message = message.toUpperCase();
        image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB); // initialises the image (size doesn't matter)
        Graphics2D g = (Graphics2D) image.getGraphics(); // gets the graphics of the image
        g.setFont(font); 
        FontMetrics font_met = g.getFontMetrics(); // gets the font metrics of the font (used to get the width and height of the text)
        width = font_met.stringWidth(message);
        height = font_met.getHeight() - font_met.getDescent();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // initialises the image to the correct size
        g = (Graphics2D) image.getGraphics(); // gets the graphics of the image
        g.setFont(font);
        g.setColor(color);
        g.drawString(message, 0, font_met.getAscent()); // draws the text to the image
        hitbox = new Rectangle(x, y, width, height); // sets the hitbox
    }
    public void draw(Graphics g) { // draws the button
        g.drawImage(image, x, y, null);
        // g.setColor(Color.GREEN);
        // g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());
    }
}
