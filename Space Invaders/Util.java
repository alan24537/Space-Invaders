// Util.java
// Jacob Gaisinsky
// Class that contains utility methods

import java.awt.*;

public class Util {

	public static int randint(int low, int high) { // returns a random integer between low and high 
		return (int)(Math.random()*(high-low+1)+low);
	}
	public static double randdouble(double low, double high) { // returns a random double between low and high
		return Math.random()*(high-low+1)+low;
	}
    public static int getCentreX(Image i, int width) { // returns the x coordinate of the image centred on the x axis   
        return (width - i.getWidth(null)) / 2;
    }
	public static Rectangle toRect(int x1, int y1, int x2, int y2) { // returns a rectangle with the top left corner at (x1, y1) and the bottom right corner at (x2, y2)
		return new Rectangle(x1, y1, x2-x1, y2-y1);
	}
	public static int centreText(String s, int width, Font font) { // returns the x coordinate of the text centred on the x axis
		FontMetrics font_met = new Canvas().getFontMetrics(font);
		return (width - font_met.stringWidth(s)) / 2;
	}
}