package HW3TernaryTreeStarter;

import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class DisplayNode {

	Ellipse2D ellipse;

	public DisplayNode() {
		ellipse = new Ellipse2D.Double(0, 0, 0, 0);
	}

	public boolean contains(Point p) {
		return ellipse.contains(p);
	}

	public void setFrame(int x, int y, int size) {
		ellipse.setFrame(x, y, size, size);
	}

}
