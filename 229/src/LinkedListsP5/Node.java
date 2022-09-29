package LinkedListsP5;

import java.awt.Color;

/**
 * A Linked List is built using Nodes: the Node stores the data (a color here),
 * and a pointer to the next Node. If the Node also stores a pointer to the
 * previous Node, we call it "Doubly Linked". A List with Nodes that only track
 * the next Node is "Singly Linked".
 * 
 * The operations to insert/remove/search are pretty similar between the two
 * types, but there is some extra complexity in Single Linked lists that we'll
 * avoid by sticking to a Doubly Linked List in this example.
 * 
 * @author Doctor Fish
 *
 */
public class Node {

	Color color;
	Node next;
	Node previous;

	NodeDisplay display;
	public boolean isSelected = false;

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
	 * value.
	 * 
	 * You could also use Node(color, n, null) or Node(color, null, n) to achieve
	 * the same effect, if you consider that format less awkward
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

	/**
	 * Connects NodeDisplays to the List so that the head/tail outline can be draw
	 * on the display.
	 * 
	 * @param list
	 */
	public void linkDisplayToList(LinkedList list) {
		this.display.connectList(list);
	}

}
