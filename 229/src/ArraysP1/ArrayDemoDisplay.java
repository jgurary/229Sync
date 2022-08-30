package ArraysP1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * Utilities for displaying the demo data structure visually
 * 
 * @author Doctor Fish
 *
 */
public class ArrayDemoDisplay implements Drawable {

	private ArrayDemo demo;
	private Rectangle2D[] displays;
	private double xStart;
	private double yStart;
	private JPanel panel;

	private Color bodyColors[];
	private Color borderColor = Color.black;
	private Color textColor = Color.black;

	/**
	 * Creates a new demo display, supporting the given number of tiles. Tiles will
	 * spawn with random colors in the default location
	 * 
	 * @param size
	 * @param panel
	 */
	public ArrayDemoDisplay(int size, ArrayDemo demo, JPanel panel) {
		displays = new Rectangle2D[size];
		bodyColors = new Color[size];
		this.demo = demo;
		this.panel = panel;
		for (int i = 0; i < size; i++) {
			bodyColors[i] = new Color((int) (Math.random() * 0xFFFFFF));
		}
		defaultPosition();
	}

	@Override
	public boolean contains(Point p) {
		for (int i = 0; i < displays.length; i++) {
			if (displays[i].contains(p)) {
				return true;
			}
		}
		return true;
	}

	@Override
	public void moveTo(double x, double y) {
		double xCurr = x;
		double yCurr = y;
		for (int i = 0; i < displays.length; i++) {
			displays[i].setFrame(xCurr, yCurr, Constants.ARRAY_DEMO_NODE_SIZE,
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
		double xCurr = displays[0].getX() + x;
		double yCurr = displays[0].getY() + y;
		for (int i = 0; i < displays.length; i++) {
			displays[i].setFrame(xCurr, yCurr, Constants.ARRAY_DEMO_NODE_SIZE,
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
		g.drawLine((int) displays[0].getX() - 5, (int) displays[0].getY(),
				(int) displays[0].getX() - 5, (int) (displays[0].getY() + displays[0].getHeight()));
		int k = displays.length - 1;
		g.drawLine((int) (displays[k].getX() + displays[k].getWidth() + 5),
				(int) displays[k].getY(), (int) (displays[k].getX() + displays[k].getWidth() + 5),
				(int) (displays[k].getY() + displays[k].getHeight()));

		// The entries and the array and their values
		for (int i = 0; i < displays.length; i++) {
			g.setStroke(new BasicStroke(2));
			if (demo.hasValue(i)) {
				g.setColor(bodyColors[i]);
				g.fill(displays[i]);
				g.setColor(borderColor);
				g.draw(displays[i]);
				g.setColor(textColor);
				// Change the font
				Font font = new Font("Serif", Font.PLAIN, 20);
				String text = demo.get(i) + "";
				g.setFont(font);
				// Center the text within the box
				FontMetrics metrics = g.getFontMetrics(font);
				double x = displays[i].getX()
						+ (displays[i].getWidth() - metrics.stringWidth(text)) / 2;
				double y = displays[i].getY() + (displays[i].getHeight() - metrics.getHeight()) / 2
						+ metrics.getAscent();
				g.drawString(text, (int) x, (int) y);
			}

			// Give the next spot a larger colored outline
			if (i == demo.nextSpot) {
				g.setStroke(new BasicStroke(4));
				g.setColor(Color.pink);
				g.draw(displays[i]);
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
		for (int i = 0; i < displays.length; i++) {
			displays[i] = new Rectangle2D.Double(x, y, Constants.ARRAY_DEMO_NODE_SIZE,
					Constants.ARRAY_DEMO_NODE_SIZE);
			if (x + Constants.ARRAY_DEMO_NODE_SIZE + Constants.ARRAY_DEMO_NODE_SIZE < panel
					.getWidth()) {
				x += Constants.PADDING_SMALL + Constants.ARRAY_DEMO_NODE_SIZE;
			} else {
				x = xStart;
				y += Constants.PADDING_SMALL + Constants.ARRAY_DEMO_NODE_SIZE;
			}
		}
	}

}
