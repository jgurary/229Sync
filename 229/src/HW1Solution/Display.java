package HW1Solution;

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

	ArrayList<CircularArray> arrs = new ArrayList<CircularArray>();
	ColorButton[] buttons = new ColorButton[13];
	Color background = Color.black;

	public static final double INNER_RADIUS = 100;
	public static final double RADIUS_STEP = 50;

	int size = 10;

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

		arrs.add(new CircularArray(size, width / 2 - Tile.TILE_SIZE / 2,
				height / 2 - Tile.TILE_SIZE / 2, INNER_RADIUS));
		arrs.add(new CircularArray(size, width / 2 - Tile.TILE_SIZE / 2,
				height / 2 - Tile.TILE_SIZE / 2, INNER_RADIUS + RADIUS_STEP));
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
		g.setColor(background);
		// Draw the "background"
		g.fillRect(0, 0, getWidth(), getHeight());
		// Draw circular arrays
		for (CircularArray c : arrs) {
			c.draw(g);
		}
		// Draw buttons
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
				for (int k = 0; k < arrs.size(); k++) {
					if (arrs.get(k).isFull()) {
						System.out.println("queue " + k + " is full");
						continue;
					}
					System.out.println("Inserting into queue " + k);
					arrs.get(k).push(c);
					repaint();
					return;
				}
				double x = getWidth() / 2 - Tile.TILE_SIZE / 2;
				double y = getHeight() / 2 - Tile.TILE_SIZE / 2;
				CircularArray arr = new CircularArray(10, x, y,
						INNER_RADIUS + arrs.size() * RADIUS_STEP);
				arrs.add(arr);
				arr.push(c);
				System.out.println("Spawned new queue and inserted into it");
			}
		}
		repaint();
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
			size = 10;
			respawnArrays(size);
		} else if (k.getKeyCode() == KeyEvent.VK_S) {
			size = 15;
			respawnArrays(size);
		} else if (k.getKeyCode() == KeyEvent.VK_D) {
			size = 20;
			respawnArrays(size);
		} else if (k.getKeyCode() == KeyEvent.VK_F) {
			size = 40;
			respawnArrays(size);
		} else if (k.getKeyCode() == KeyEvent.VK_SPACE) {
			for (int i = 0; i < arrs.size(); i++) {
				if (!arrs.get(i).isEmpty()) {
					System.out.println("Popping from queue " + i);
					background = arrs.get(i).pop();
					repaint();
					return;
				}
			}
			System.out.println("All queues empty.");
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
	 * Resets all circular arrays and respawns them with the given size.
	 */
	public void respawnArrays(int size) {
		double x = getWidth() / 2 - Tile.TILE_SIZE / 2;
		double y = getHeight() / 2 - Tile.TILE_SIZE / 2;
		for (int k = 0; k < arrs.size(); k++) {
			arrs.set(k, new CircularArray(size, x, y, INNER_RADIUS + k * RADIUS_STEP));
		}
	}

}