package HW1CircularQueueStarter;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * A circular array that represents a queue. Supports O(1) insertion and
 * removal.
 *
 */
public class CircularArray {

	Tile[] tiles;

	/**
	 * How far each Tile's display is from the center point.
	 */
	public double radius;

	/**
	 * Create a Circular Array to hold some tiles with the given center point and
	 * radius
	 * 
	 * @param size   - number of items to support
	 * @param startX - center of the display's circle
	 * @param startY - center of the display's circle
	 * @param radius - how far Tiles spawn away from the center
	 */
	public CircularArray(int size, double startX, double startY, double radius) {
		tiles = new Tile[size];
		Tile.resetCounter();
		this.radius = radius;
		placeCircles(startX, startY);
	}

	/**
	 * Inserts a tile tile into the Circular Array
	 * 
	 * @param c
	 */
	public void push(Color c) {
		// TODO finish....
		tiles[0].setColor(Color.pink);
	}

	/**
	 * Removes the oldest entry in the Circular Array
	 * 
	 * @return
	 */
	public Color pop() {
		// TODO finish....
		Color c = tiles[0].getColor();
		tiles[0].clear();
		return c;
	}

	public boolean isFull() {
		// TODO
		return false;
	}

	/**
	 * Places the visual component of tiles relative to the given point as the
	 * center
	 * 
	 * @param startX
	 * @param startY
	 */
	public void placeCircles(double startX, double startY) {
		int size = tiles.length;
		double step = 2 * Math.PI / size;
		double theta = 0;
		for (int i = 0; i < size; i++) {
			double x = radius * Math.cos(theta);
			double y = radius * Math.sin(theta);
			// System.out.println((int) x + " " + (int) y + "theta " + theta);
			tiles[i] = new Tile(startX + x, startY + y);
			theta += step;
		}
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].draw(g);
		}
	}
}
