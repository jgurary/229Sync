package HW3TernaryTreeStarter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Stack;

public class Tree {

	Node root;
	/**
	 * Link to the display, used to properly center Nodes in the display when
	 * drawing.
	 */
	private Display display;

	/**
	 * Size of the Nodes when drawn to the screen.
	 */
	public static final int SIZE = 30;
	/**
	 * Minimal spacing between Nodes: if a tier requires more spacing than the
	 * screen can provide, this value will be used instead, though some Nodes may
	 * end up off-screen or partially hidden by other children
	 */
	public static final int MIN_SPACING = 20;

	/**
	 * Creates an empty tree
	 * 
	 * @param d - the Display panel where this tree will draw.
	 */
	public Tree(Display d) {
		root = null;
		display = d;
	}

	/**
	 * Creates a tree with the given root
	 * 
	 * @param root
	 * @param d    - the Display panel where this tree will draw.
	 */
	public Tree(Node root, Display d) {
		this.root = root;
		display = d;
	}

	/**
	 * If the tree is empty, makes this Node the root and returns true, else returns
	 * false.
	 * 
	 * @param n
	 * @return
	 */
	public boolean insertIfEmpty(Node n) {
		if (root == null) {
			root = n;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Attempts to insert into the tree
	 * 
	 * @param n    - Node to be inserted
	 * @param curr - Where to being searching for an insertion spot (pass root to
	 *             search the entire tree)
	 */
	public void addNode(Node n, Node curr) {
		if (insertIfEmpty(n)) {
			return;
		}
		if (n.getColor().getBlue() < curr.getColor().getBlue()) {
			if (curr.left == null) {
				insertLeft(curr, n);
			} else {
				addNode(n, curr.left);
			}
		} else if (n.getColor().getBlue() == curr.getColor().getBlue()) {
			if (curr.middle == null) {
				insertMiddle(curr, n);
			} else {
				addNode(n, curr.middle);
			}
		} else if (n.getColor().getBlue() > curr.getColor().getBlue()) {
			if (curr.right == null) {
				insertRight(curr, n);
			} else {
				addNode(n, curr.right);
			}
		}
	}

	// Valid non-recursive alternative insertion algorithm
//	public void addNodeAlt(Node n, Node start) {
//		if (insertIfEmpty(n)) {
//			return;
//		}
//		
//		Node currNode = start;
//		while (currNode != null) {
//			if (n.getColor().getBlue() < currNode.getColor().getBlue()) {
//				if (currNode.left == null) {
//					insertLeft(currNode, n);
//				} else {
//					currNode = currNode.left;
//				}
//			} else if (n.getColor().getBlue() == currNode.getColor().getBlue()) {
//				if (currNode.left == null) {
//					insertMiddle(currNode, n);
//				} else {
//					currNode = currNode.middle;
//				}
//			} else {
//				if (currNode.right == null) {
//					insertRight(currNode, n);
//				} else {
//					currNode = currNode.right;
//				}
//			}
//		}
//	}

	/**
	 * Returns the depth of the Tree, starting from the given Node.
	 * 
	 * The depth is the longest path from the given Node.
	 * 
	 * @param curr - pass the root to get the depth of the entire tree
	 * @return
	 */
	public int getMaxDepth(Node curr) {
		if (curr == null) {
			return 0;
		} else {
			int lDepth = getMaxDepth(curr.left);
			int mDepth = getMaxDepth(curr.middle);
			int rDepth = getMaxDepth(curr.right);

			return maxOf(lDepth, mDepth, rDepth) + 1;
		}
	}

	/**
	 * The non-recursive approach to get the max depth is hard to say the least!
	 * 
	 * There are probably some slightly simpler algorithms out there: this one uses
	 * a stack to keep track of visited nodes, popping them as it goes. It visits
	 * left, then right, and takes advantage of the fact that if the right node was
	 * the last one popped, the left was also visited.
	 * 
	 * There may be bugs...
	 * 
	 * @param curr
	 * @return
	 */
	public int getMaxDepthAlt(Node curr) {
		int depth = 1;
		int maxDepth = 1;
		Stack<Node> stack = new Stack<Node>();
		stack.push(curr);
		Node lastPopped = null;
		while (true) {
			if (stack.isEmpty()) {
				break;
			} else {
//				System.out.println("Visited: " + curr.getColor() + " at depth " + depth);
//				if (lastPopped != null) {
//					System.out.println("last popped: " + lastPopped.getColor());
//				}
			}

			// If left node not null, not the last one visited, nor was the right one the
			// last one visited
			if (curr.left != null && lastPopped != curr.left
					&& (lastPopped != curr.right || curr.right == null)) {
				depth++;
				if (depth > maxDepth) {
					maxDepth = depth;
				}
				curr = curr.left;
				stack.push(curr);
				// right node not null nor the last one visited
			} else if (curr.right != null && lastPopped != curr.right) {
				depth++;
				if (depth > maxDepth) {
					maxDepth = depth;
				}
				curr = curr.right;
				stack.push(curr);
			} else {
				// move back up the tree and pop as we go
				lastPopped = stack.pop();
				curr = curr.parent;
				depth--;
			}
		}

		return maxDepth;
	}

	/**
	 * Returns the max value of the three given inputs.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public int maxOf(int a, int b, int c) {
		return Math.max(Math.max(a, b), c);
	}

	/**
	 * Draws the Tree to the display using recursive magic
	 * 
	 * @param g
	 */
	public void drawTree(Graphics2D g) {
		if (root == null) {
			return;
		}
		Node curr = root;
		// The middle
		int rootX = display.getWidth() / 2 - SIZE / 2;
		int rootY = 150; // A little padding away from the top
		drawNode(g, curr, 0, rootX, rootY, -1, -1);
	}

	/**
	 * Draws a Node to the screen, then draws its children.
	 * 
	 * Determines spacing based on the expected maximum Nodes in each tier, or a
	 * default minimum if there is not enough room to properly space Nodes. Some
	 * Nodes may overlap of go off-screen
	 * 
	 * @param g
	 * @param curr
	 * @param int     tier - depth of this Node in the tree, used for properly
	 *                spacing Nodes in a given level
	 * @param x
	 * @param y
	 * @param parentX - for drawing lines back to the parent, pass a negative to
	 *                draw no line
	 * @param parentY - for drawing lines back to the parent, pass a negative to
	 *                draw no line
	 */
	public void drawNode(Graphics2D g, Node curr, int tier, int x, int y, int parentX,
			int parentY) {

		// Line to parent (if applicable)
		g.setStroke(new BasicStroke(2));
		g.setColor(Color.WHITE);
		if (parentX > 0 && parentY > 0) {
			g.drawLine(x + SIZE / 2, y + SIZE / 2, parentX + SIZE / 2, parentY + SIZE / 2);
		}
		curr.display.setFrame(x, y, SIZE);

		// Body
		g.setColor(curr.getColor());
		g.fill(curr.display.ellipse);

		// Outline color (matches the button's outline)
		g.setStroke(new BasicStroke(4));
		g.setColor(curr.borderColor);
		g.draw(curr.display.ellipse);

		// Node ID text
		g.setColor(Color.WHITE);
		Font font = new Font("Serif", Font.PLAIN, 14);
		String text = "" + curr.id;
		g.setFont(font);
		// Center the text within the box
		FontMetrics metrics = g.getFontMetrics(font);
		double textX = x + (SIZE - metrics.stringWidth(text)) / 2;
		double textY = y + (SIZE - metrics.getHeight()) / 2 + metrics.getAscent();
		g.drawString(text, (int) textX, (int) textY);

		// There are 3^tier Nodes in any given tier
		// width / Nodes gives you space available per Node in the next tier
		// Subtracting SIZE gives you just the expected padding.

		// Sizing the tree as if it has just 2^n Nodes per tier but starting one tier
		// later has a better look for smaller trees, but overlaps quickly
		int spacing = display.getWidth() / (int) Math.pow(2, tier + 2) - SIZE;
		// This is technically more correct, but runs out of space very quickly.
		// int spacing = display.getWidth() / (int) Math.pow(3, tier + 1) - SIZE;

		// Negative spacing doesn't make sense. If the padding was negative, the Nodes
		// won't fit on the panel, some end up off-screen, some overlap each other
		spacing = Math.max(spacing, MIN_SPACING);

		// Drawing middle first allows it hide behind the sides when space is tight.
		if (curr.middle != null) {
			drawNode(g, curr.middle, tier + 1, x, y + SIZE, x, y);
		}
		if (curr.left != null) {
			drawNode(g, curr.left, tier + 1, x - spacing, y + SIZE, x, y);
		}
		if (curr.right != null) {
			drawNode(g, curr.right, tier + 1, x + spacing, y + SIZE, x, y);
		}

	}

	/**
	 * Inserts a child Node to the left of the given parent.
	 * 
	 * @param parent
	 * @param child
	 */
	public void insertLeft(Node parent, Node child) {
		// TODO consider handling the case where the left branch is already occupied!
		parent.left = child;
		child.parent = parent;
	}

	/**
	 * Inserts a child Node to the middle of the given parent.
	 * 
	 * @param parent
	 * @param child
	 */
	public void insertMiddle(Node parent, Node child) {
		// TODO consider handling the case where the left branch is already occupied!
		parent.middle = child;
		child.parent = parent;
	}

	/**
	 * Inserts a child Node to the right of the given parent.
	 * 
	 * @param parent
	 * @param child
	 */
	public void insertRight(Node parent, Node child) {
		// TODO consider handling the case where the left branch is already occupied!
		parent.right = child;
		child.parent = parent;
	}

}
