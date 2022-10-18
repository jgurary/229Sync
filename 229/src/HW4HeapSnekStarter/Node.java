package HW4HeapSnekStarter;

public class Node {

	/**
	 * Node's stored value
	 */
	int value;
	/**
	 * Node location in 2D space
	 */
	int x, y;
	NodeDisplay display;
	Node previous;
	Node next;

	/**
	 * True if the Node was removed from the heap, otherwise false
	 */
	public boolean isRemoved = false;

	/**
	 * Creates a Node with no connections, whose location is off-screen
	 * 
	 * @param val
	 */
	public Node(int val) {
		this.value = val;
		this.previous = null;
		this.next = null;
		x = -150;
		y = -150;
		display = new NodeDisplay(x, y, this);
	}

	/**
	 * Creates a Node with no connections, at the given location in 2D space
	 * 
	 * @param val
	 * @param x
	 * @param y
	 */
	public Node(int val, int x, int y) {
		this.value = val;
		this.previous = null;
		this.next = null;
		this.x = x;
		this.y = y;
		display = new NodeDisplay(x, y, this);
	}

	/**
	 * Returns true if this Node touches the given one.
	 * 
	 * @param n
	 * @return
	 */
	public boolean nodeTouches(Node n) {
		return this.display.rect.intersects(n.display.rect);
	}

	/**
	 * Returns true if this Node touches the given Apple
	 * 
	 * @param a
	 * @return
	 */
	public boolean nodeTouches(Apple a) {
		return a.display.intersects(this.display.rect);
	}

}
