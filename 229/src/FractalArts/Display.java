package FractalArts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class Display extends JPanel implements MouseInputListener, KeyListener {

	/**
	 * If false, turns off anti-aliasing. Weaker machines can't run this demo with
	 * it on.
	 */
	public static final boolean I_HAVE_A_GOOD_GPU = true;

	ArrayList<InfinitySquare> roots = new ArrayList<InfinitySquare>();
	ArrayList<GravityInfinityParticle> gRoots = new ArrayList<GravityInfinityParticle>();
	ArrayList<Singularity> singularities = new ArrayList<Singularity>();
	ArrayList<InfinitySpiral> spirals = new ArrayList<InfinitySpiral>();

	double mouseX = 0, mouseY = 0;

	/**
	 * Construct a panel with specified width, height, and background color
	 * 
	 * @param width
	 * @param height
	 * @param bgColor
	 */
	public Display(int width, int height, Color bgColor) {
		setPreferredSize(new Dimension(width, height));
		setBackground(bgColor);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		this.addKeyListener(this);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
	}

	protected void paintComponent(Graphics graphicHelper) {
		super.paintComponent(graphicHelper);
		Graphics2D g = (Graphics2D) graphicHelper;
		if (I_HAVE_A_GOOD_GPU) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		for (InfinitySpiral s : spirals) {
			s.draw(g);
		}
		g.setColor(Color.black);
		g.drawString("Increment:" + InfinitySpiral.strobeIncrement, 50, 50);

		for (InfinitySquare i : roots) {
			i.draw(g);
		}

		for (GravityInfinityParticle i : gRoots) {
			i.draw(g);
			i.move();
		}

		for (Singularity s : singularities) {
			s.draw(g);
			for (GravityInfinityParticle i : gRoots) {
				i.attractToSingularity(s);
			}
		}

		// System.out.println("Total children in existence: " +
		// InfinitySquare.totalChildren);

		long frameTime = 1000 / 30;
		long startTime = System.currentTimeMillis();
		long currTime = System.currentTimeMillis();
		long delta = 0;
		while (delta < frameTime) {
			currTime = System.currentTimeMillis();
			delta = currTime - startTime;
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			System.out.println("Clicked, drawing random squares.");
			roots.add(new InfinitySquare(e.getX(), e.getY(), 50));
		} else {
			System.out.println("Right clicked, drawing gravity squares.");
			gRoots.add(new GravityInfinityParticle(e.getX(), e.getY(), 50));
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if (k.getKeyChar() == '1') {
			singularities.add(new Singularity(mouseX, mouseY, 100));
		} else if (k.getKeyChar() == '2') {
			singularities.clear();
		} else if (k.getKeyChar() == '3') {
			spirals.add(new InfinitySpiral(mouseX, mouseY, 5, 0, Color.black));
		} else if (k.getKeyChar() == '4') {
			InfinitySpiral toAdd = null;
			// TODO only can invert a single spiral (the last one), clears all others
			for (InfinitySpiral s : spirals) {
				toAdd = s.invertLineage(null);
				// System.out.println("new root: "+ toAdd + " " + toAdd.hasChildren + " " +
				// toAdd.child);
			}
			spirals.clear();
			spirals.add(toAdd);
		} else if (k.getKeyChar() == '5') {
			InfinitySpiral.strobeIncrement++;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}