package ArrayListsP2;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Any object that can be drawn on our Panel
 * 
 * @author Doctor Fish
 *
 */
public interface Drawable {

	/**
	 * Returns true if the specified point is inside the element
	 * 
	 * @param p
	 * @return
	 */
	public boolean contains(Point p);

	/**
	 * Moves the top-left corner of the element to the specified location
	 * 
	 * @param x
	 * @param y
	 */
	public void moveTo(double x, double y);

	/**
	 * Shifts the top-left corner of the element by the given values, positive
	 * values shift right/down and negatives shift left/up
	 * 
	 * @param x
	 * @param y
	 */
	public void translate(double x, double y);

	/**
	 * Draws element to the screen
	 * 
	 * @param g
	 */
	public void draw(Graphics2D g);

}
