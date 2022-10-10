package HW2LinkedListSolution;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class LinkedList {

	/**
	 * The first Node in the list
	 */
	Node head;
	/**
	 * The last Node in the list
	 */
	Node tail;
	/**
	 * Selected Node for cut, insert before/after, and display functionality.
	 */
	Node selected;

	/**
	 * Creates an empty list
	 */
	public LinkedList() {
		head = null;
		tail = null;
	}

	/**
	 * Creates a list with one node
	 * 
	 * @param n
	 */
	public LinkedList(Node n) {
		this.head = n;
		this.tail = n;
	}

	/**
	 * Inserts into the list if it's blank, otherwise does nothing.
	 * 
	 * Insertion is always a little different when the list is completely blank,
	 * other insertion functions utilize this one check if the list is blank and act
	 * accordingly
	 * 
	 * @param n
	 * @return - true if an insertion occurred, otherwise false
	 */
	public boolean insertIfEmpty(Node n) {
		if (head != null) {
			return false;
		} else {
			head = n;
			tail = n;
			return true;
		}
	}

	/**
	 * Inserts a Node at the tail. Cannot fail.
	 * 
	 * @param n
	 */
	public void insertAtTail(Node n) {
		if (insertIfEmpty(n)) {
			return;
		}
		tail.next = n;
		n.previous = tail;
		tail = n;
	}

	/**
	 * Inserts a node at the head. Cannot fail.
	 * 
	 * @param n
	 */
	public void insertAtHead(Node n) {
		if (insertIfEmpty(n)) {
			return;
		}
		head.previous = n;
		n.next = head;
		head = n;
	}

	/**
	 * Inserts a Node just before the given Node
	 * 
	 * @param toAdd     - the new Node to be added
	 * @param addBefore - the existing Node to add before
	 */
	public void insertBefore(Node toAdd, Node addBefore) {
		if (addBefore == head) {
			insertAtHead(toAdd);
		} else {
			toAdd.previous = addBefore.previous;
			toAdd.next = addBefore;
			addBefore.previous.next = toAdd;
			addBefore.previous = toAdd;
		}
	}

	/**
	 * Inserts a Node just after the given Node
	 * 
	 * @param toAdd    - the new Node to be added
	 * @param addAfter - the existing Node to add after
	 */
	public void insertAfter(Node toAdd, Node addAfter) {
		if (addAfter == tail) {
			insertAtTail(toAdd);
		} else {
			toAdd.previous = addAfter;
			toAdd.next = addAfter.next;
			addAfter.next.previous = toAdd;
			addAfter.next = toAdd;
		}
	}

	/**
	 * Removes the given Node from the list
	 * 
	 * @param n
	 */
	public void removeNode(Node n) {
		// edge case where the Node is the last/only in the list
		if (n == head && n == tail) {
			head = null;
			tail = null;
		}
		// Node is the head (the Node just after is the new head)
		else if (n == head) {
			head = n.next;
			n.next.previous = null;
		}
		// Node is the tail (the Node just before is the new tail)
		else if (n == tail) {
			tail = n.previous;
			n.previous.next = null;
		}
		// All other Nodes:
		else {
			n.next.previous = n.previous;
			n.previous.next = n.next;
		}
	}

	/**
	 * Removes all Nodes with the given Color from the list.
	 * 
	 * @param c
	 */
	public void removeAllWithColor(Color c) {
		Node curr = head;
		while (curr != null) {
			if (curr.color == c) {
				removeNode(curr);
			}
			curr = curr.next;
		}
	}

	/**
	 * Selects the given Node, giving it a special outline and preparing it for
	 * other operations
	 * 
	 * @param n
	 */
	public void selectNode(Node n) {
		selected = n;
	}

	/**
	 * Clears the list's selected Node: no Node will be selected until
	 * {@link #selectNode} triggers again.
	 */
	public void deselect() {
		selected = null;
	}

	/**
	 * Returns whichever of the two given Nodes comes first in the list (is closest
	 * to the head). Returns null if neither Node is found
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public Node whichFirst(Node a, Node b) {
		Node curr = head;
		while (curr != null) {
			if (curr == a) {
				return a;
			} else if (curr == b) {
				return b;
			}
			curr = curr.next;
		}
		return null;
	}

	/**
	 * Removes all Nodes between the two given Nodes
	 * 
	 * @param a
	 * @param b
	 */
	public void cut(Node a, Node b) {
		if (a == b) {
			return;
		}
		Node first = whichFirst(a, b);
		// This "ternary operator" is a syntax shorthand for:
		/*
		 * if (first == a) { second = b; } else { second = a; }
		 */
		Node second = first == a ? b : a;

		first.next = second;
		second.previous = first;
	}

	/**
	 * Inverts the list: the head is now the tail and vice-versa.
	 */
	public void invertList() {
		Node curr = head;

		if (head == null) {
			return;
		}

		while (curr != null) {
			Node oldNext = curr.next;
			curr.next = curr.previous;
			curr.previous = oldNext;
			/*
			 * This is a tricky step: many examples will do curr = curr.previous instead,
			 * which works because next and previous just changed places. I find saving the
			 * next in a temp variable to be much easier to understand!
			 */
			curr = oldNext;

		}
		Node temp = head;
		head = tail;
		tail = temp;
	}

	/**
	 * Draws the list to the screen in a fancy spiral
	 * 
	 * @param g
	 */
	public void draw(Graphics2D g, int centerX, int centerY) {
		if (head == null) {
			return;
		}

		final double THETA_STEP = Math.PI / 5;
		final double RADIUS_STEP = 4;
		final int NODE_SIZE = (int) NodeDisplay.NODE_SIZE;

		int x = centerX;
		int y = centerY;
		double radius = NODE_SIZE;
		double theta = 0;
		Node curr = head;
		while (curr != null) {
			curr.display.moveTo(x, y);
			// Draws a special outline for the selected Node
			if (selected != null && curr == selected) {
				g.setStroke(new BasicStroke(6));
				g.setColor(Color.WHITE);
				g.drawOval(x, y, NODE_SIZE, NODE_SIZE);
			}
			// Prepare x/y for the next Node to be drawn
			x = (int) (centerX + Math.cos(theta) * radius);
			y = (int) (centerY + Math.sin(theta) * radius);
			radius += RADIUS_STEP;
			theta += THETA_STEP;
			curr = curr.next;
		}

		curr = head;
		while (curr != null) {
			curr.display.draw(g);
			curr = curr.next;
		}

	}

}
