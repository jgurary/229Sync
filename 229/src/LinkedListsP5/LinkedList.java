package LinkedListsP5;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * A Linked List can be envisioned as a series of Nodes containing data that are
 * connected to each other but otherwise aren't organized in any way (they may
 * be sorted, but not organized in terms of code).
 * 
 * Arrays get a whole chunk of memory allocated to them at once, even for the
 * empty spots, while a Linked List allocates new memory only when a Node is
 * made. This causes them to be fragmented in the system's memory, and is
 * generally the reason Linked Lists have poor performance in spite of their
 * theoretical advantages.
 * 
 * The advantage is that unlike an Array, the size of a Linked List isn't fixed:
 * it's infinite (until your system runs out of memory), and unlike an
 * ArrayList, it achieves this without ever having to copy its Nodes over again.
 * 
 * No matter how large the Linked List is, you can always insert/remove an item
 * anywhere in the List in O(1) time. This is their main advantage. You may need
 * to search for the spot to insert at, and this is O(N).
 * 
 * Even sorted Linked Lists take a long time to search compared to Arrays,
 * though more advanced lists (like Skip Lists) can make this slightly better
 * 
 * You can only traverse (loop over all items) in a Linked List by starting at
 * the first Node (the "head") and visiting the Node it's linked to, then the
 * one that one is linked to, and so forth. If Doubly Linked, you might also
 * start at the tail and go backwards. This process is O(N), which makes Linked
 * Lists a poor choice for any application where items other than the tail/head
 * are accessed frequently (which is most applications). Linked Lists are not
 * particularly popular because of this and other performance constraints, but
 * you can make a decent queue with one pretty easily, and they are still
 * popular for interview questions.
 *
 */
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
	 * Inserts into the list if it's blank, otherwise does nothing. Always O(1)
	 * 
	 * Insertion is always a little different when the list is completely blank,
	 * other insertion functions utilize this one check if the list is blank and act
	 * accordingly
	 * 
	 * Most examples you'll see of Linked Lists bake this check directly into the
	 * insertion function, but I personally find it more conceptually easy to
	 * understand if it's placed in a function like this!
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
	 * Inserts a Node at the tail. Cannot fail. Always O(1)
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
	 * Removes the tail, returns it. May return null if List was empty. Always O(1)
	 * 
	 * @return
	 */
	public Node removeAtTail() {
		Node n = tail;
		// Special case when removing the last Node
		if (head == tail) {
			head = null;
			tail = null;
		} else {
			tail = n.previous;
			n.previous.next = null;
		}
		return n;
	}

	/**
	 * Inserts a node at the head. Cannot fail. Always O(1)
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
	 * Removes the head, returns it. May return null if List was empty. Always O(1)
	 * 
	 * @return
	 */
	public Node removeAtHead() {
		Node n = head;
		// Special case when removing the last Node
		if (head == tail) {
			head = null;
			tail = null;
		} else {
			head = n.next;
			n.next.previous = null;
		}
		return n;
	}

	/**
	 * Inserts right after the given index. Inserts to the tail if the index is out
	 * of bounds
	 * 
	 * Average run time is around N/2: O(N), worst case is N: O(N) (due to the
	 * search, the insertion itself is O(1))
	 * 
	 * Note that insertion by index is pretty rare
	 * 
	 * @param n
	 * @param index - position to insert after
	 */
	public void insertAtIndex(Node n, int index) {
		if (insertIfEmpty(n)) {
			return;
		}
		Node curr = head;
		int i = 0;
		while (curr != null) {
			if (i == index) {
				// Handles the special case where insertion index is the last Node
				if (curr == tail) {
					insertAtTail(n);
				} else {
					n.previous = curr;
					n.next = curr.next;
					curr.next.previous = n;
					curr.next = n;
				}
				return;
			}
			i++;
			curr = curr.next;
		}
		// Got to the end and didn't reach the index, insert at the end
		insertAtTail(n);
	}

	/**
	 * Returns the first index of some Color in the Linked List, or -1 if it doesn't
	 * exist. Average run time is around N/2: O(N), worst case is N: O(N)
	 * 
	 * This is the biggest disadvantage of Linked Lists: accessing Items is slow. In
	 * fact, it's very slow. This makes Linked Lists a poor choice for *most*
	 * applications, and why they are relatively seldom used today!
	 * 
	 * @param c
	 * @return
	 */
	public int getIndex(Color c) {
		Node curr = head;
		int i = 0;
		while (curr != null) {
			if (curr.color.equals(c)) {
				return i;
			}
			i++;
			curr = curr.next;
		}
		return -1; // Not found, return some placeholder
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
