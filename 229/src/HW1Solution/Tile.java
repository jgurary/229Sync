package HW1Solution;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Tile {

	Ellipse2D.Double display;
	private Color color;
	int id;
	boolean isOccupied;
	/**
	 * For visualizing how the start/end pointers move, doesn't do anything
	 * functionally except apply a green/orange colored bordered to the start/end
	 */
	boolean isEnd, isStart;

	public static int TILE_SIZE = 50;
	public static final int TILE_SPACING = 10;
	public static int NEXT_CREATION_ID = 0;
	public static final Color DEFAULT_COLOR = Color.BLACK;

	/**
	 * Create an empty/unoccupied Tile
	 * 
	 * @param x
	 * @param y
	 */
	public Tile(double x, double y) {
		this.isOccupied = false;
		color = DEFAULT_COLOR;
		display = new Ellipse2D.Double(x, y, TILE_SIZE, TILE_SIZE);
		id = NEXT_CREATION_ID;
		NEXT_CREATION_ID++;
	}

	/**
	 * Create an occupied Tile with the given color
	 * 
	 * @param color
	 * @param x
	 * @param y
	 */
	public Tile(Color color, double x, double y) {
		this.isOccupied = true;
		this.color = color;
		display = new Ellipse2D.Double(x, y, TILE_SIZE, TILE_SIZE);
		id = NEXT_CREATION_ID;
		NEXT_CREATION_ID++;
	}

	/**
	 * Resets the ID counter back to 0
	 */
	public static void resetCounter() {
		NEXT_CREATION_ID = 0;
	}

	/**
	 * Set this tile to some color
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
		this.isOccupied = true;
	}

	/**
	 * Clear out this tile to the default color, it is now considered unoccupied
	 */
	public void clear() {
		this.color = DEFAULT_COLOR;
		this.isOccupied = false;
	}

	public Color getColor() {
		return color;
	}

	void draw(Graphics2D g) {
		g.setStroke(new BasicStroke(2));

		g.setColor(color);
		g.fill(display);
		if (isOccupied) {
			g.setColor(Color.PINK);
		} else {
			g.setColor(Color.CYAN);
		}
		g.drawString("" + id, (int) display.getCenterX(), (int) display.getCenterY());
		g.draw(display);

		// For visualizing how the start/end pointers move
		if (isEnd) {
			g.setStroke(new BasicStroke(8));
			g.setColor(Color.RED);
			g.draw(display);
		}

		if (isStart) {
			g.setStroke(new BasicStroke(4));
			g.setColor(Color.ORANGE);
			g.draw(display);
		}
	}

}
