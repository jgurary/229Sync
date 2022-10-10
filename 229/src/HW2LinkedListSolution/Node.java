package HW2LinkedListSolution;

import java.awt.Color;

public class Node {

	Color color;
	NodeDisplay display;
	Node previous;
	Node next;

	/**
	 * Creates a Node with no connections
	 * 
	 * @param color
	 */
	public Node(Color color) {
		this.color = color;
		this.previous = null;
		this.next = null;
		display = new NodeDisplay(this);
	}

	/**
	 * Creates a new Node and sets either the next or previous pointer to the given
	 * value
	 * 
	 * @param color
	 * @param n
	 * @param isNext - if true, sets next, else sets previous to n
	 */
	public Node(Color color, Node n, boolean isNext) {
		this.color = color;
		if (isNext) {
			this.next = n;
		} else {
			this.previous = n;
		}
		display = new NodeDisplay(this);
	}

	/**
	 * Creates a new Node with the given previous and next points
	 * 
	 * @param color
	 * @param prev
	 * @param next
	 */
	public Node(Color color, Node prev, Node next) {
		this.color = color;
		this.previous = prev;
		this.next = next;
		display = new NodeDisplay(this);
	}

}
