package HW4HeapSnekStarter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Apple {

	public static final double APPLE_SIZE = Constants.screenHeight / 50;
	Rectangle2D display;

	/**
	 * Spawns an apple at the given location
	 * 
	 * @param x
	 * @param y
	 */
	public Apple(int x, int y) {
		display = new Rectangle2D.Double(x, y, APPLE_SIZE, APPLE_SIZE);
	}

	/**
	 * Returns true if the point lies within the apple
	 * 
	 * @param p
	 * @return
	 */
	public boolean contains(Point2D p) {
		return display.contains(p);
	}

	/**
	 * Draws the apple to the screen
	 * 
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fill(display);
		g.setColor(Color.WHITE);
		g.draw(display);
	}

}
