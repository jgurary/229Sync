package HW2LinkedListSolution;

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

	/**
	 * If false, turns off anti-aliasing. Weaker machines can't run this demo with
	 * it on.
	 */
	public static final boolean I_HAVE_A_GOOD_GPU = true;

	LinkedList list = new LinkedList();
	ColorButton[] buttons = new ColorButton[13];

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

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new ColorButton(
					ColorButton.PADDING + i * (ColorButton.SIZE + ColorButton.PADDING),
					ColorButton.PADDING);
		}
	}

	protected void paintComponent(Graphics graphicHelper) {
		super.paintComponent(graphicHelper);
		Graphics2D g = (Graphics2D) graphicHelper;
		if (I_HAVE_A_GOOD_GPU) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		for (int i = 0; i < buttons.length; i++) {
			buttons[i].draw(g);
		}
		list.draw(g, this.getWidth() / 2, this.getHeight() / 2);

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
		// Used to detect when none of the buttons or Nodes were clicked.
		boolean clickedSomething = false;

		// Handle button clicks
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].contains(e.getPoint())) {
				clickedSomething = true;
				Color c = buttons[i].getColor();
				if (e.getButton() == MouseEvent.BUTTON1 && list.selected != null) {
					list.insertAfter(new Node(c), list.selected);
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					list.insertAtTail(new Node(c));
				} else if (e.getButton() == MouseEvent.BUTTON3 && list.selected != null) {
					list.insertBefore(new Node(c), list.selected);
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					list.removeAllWithColor(c);
				}
			}
		}

		// Handle Node clicks
		Node curr = list.head;
		while (curr != null) {
			if (curr.display.contains(e.getPoint())) {
				clickedSomething = true;
				if (e.getButton() == MouseEvent.BUTTON1 && list.selected != null) {
					// Does nothing
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					list.removeNode(curr);
				} else if (e.getButton() == MouseEvent.BUTTON3 && list.selected != null) {
					list.cut(curr, list.selected);
					list.deselect();
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					list.selectNode(curr);
				}
			}
			curr = curr.next;
		}

		// Clicks in the empty space deselect the currently selected Node
		if (!clickedSomething) {
			list.deselect();
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
		if (k.getKeyCode() == KeyEvent.VK_SPACE) {
			list.invertList();
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent k) {
	}

	@Override
	public void keyTyped(KeyEvent k) {
	}

}