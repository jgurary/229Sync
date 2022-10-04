package HW2LinkedListStarter;

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
				Color c = buttons[i].getColor();
				list.insertAtTail(new Node(c));
			}
		}
		Node curr = list.head;
		while (curr != null) {
			if (curr.display.contains(e.getPoint())) {
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
		System.out.println(k.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent k) {
	}

	@Override
	public void keyTyped(KeyEvent k) {
	}

}