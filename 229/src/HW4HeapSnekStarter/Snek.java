package HW4HeapSnekStarter;

import java.awt.Graphics2D;

import HW4HeapSnekStarter.Constants.Direction;

public class Snek {

	/**
	 * Direction the Snek is moving in
	 */
	Direction direction;
	/**
	 * Underyling Linked List that makes up the snek
	 */
	LinkedList list;
	/**
	 * How much the snek moves per frame. If this is larger than the Node size
	 * you'll see gaps. If smaller, you'll see some overlap between Nodes
	 */
	int speed = Constants.screenHeight / 50;

	public Snek() {
		direction = Direction.RIGHT;
		Node head = new Node(-1, 100, 100);
		list = new LinkedList(head);
	}

	/**
	 * Advances the Snek in the direction it's currently moving
	 * 
	 * @param display - the display the Snek is in. Used to wrap around when hitting
	 *                edges.
	 */
	public void move(Display display) {
		int xMove, yMove;
		switch (direction) {
		case DOWN:
			xMove = 0;
			yMove = speed;
			break;
		case LEFT:
			xMove = -speed;
			yMove = 0;
			break;
		case RIGHT:
			xMove = speed;
			yMove = 0;
			break;
		case UP:
			xMove = 0;
			yMove = -speed;
			break;
		default:
			xMove = 0;
			yMove = 0;
			break;
		}

		// All the Nodes except the head scoot up to take the previous Node's position
		Node curr = list.tail;
		while (curr != list.head) {
			curr.x = curr.previous.x;
			curr.y = curr.previous.y;
			curr = curr.previous;
		}

		// The Head moves
		list.head.x += xMove;
		list.head.y += yMove;
		if (list.head.x > display.getWidth()) {
			list.head.x = 0;
		} else if (list.head.x < 0) {
			list.head.x = display.getWidth();
		}
		if (list.head.y > display.getHeight()) {
			list.head.y = 0;
		} else if (list.head.y < 0) {
			list.head.y = display.getHeight();
		}
	}

	/**
	 * Snek grows one link longer
	 * 
	 * @param n - new Node to link
	 */
	public void grow(Node n) {
		list.insertAtTail(n);
	}

	/**
	 * Returns true if the Snek's head touches the given Node
	 * 
	 * @param n
	 * @return
	 */
	public boolean headTouches(Node n) {
		return list.head.nodeTouches(n);
	}

	/**
	 * Returns true if the Snek's head touches the given Apple
	 * 
	 * @param a
	 * @return
	 */
	public boolean headTouches(Apple a) {
		return list.head.nodeTouches(a);
	}

	/**
	 * Wen Snek ded, reset to just head.
	 */
	public void resetSnek() {
		list.head.next = null;
		list.tail = list.head;
	}

	public void draw(Graphics2D g) {
		list.draw(g);
	}

}
