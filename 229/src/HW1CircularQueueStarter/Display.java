package HW1CircularQueueStarter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class Display extends JPanel implements MouseInputListener, KeyListener {

	CircularArray outer;
	CircularArray inner;
	ColorButton[] buttons = new ColorButton[13];

	public static final double OUTER_RADIUS = 150;
	public static final double INNER_RADIUS = 100;

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

		outer = new CircularArray(10, width / 2 - Tile.TILE_SIZE / 2,
				height / 2 - Tile.TILE_SIZE / 2, OUTER_RADIUS);
		inner = new CircularArray(10, width / 2 - Tile.TILE_SIZE / 2,
				height / 2 - Tile.TILE_SIZE / 2, INNER_RADIUS);
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new ColorButton(
					ColorButton.PADDING + i * (ColorButton.SIZE + ColorButton.PADDING),
					ColorButton.PADDING);
		}
	}

	protected void paintComponent(Graphics graphicHelper) {
		super.paintComponent(graphicHelper);
		Graphics2D g = (Graphics2D) graphicHelper;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		outer.draw(g);
		inner.draw(g);
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].draw(g);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// DONT USE THIS ONE
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].contains(e.getPoint())) {
				Color c = buttons[i].getColor();
				System.out.println("The last color clicked was: " + c);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_A) {
			respawnArrays(10);
		} else if (k.getKeyCode() == KeyEvent.VK_S) {
			respawnArrays(15);
		} else if (k.getKeyCode() == KeyEvent.VK_D) {
			respawnArrays(20);
		} else if (k.getKeyCode() == KeyEvent.VK_F) {
			respawnArrays(40);
		} else if (k.getKeyCode() == KeyEvent.VK_Z) {
			outer.push(Color.BLUE);
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent k) {
	}

	@Override
	public void keyTyped(KeyEvent k) {
	}

	/**
	 * Resets both circular arrays and respawns them with the given size.
	 */
	public void respawnArrays(int size) {
		double x = getWidth() / 2 - Tile.TILE_SIZE / 2;
		double y = getHeight() / 2 - Tile.TILE_SIZE / 2;
		outer = new CircularArray(size, x, y, OUTER_RADIUS);
		inner = new CircularArray(size, x, y, INNER_RADIUS);
	}

}