package LinkedListsP5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class ColorButton extends Rectangle2D.Double {

	public static final double SIZE = 50;
	public static final double PADDING = 10;
	private Color color;

	public ColorButton(double x, double y) {
		super(x, y, SIZE, SIZE);
		color = new Color((int) (Math.random() * 0xFFFFFF));
	}

	public Color getColor() {
		return color;
	}

	public boolean isClicked(Point p) {
		return this.contains(p);
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.draw(this);
		g.setColor(color);
		g.fill(this);
	}

}
