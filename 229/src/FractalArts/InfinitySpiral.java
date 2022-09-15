package FractalArts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class InfinitySpiral extends Ellipse2D.Double {

	public static int totalChildren = 0;
	public static int strobe = 0;
	/**
	 * Vary this for crazy effects in hypnomode
	 */
	public static int strobeIncrement = 1;
	/**
	 * Every infinity spiral, except the last one, has a child
	 */
	public InfinitySpiral child;
	/**
	 * True if has children, otherwise false.
	 */
	public boolean hasChildren;
	public Color color;
	public double size;
	public double theta;
	/**
	 * Changes the way the spiral is drawn by altering how far a child will rotate
	 * away from the parent. Remember degrees should be in radians. Try values
	 * between Math.PI /6 and Math.PI /12 for the coolest effects (in my opinion!).
	 * 
	 * Also try 90 (just 90) for a really amusing one.
	 */
	public static final double THETA_STEP = 90;

	public static final int TERMINATION_SIZE = 400;

	public static boolean STROBE_MODE = true;

	/**
	 * Creates a single particle which recursively duplicates itself into a spiral.
	 * Subsequent particles grow by 5 until reaching the termination size
	 * 
	 * @param x     - x position of the first particle
	 * @param y     - y position of the first particle
	 * @param size  - size of the first particle
	 * @param theta - used to advance the spiral using trig functions. Set to
	 *              anything at start
	 * @param color - starting color, fades to blue, then green for varying results.
	 */
	public InfinitySpiral(double x, double y, double size, double theta, Color color) {
		super(x, y, size, size);
		this.size = size;
		this.color = color;
		this.theta = theta;

		// recursively generate children if size < final termination size
		if (size < TERMINATION_SIZE) {
			hasChildren = true;
			// colors only used in non-strobe mode
			int r, g, b;
			r = (int) Math.abs(255 * Math.cos(theta));
			g = color.getGreen();
			b = color.getBlue();
			if (b + 2 <= 255) {
				b += 2;
			} else if (g + 2 <= 255) {
				g += 2;
			} else {
				b = 10;
				g = 10;
			}
			// System.out.println(r + " " + g + " " + b);
			child = new InfinitySpiral(x + Math.cos(theta) * size, y + Math.sin(theta) * size,
					size + 1, theta + THETA_STEP, new Color(r, g, b));
			totalChildren++;
			// System.out.println("Total children in the spiral: " + totalChildren);
		} else {
			// The very last Node generates no children, recursion is over!
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

		// HYPNOSTROBE MODE !!MAJOR EPILLIPSY WARNING!!
		if (STROBE_MODE) {
			int r = (int) Math.abs(255 * Math.cos(theta + strobe));
			g.setColor(new Color(r, 230, 255));
		} else {
			g.setColor(color);
		}
		g.fill(this);

		// recursively draw children
		if (hasChildren) {
			strobe += strobeIncrement;
			child.draw(g);
		}
	}

	/**
	 * Inverts the parent-child order of particles, such that the root becomes the
	 * last child, and the last child becomes the root. Call function from root node
	 * to invert entire spiral. Calls from non-root will have unpredictable results
	 * 
	 * @param parent - supply null if calling from root node
	 */
	public InfinitySpiral invertLineage(InfinitySpiral parent) {
		if (hasChildren) {
			// When we've reached the first node (which has no parent), the recursion ends
			if (parent == null) {
				hasChildren = false;
			}
			// All other Nodes simply invert so that their child is now the Node that used
			// to be their parent, then they recursively tell their former child to do the
			// same!
			InfinitySpiral tempChild = child;
			child = parent;
			return tempChild.invertLineage(this);
		} else {
			// The last Node, which has no children, becomes the new root: the only Node
			// with no children itself. Its old parent is now its child
			child = parent;
			hasChildren = true;
			return this;
		}
	}

}
