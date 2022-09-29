package LinkedListsP5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

/**
 * Contains functionality for drawing a Node only, has no impact on the
 * functionality of the list.
 *
 */
public class NodeDisplay {
	Ellipse2D ellipse;
	Node node;
	/**
	 * This connection is optional, but allows the head and tail to have an outline
	 * drawn.
	 */
	LinkedList list;

	public static final double NODE_SIZE = 50;

	public NodeDisplay(double x, double y, Node n) {
		ellipse = new Ellipse2D.Double(x, y, NODE_SIZE, NODE_SIZE);
		this.node = n;
	}

	public NodeDisplay(Node n) {
		ellipse = new Ellipse2D.Double(0, 0, NODE_SIZE, NODE_SIZE);
		this.node = n;
	}

	/**
	 * Connects the given Linked List to this display so it can display the
	 * head/tail outlines
	 * 
	 * @param list
	 */
	public void connectList(LinkedList list) {
		this.list = list;
	}

	public void moveTo(double x, double y) {
		ellipse.setFrame(x, y, NODE_SIZE, NODE_SIZE);
	}

	public void draw(Graphics2D g) {
		g.setStroke(new BasicStroke(1));
		g.setColor(node.color);
		g.fill(ellipse);
		g.setColor(Color.WHITE);
		g.draw(ellipse);
		if (node.next != null) {
			g.setColor(Color.WHITE);
			Ellipse2D next = node.next.display.ellipse;
			g.drawLine((int) ellipse.getCenterX(), (int) ellipse.getCenterY(),
					(int) next.getCenterX(), (int) next.getCenterY());
		}
		if (list != null && list.head == node) {
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(8));
			g.draw(ellipse);
		}
		if (list != null && list.tail == node) {
			g.setColor(Color.YELLOW);
			g.setStroke(new BasicStroke(6));
			g.draw(ellipse);
		}
		if (node.isSelected) {
			g.setStroke(new BasicStroke(4));
			g.setColor(Color.GREEN);
			g.draw(ellipse);
		}
	}

	/**
	 * Returns true if the display contains the point (typically a click)
	 * 
	 * @param p
	 * @return
	 */
	public boolean contains(Point p) {
		return ellipse.contains(p);
	}
}
