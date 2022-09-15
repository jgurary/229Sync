package FractalArts;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Singularity extends Ellipse2D.Double{
	
	public double size;
	/**
	 * Attractive power of the singularity at max strength
	 */
	public static final double POWER_FACTOR = 1.5;
	public static final double MAX_RANGE = 800;
	
	public Singularity(double x, double y, double size) {
		super(x, y, size, size);
		this.size = size;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fill(this);
		
		double radius = size*.6;
		//Draw event horizon radiation pulses
		for(int i=0; i<25; i++) {
			int nx = (int) (x + size/2 + Math.random()*radius-radius);
			int ny = (int) (y + size/2 + Math.random()*radius-radius);
			g.setColor(Color.darkGray);
			g.fillOval(nx, ny, (int) radius, (int) radius);
		}
	}
}
