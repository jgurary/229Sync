package FractalArts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class InfinitySquare extends Rectangle2D.Double {

	public static int totalChildren = 0;
	/**
	 * Children spawned per recursion wave. Very expensive to increase.
	 */
	public static final int CHILDREN_PER_RECURSION = 2;
	/**
	 * Max distance spawned away from parent. Increase to spread children from their
	 * parents.
	 */
	public static final double DISTANCE_FROM_PARENT = 300;
	/**
	 * Array containing children recursively spawned by this square. Null if none.
	 */
	public InfinitySquare[] children;
	/**
	 * True if this square has children, otherwise false.
	 */
	public boolean hasChildren;
	public Color color;
	public double size;

	/**
	 * Creates a new InfinitySquare which recursively spawns children that are 5
	 * units smaller until size is less than 5. WARNING: VERY EXPENSIVE IF SIZE > 40
	 * 
	 * @param x    - x position of this square, children spawn randomly nearby
	 * @param y    - y position of this square, children spawn randomly nearby
	 * @param size - size of the initial square, children are 5 units smaller
	 */
	public InfinitySquare(double x, double y, double size) {
		super(x, y, size, size);
		this.size = size;
		color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());

		// recursively generate children if size>5
		if (size > 5) {
			hasChildren = true;
			children = new InfinitySquare[CHILDREN_PER_RECURSION];
			for (int i = 0; i < children.length; i++) {
				children[i] = new InfinitySquare(
						x + Math.random() * DISTANCE_FROM_PARENT - DISTANCE_FROM_PARENT / 2,
						y + Math.random() * DISTANCE_FROM_PARENT - DISTANCE_FROM_PARENT / 2,
						size - 5);
				totalChildren++;
			}
		} else {
			hasChildren = false;
		}
	}

	/**
	 * Draw self and recursively draw all children.
	 * 
	 * @param g
	 */
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.draw(this);
		g.setColor(color);
		g.fill(this);

		// recursively draw children and lines to children
		if (hasChildren) {
			for (int i = 0; i < children.length; i++) {
				g.drawLine((int) this.getCenterX(), (int) this.getCenterY(),
						(int) children[i].getCenterX(), (int) children[i].getCenterY());
				children[i].draw(g);
			}
		}
	}

}
