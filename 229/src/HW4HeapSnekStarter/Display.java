package HW4HeapSnekStarter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import HW4HeapSnekStarter.Constants.Direction;

public class Display extends JPanel implements MouseInputListener, KeyListener, Runnable {

	GameManager game;
	HeapDisplay heapDisplay;
	boolean paused = false;

	/**
	 * Construct a panel with specified width, height, and background color
	 * 
	 * @param width
	 * @param height
	 * @param bgColor
	 * @param heapDisply - a panel that displays information about the heap
	 */
	public Display(int width, int height, Color bgColor, HeapDisplay heapDisplay) {
		setPreferredSize(new Dimension(width, height));
		setBackground(bgColor);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		this.addKeyListener(this);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);

		Thread thr = new Thread(this);
		thr.start();
		game = new GameManager(this);
		this.heapDisplay = heapDisplay;
		heapDisplay.heap = game.heap;
	}

	protected void paintComponent(Graphics graphicHelper) {
		super.paintComponent(graphicHelper);
		Graphics2D g = (Graphics2D) graphicHelper;
		game.snek.draw(g);
		game.drawApples(g);
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
	public void mousePressed(MouseEvent arg0) {
		System.out.println("Click");
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent k) {
		switch (k.getKeyCode()) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			game.snek.direction = Direction.UP;
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			game.snek.direction = Direction.DOWN;
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			game.snek.direction = Direction.RIGHT;
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			game.snek.direction = Direction.LEFT;
			break;
		case KeyEvent.VK_SPACE:
			paused = !paused;
			break;
		case KeyEvent.VK_Z:
			Node min = game.heap.getMin();
			if (min != null) {
				System.out.println("min was: " + min.value);
				game.snek.list.setRemoved(min);
				heapDisplay.repaint();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Advances the simulation one frame.
	 */
	public void tick() {
		game.tick();
		repaint();
	}

	/**
	 * Runs the animation/simulation. Triggers a tick roughly "FPS" times per second
	 */
	@Override
	public void run() {
		// How many milliseconds go by between each frame
		long requiredTime = 1000 / Constants.FPS;
		// time last frame was drawn
		long startTime = System.currentTimeMillis();

		while (true) {
			// If paused, does nothing
			if (paused) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}

			long deltaTime = System.currentTimeMillis() - startTime;
			if (deltaTime > requiredTime) {
				tick();
				startTime = System.currentTimeMillis();
			} else {
				// Pauses execution until the next frame should be drawn
				long toSleep = requiredTime - deltaTime;
				try {
					Thread.sleep(toSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}