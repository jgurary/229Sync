package HW2LinkedListStarter;

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
