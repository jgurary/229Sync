package HW4HeapSnekStarter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class HeapDisplay extends JPanel {

	Heap heap;

	public static final int NODE_SIZE = Constants.screenHeight / 50;

	/**
	 * Construct a panel with specified width, height, and background color
	 * 
	 * @param width
	 * @param height
	 * @param bgColor
	 */
	public HeapDisplay(int width, int height, Color bgColor) {
		setPreferredSize(new Dimension(width, height));
		setBackground(bgColor);
	}

	protected void paintComponent(Graphics graphicHelper) {
		super.paintComponent(graphicHelper);
		Graphics2D g = (Graphics2D) graphicHelper;

		int startX = (int) (getWidth() / 2) - NODE_SIZE / 2;
		// A little padding from the top
		int startY = 50;

		drawHeap(g, startX, startY);
	}

	/**
	 * Draws the whole heap, with the given x/y position for the root's coordinates,
	 * and moving down from there.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	public void drawHeap(Graphics2D g, int x, int y) {
		for (int i = 0; i < heap.arr.length; i++) {
			int tier = getTier(i);
			int nodeY = y + tier * NODE_SIZE;

			int nodeX = x;
			// Center the root
			if (tier == 0) {
				nodeX -= NODE_SIZE / 2;
			}
			int nodes = getNodesInTier(tier);
			int first = getFirstInTier(tier);
			int mid = first + nodes / 2;
			if (i <= mid) {
				nodeX -= NODE_SIZE * (mid - i);
			} else if (i > mid) {
				nodeX += NODE_SIZE * (i - mid);
			}
			drawHeapNode(g, nodeX, nodeY, i);
		}

		// Draws the average
		Font font = new Font("Serif", Font.PLAIN, 24);
		g.setFont(font);
		g.drawString("Avg: " + heap.getAverage(), 10, 25);

	}

	/**
	 * Draws a single node at the given x/y coordinates
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param pos - index of the node to draw in the heap
	 */
	public void drawHeapNode(Graphics2D g, int x, int y, int pos) {
		g.setStroke(new BasicStroke(4));
		if (pos % 2 == 0) {
			g.setColor(Color.cyan);
		} else {
			g.setColor(Color.green);
		}
		g.drawRect(x, y, NODE_SIZE, NODE_SIZE);

		// Node value text
		if (heap.exists(pos)) {
			g.setColor(Color.WHITE);
			Font font = new Font("Serif", Font.PLAIN, 14);
			String text = "" + heap.peekIndex(pos);
			g.setFont(font);
			// Center the text within the box
			FontMetrics metrics = g.getFontMetrics(font);
			double textX = x + (NODE_SIZE - metrics.stringWidth(text)) / 2;
			double textY = y + (NODE_SIZE - metrics.getHeight()) / 2 + metrics.getAscent();
			g.drawString(text, (int) textX, (int) textY);
		}

	}

	/**
	 * Returns the tier of a Node at some index.
	 * <p>
	 * T0 = root.
	 * <p>
	 * T1 = 1, 2
	 * <p>
	 * T2 = 3, 4, 5, 6
	 * <p>
	 * T3 = 7, 8, 9, 10, 11, 12, 13, 14
	 * <p>
	 * ...etc
	 * 
	 * @param i
	 * @return
	 */
	public int getTier(int i) {
		if (i == 0) {
			return 0;
		}
		int tier = 1;
		while (true) {
			if (i > (int) Math.pow(2, tier) - 2 && i <= (int) Math.pow(2, tier + 1) - 2) {
				return tier;
			} else {
				tier++;
			}
		}
	}

	/**
	 * Returns how many Nodes are in the given tier (2^i)
	 * 
	 * @param i
	 * @return
	 */
	public int getNodesInTier(int i) {
		return (int) Math.pow(2, i);
	}

	/**
	 * Returns the first Node in a given tier
	 * 
	 * @param i
	 * @return
	 */
	public int getFirstInTier(int i) {
		return (int) Math.pow(2, i) - 1;
	}

}