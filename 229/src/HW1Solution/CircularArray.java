package HW1Solution;

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
	 * Queues always add new values to the end
	 */
	int end = 0;
	/**
	 * Queues always remove values from the start (oldest first), moving towards the
	 * end
	 */
	int start = 0;

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
		tiles[0].isEnd = true;
		tiles[0].isStart = true;
	}

	/**
	 * Inserts a color into the Circular Array
	 * 
	 * @param c
	 */
	public void push(Color c) {
		if (end + 1 < tiles.length) {
			tiles[end].setColor(c);
			tiles[end].isEnd = false;
			end++;
			tiles[end].isEnd = true;
		} else if (end == tiles.length - 1) {
			tiles[end].setColor(c);
			tiles[end].isEnd = false;
			end = 0;
			tiles[end].isEnd = true;
		}

	}

	/**
	 * Removes the oldest entry in the Circular Array
	 * 
	 * @return - the removed color
	 */
	public Color pop() {
		Color c = null;
		if (start + 1 < tiles.length) {
			c = tiles[start].getColor();
			tiles[start].clear();
			tiles[start].isStart = false;
			start++;
			tiles[start].isStart = true;
		} else if (start == tiles.length - 1) {
			c = tiles[start].getColor();
			tiles[start].clear();
			tiles[start].isStart = false;
			start = 0;
			tiles[start].isStart = true;
		}
		return c;
	}

	/**
	 * Returns true if the queue is full
	 * 
	 * @return
	 */
	public boolean isFull() {
		if (end == start && tiles[start].isOccupied) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the true if the queue has no items
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		if (end == start && !tiles[start].isOccupied) {
			return true;
		} else {
			return false;
		}
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
