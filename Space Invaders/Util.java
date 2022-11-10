import java.awt.*;

public class Util {

	public static int randint(int low, int high){
		return (int)(Math.random()*(high-low+1)+low);
	}
	public static double randdouble(double low, double high){
		return Math.random()*(high-low+1)+low;
	}
    public static Point randpoint(Rectangle goodRect, Rectangle badRect) {
        double x = randdouble(goodRect.getX(), goodRect.getX() + goodRect.getWidth());
        double y = randdouble(goodRect.getY(), goodRect.getY() + goodRect.getHeight());

        while (badRect.contains(x, y) && !goodRect.contains(x, y)) {
            x = randdouble(goodRect.getX(), goodRect.getX() + goodRect.getWidth());
            y = randdouble(goodRect.getY(), goodRect.getY() + goodRect.getHeight());
        }
        return new Point((int)x, (int)y);
    }
    public static Point randpoint(Rectangle rect) {
        int x = (int) randdouble(rect.getX(), rect.getX() + rect.getWidth());
        int y = (int) randdouble(rect.getY(), rect.getY() + rect.getHeight());
        return new Point(x, y);
    }
    public static Point randpoint(Rectangle rect, int abovex, int abovey) {
        int x = 0, y = 0;
        while (Math.abs(x) < abovex && Math.abs(y) < abovey) {
            x = (int) randdouble(rect.getX(), rect.getX() + rect.getWidth());
            y = (int) randdouble(rect.getY(), rect.getY() + rect.getHeight());
        }
        return new Point(x, y);
    }
    public static int getCentreX(Image i, int width) {
        return (width - i.getWidth(null)) / 2;
    }
}