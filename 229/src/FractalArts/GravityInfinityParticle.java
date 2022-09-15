package FractalArts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class GravityInfinityParticle extends Ellipse2D.Double {

	public static int totalChildren = 0;
	/**
	 * Children spawned per recursion wave. Very expensive to increase.
	 */
	public static final int CHILDREN_PER_RECURSION = 2;
	/**
	 * Max distance spawned away from parent. Increase to spread children from their
	 * parents.
	 */
	public static final double DISTANCE_FROM_PARENT = 200;
	/**
	 * Array containing children recursively spawned by this square. Null if none.
	 */
	public GravityInfinityParticle[] children;
	/**
	 * True if this square has children, otherwise false.
	 */
	public boolean hasChildren;
	public Color color;
	public double size;

	// Variables for gravitySquares
	public static final double gravity = .2;
	/**
	 * Momentum retained after bouncing from 0-100%
	 */
	public static final double dampening = .80;
	public double vX;
	public double vY;

	/**
	 * Creates a new Particle which recursively spawns children that are 5 units
	 * smaller until size is less than 5. WARNING: VERY EXPENSIVE IF SIZE > 40
	 * 
	 * @param x    - x position of this square, children spawn randomly nearby
	 * @param y    - y position of this square, children spawn randomly nearby
	 * @param size - size of the initial square, children are 5 units smaller
	 */
	public GravityInfinityParticle(double x, double y, double size) {
		super(x, y, size, size);
		this.size = size;

		// recursively generate children if size>5
		if (size > 5) {
			hasChildren = true;
			children = new GravityInfinityParticle[CHILDREN_PER_RECURSION];
			for (int i = 0; i < children.length; i++) {
				children[i] = new GravityInfinityParticle(
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
		generateColor();
		g.setStroke(new BasicStroke(8));
		g.setColor(Color.black);
		g.draw(this);
		g.setColor(color);
		g.fill(this);

		// recursively draw children
		if (hasChildren) {
			for (int i = 0; i < children.length; i++) {
				children[i].draw(g);
			}
		}
	}

	/**
	 * Generates a color for this ball based on y position, size, and speed
	 */
	private void generateColor() {
		int r = (int) (255 * getCenterY() / 800);
		int g = (int) (225 * size / 80 + 30);
		int b = (int) ((Math.abs(vX * 2000) + Math.abs(vY * 2000)) / 255);

		if (r > 255 || r < 0) {
			r = 255;
		}
		if (g > 255 || g < 0) {
			g = 255;
		}
		if (b > 255 || b < 0) {
			b = 255;
		}
		color = new Color(r, g, b);
	}

	/**
	 * Moves this square and all its children
	 */
	public void move() {
		x += vX;
		y += vY;
		vY += gravity;

		if (getCenterY() > 800 && vY > 0 || getCenterY() < 0 && vY < 0) {
			vY = -vY * dampening;
		}

		if ((x + size / 2 < 0 && vX < 0) || (x + size / 2 > 800 && vX > 0)) {
			vX = -vX * dampening;
		}

		// recursively move children
		if (hasChildren) {
			for (int i = 0; i < children.length; i++) {
				children[i].move();
			}
		}
	}

	/**
	 * Accelerates this square on the x and y axises
	 * 
	 * @param aX
	 * @param aY
	 */
	public void accelerate(double aX, double aY) {
		vX += aX;
		vY += aY;
	}

	/**
	 * Pull this object towards a singularity, and recursively pull all children.
	 * Attractive force depends inversely on distance from the singularity.
	 * 
	 * @param s
	 */
	public void attractToSingularity(Singularity s) {
		double dX = Math.abs(s.x - this.x);
		double dY = Math.abs(s.y - this.y);
		double distance = Math.sqrt(dX * dX + dY * dY); // use-case tbd

		if (dX == 0 || dY == 0) {
			return;
		}

		// The attractive force increases the closer an object is to the singularity
		double forceX = Math.abs(dX - Singularity.MAX_RANGE) / Singularity.MAX_RANGE
				* Singularity.POWER_FACTOR;
		double forceY = Math.abs(dY - Singularity.MAX_RANGE) / Singularity.MAX_RANGE
				* Singularity.POWER_FACTOR;

		if (s.getCenterX() > this.getCenterX()) {
			if (s.getCenterY() > this.getCenterY()) {
				accelerate(forceX, forceY);
			} else {
				accelerate(forceX, -forceY);
			}
		} else {
			if (s.getCenterY() > this.getCenterY()) {
				accelerate(-forceX, forceY);
			} else {
				accelerate(-forceX, -forceY);
			}
		}

		// recursively pull children
		if (hasChildren) {
			for (int i = 0; i < children.length; i++) {
				children[i].attractToSingularity(s);
			}
		}
	}

}
