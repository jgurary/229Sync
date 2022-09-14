package StacksQueuesP3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Utilities for displaying the demo data structure visually
 * 
 * @author Doctor Fish
 *
 */
public class ArrayDemoDisplay implements Drawable {

	private ArrayLike demo;
	private ArrayList<Rectangle2D> displays;
	public int size;
	private double xStart;
	private double yStart;
	private JPanel panel;

	private Color borderColor = Color.black;
	private Color textColor = Color.black;

	/**
	 * Creates a new demo display, supporting the given number of tiles. Tiles will
	 * spawn with random colors in the default location
	 * 
	 * @param size
	 * @param panel
	 */
	public ArrayDemoDisplay(int size, ArrayLike demo, JPanel panel) {
		displays = new ArrayList<Rectangle2D>();
		this.size = size;
		this.demo = demo;
		this.panel = panel;
		defaultPosition();
	}

	@Override
	public boolean contains(Point p) {
		for (int i = 0; i < displays.size(); i++) {
			if (displays.get(i).contains(p)) {
				return true;
			}
		}
		return true;
	}

	@Override
	public void moveTo(double x, double y) {
		double xCurr = x;
		double yCurr = y;
		for (int i = 0; i < displays.size(); i++) {
			displays.get(i).setFrame(xCurr, yCurr, Constants.ARRAY_DEMO_NODE_SIZE,
					Constants.ARRAY_DEMO_NODE_SIZE);
			if (xCurr + Constants.ARRAY_DEMO_NODE_SIZE + Constants.ARRAY_DEMO_NODE_SIZE < panel
					.getWidth()) {
				xCurr += Constants.PADDING_SMALL + Constants.ARRAY_DEMO_NODE_SIZE;
			} else {
				xCurr = x;
				yCurr += Constants.PADDING_SMALL + Constants.ARRAY_DEMO_NODE_SIZE;
			}
		}

	}

	@Override
	public void translate(double x, double y) {
		double xCurr = displays.get(0).getX() + x;
		double yCurr = displays.get(0).getY() + y;
		for (int i = 0; i < displays.size(); i++) {
			displays.get(i).setFrame(xCurr, yCurr, Constants.ARRAY_DEMO_NODE_SIZE,
					Constants.ARRAY_DEMO_NODE_SIZE);
			if (xCurr + Constants.ARRAY_DEMO_NODE_SIZE + Constants.ARRAY_DEMO_NODE_SIZE < panel
					.getWidth()) {
				x += Constants.PADDING_SMALL + Constants.ARRAY_DEMO_NODE_SIZE;
			} else {
				xCurr = x;
				yCurr += Constants.PADDING_SMALL + Constants.ARRAY_DEMO_NODE_SIZE;
			}
		}
	}

	@Override
	public void draw(Graphics2D g) {
		// Borders that show the limit of the array
		g.setStroke(new BasicStroke(8));
		Rectangle2D disp = displays.get(0);
		g.drawLine((int) disp.getX() - 5, (int) disp.getY(), (int) disp.getX() - 5,
				(int) (disp.getY() + disp.getHeight()));
		int k = size - 1;
		disp = displays.get(k);
		g.drawLine((int) (disp.getX() + disp.getWidth() + 5), (int) disp.getY(),
				(int) (disp.getX() + disp.getWidth() + 5), (int) (disp.getY() + disp.getHeight()));

		// The entries and the array and their values
		for (int i = 0; i < displays.size(); i++) {
			disp = displays.get(i);
			g.setStroke(new BasicStroke(2));
			if (demo.hasValue(i)) {
				g.setColor(demo.get(i).color);
				g.fill(disp);
				g.setColor(borderColor);
				g.draw(disp);
				g.setColor(textColor);
				// Change the font
				Font font = new Font("Serif", Font.PLAIN, 20);
				String text = demo.getString(i) + "";
				g.setFont(font);
				// Center the text within the box
				FontMetrics metrics = g.getFontMetrics(font);
				double x = disp.getX() + (disp.getWidth() - metrics.stringWidth(text)) / 2;
				double y = disp.getY() + (disp.getHeight() - metrics.getHeight()) / 2
						+ metrics.getAscent();
				g.drawString(text, (int) x, (int) y);
			}

			// Give the next spot a larger colored outline
			if (i == demo.getNextSpot()) {
				g.setStroke(new BasicStroke(4));
				g.setColor(Color.pink);
				g.draw(disp);
			}
		}
	}

	/**
	 * Moves the display to the default starting position
	 */
	public void defaultPosition() {
		xStart = panel.getWidth();
		yStart = Constants.PADDING_SMALL;

		double x = xStart;
		double y = yStart;
		displays.clear();
		for (int i = 0; i < size; i++) {
			displays.add(new Rectangle2D.Double(x, y, Constants.ARRAY_DEMO_NODE_SIZE,
					Constants.ARRAY_DEMO_NODE_SIZE));
			if (x + Constants.ARRAY_DEMO_NODE_SIZE + Constants.ARRAY_DEMO_NODE_SIZE < panel
					.getWidth()) {
				x += Constants.PADDING_SMALL + Constants.ARRAY_DEMO_NODE_SIZE;
			} else {
				x = xStart;
				y += Constants.PADDING_SMALL + Constants.ARRAY_DEMO_NODE_SIZE;
			}
		}
	}

	/**
	 * If the display is not big enough to accommodate the underlying structure, the
	 * we add some more rectangles. This growth matches the growth of an ArrayList
	 * as it gets larger.
	 * 
	 * @param currentDemoSize
	 */
	public void updateSize(int currentDemoSize) {
		if (currentDemoSize > size) {
			size = (int) (size * 1.5);
			defaultPosition();
		}
	}

}
