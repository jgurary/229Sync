package HW4HeapSnekStarter;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

/**
 * Contains functionality for drawing a Node only, has no impact on the
 * functionality of the list.
 *
 */
public class NodeDisplay {
	Rectangle2D rect;
	Node node;

	public static final double NODE_SIZE = Constants.screenHeight / 50;

	public NodeDisplay(double x, double y, Node n) {
		rect = new Rectangle2D.Double(x, y, NODE_SIZE, NODE_SIZE);
		this.node = n;
	}

	/**
	 * Draws the Node to the screen. Always fetches the latest x/y location from the
	 * Node.
	 * 
	 * @param g
	 */
	public void draw(Graphics2D g) {
		rect.setFrame(node.x, node.y, NODE_SIZE, NODE_SIZE);

		if (node.isRemoved) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.CYAN);
		}
		g.fill(rect);
		g.setColor(Color.WHITE);
		g.draw(rect);

		// The head has a placeholder value of -1, other nodes should draw their value
		if (node.value != -1) {
			g.setColor(Color.WHITE);
			Font font = new Font("Serif", Font.PLAIN, 14);
			String text = "" + node.value;
			g.setFont(font);
			// Center the text within the box
			FontMetrics metrics = g.getFontMetrics(font);
			double textX = node.x + (NODE_SIZE - metrics.stringWidth(text)) / 2;
			double textY = node.y + (NODE_SIZE - metrics.getHeight()) / 2 + metrics.getAscent();
			g.drawString(text, (int) textX, (int) textY);
		}
	}

	/**
	 * Returns true if the display contains the point (typically a click)
	 * 
	 * @param p
	 * @return
	 */
	public boolean contains(Point p) {
		return rect.contains(p);
	}
}
