package HW3TernaryTreeStarter;

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

	Tree tree;
	ColorButton[] buttons = new ColorButton[40];
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

		int red = 0;
		int green = 0;
		int blue = 0;
		double x = ColorButton.PADDING;
		double y = ColorButton.PADDING;
		for (int i = 0; i < buttons.length; i++) {
			red = 255 - 255 / buttons.length * i;
			green = 255 / buttons.length * i;
			blue = 255 / buttons.length * i;
			buttons[i] = new ColorButton(x, y, new Color(red, green, blue));
			x += ColorButton.SIZE + ColorButton.PADDING;
			if (x + ColorButton.SIZE > width) {
				x = ColorButton.PADDING;
				y += ColorButton.SIZE + ColorButton.PADDING;
			}
		}
		tree = new Tree(this);
	}

	protected void paintComponent(Graphics graphicHelper) {
		super.paintComponent(graphicHelper);
		Graphics2D g = (Graphics2D) graphicHelper;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(background);
		// Draw the "background"
		g.fillRect(0, 0, getWidth(), getHeight());
		// Draw buttons
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].draw(g);
		}
		tree.drawTree(g);

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
				Color border = buttons[i].borderColor;
				tree.addNode(new Node(c, border), tree.root);
			}
		}
		if (tree.root != null && tree.root.display.contains(e.getPoint())) {
			System.out.println("Clicked the root!");
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
			System.out.println("Max depth is: " + tree.getMaxDepth(tree.root));
		}
		if (k.getKeyCode() == KeyEvent.VK_S) {
			System.out.println("Max depth is: " + tree.getMaxDepthAlt(tree.root));
		}
		if (k.getKeyCode() == KeyEvent.VK_SPACE) {
			tree.rebalanceTree();
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