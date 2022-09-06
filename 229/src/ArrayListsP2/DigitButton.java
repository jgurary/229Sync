package ArrayListsP2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;

public class DigitButton implements Drawable, Clickable {

	int value;
	public Drawable display;
	private Rectangle2D rect;
	private Color bodyColor = Color.white;
	private Color borderColor = Color.black;
	private Color textColor = Color.black;

	/**
	 * Creates a new DigitButton with its display off the screen
	 * 
	 * @param value - some value this button represents
	 */
	public DigitButton(int value) {
		this.value = value;
		rect = new Rectangle2D.Double(-100, -100, Constants.DIGIT_BUTTON_SIZE,
				Constants.DIGIT_BUTTON_SIZE);
	}

	/**
	 * Creates a new digit button with its display at given location
	 * 
	 * @param value - some value this button represents
	 * @param x
	 * @param y
	 */
	public DigitButton(int value, double x, double y) {
		this.value = value;
		rect = new Rectangle2D.Double(x, y, Constants.DIGIT_BUTTON_SIZE,
				Constants.DIGIT_BUTTON_SIZE);
	}

	@Override
	public Event click(Point p) {
		if (contains(p)) {
			Event event = new Event(this);
			event.value = value;
			return event;
		}
		return null;
	}

	@Override
	public boolean contains(Point p) {
		return rect.contains(p);
	}

	@Override
	public void moveTo(double x, double y) {
		rect.setFrame(x, y, rect.getWidth(), rect.getHeight());

	}

	@Override
	public void translate(double x, double y) {
		rect.setFrame(rect.getX() + x, rect.getY() + y, rect.getWidth(), rect.getHeight());
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(bodyColor);
		g.fill(rect);
		g.setColor(borderColor);
		g.draw(rect);
		g.setColor(textColor);
		// Change the font
		Font font = new Font("Serif", Font.PLAIN, 20);
		String text = value + "";
		g.setFont(font);
		// Center the text within the box
		FontMetrics metrics = g.getFontMetrics(font);
		double x = rect.getX() + (rect.getWidth() - metrics.stringWidth(text)) / 2;
		double y = rect.getY() + (rect.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
		g.drawString(text, (int) x, (int) y);

	}

}
