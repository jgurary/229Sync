package LinkedListsP5;

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
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].contains(e.getPoint())) {
				// BUTTON1 = Left click, BUTTON3 = Right click (BUTTON2 is scroll wheel click)
				if (e.getButton() == MouseEvent.BUTTON1) {
					Color c = buttons[i].getColor();
					Node n = new Node(c);
					list.insertAtTail(n);
					n.linkDisplayToList(list);
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					Color c = buttons[i].getColor();
					int index = list.getIndex(c);
					if (index != -1) {
						System.out.println("Found color " + c + " at index " + index);
					} else {
						System.out.println("Couldn't find " + c);
					}
				}
			}
		}
		Node curr = list.head;
		while (curr != null) {
			if (curr.display.contains(e.getPoint())) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					curr.isSelected = !curr.isSelected;
				}
				System.out.println("Clicked node: " + curr.color);
			}
			curr = curr.next;
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
			Node n = new Node(Color.CYAN);
			System.out.println("Inserting a cyan Node at index 5");
			list.insertAtIndex(n, 5);
		} else if (k.getKeyCode() == KeyEvent.VK_A) {
			Node n = list.removeAtHead();
			if (n != null) {
				System.out.println("Removed the head: " + n.color);
			}
		} else if (k.getKeyCode() == KeyEvent.VK_S) {
			Node n = list.removeAtTail();
			if (n != null) {
				System.out.println("Removed the tail: " + n.color);
			}
		} else if (k.getKeyCode() == KeyEvent.VK_D) {
			list = list.getVeryBlueList(list.head);
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