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
}