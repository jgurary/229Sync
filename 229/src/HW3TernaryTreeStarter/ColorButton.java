package HW3TernaryTreeStarter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class ColorButton extends Rectangle2D.Double {

	public static final double SIZE = 50;
	public static final double PADDING = 10;
	private Color color;
	/**
	 * Always random, used to outline the buttons to make similar shades more unique
	 */
	public Color borderColor;

	/**
	 * Creates a new button with a random color
	 * 
	 * @param x
	 * @param y
	 */
	public ColorButton(double x, double y) {
		super(x, y, SIZE, SIZE);
		color = new Color((int) (Math.random() * 0xFFFFFF));
		borderColor = new Color((int) (Math.random() * 0xFFFFFF));
	}

	/**
	 * Creates a new button with a particular color
	 * 
	 * @param x
	 * @param y
	 * @param c
	 */
	public ColorButton(double x, double y, Color c) {
		super(x, y, SIZE, SIZE);
		color = c;
		borderColor = new Color((int) (Math.random() * 0xFFFFFF));
	}

	public Color getColor() {
		return color;
	}

	public boolean isClicked(Point p) {
		return this.contains(p);
	}

	public void draw(Graphics2D g) {
		g.setStroke(new BasicStroke(6));
		g.setColor(borderColor);
		g.draw(this);
		g.setColor(color);
		g.fill(this);
		g.setColor(Color.WHITE);
		g.drawString("R: " + color.getRed(), (float) x, (float) (y + SIZE / 3));
		g.drawString("G: " + color.getGreen(), (float) x, (float) (y + 2 * SIZE / 3));
		g.drawString("B: " + color.getBlue(), (float) x, (float) (y + 3 * SIZE / 3));
	}

}
