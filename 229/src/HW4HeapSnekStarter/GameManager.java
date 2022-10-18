package HW4HeapSnekStarter;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Handles basic game functions like spawning apples
 *
 */
public class GameManager {

	Display display;
	Snek snek;
	Heap heap;
	ArrayList<Apple> apples = new ArrayList<Apple>();
	/**
	 * Net ticks that have elapsed since simulation started
	 */
	long ticks;

	/**
	 * An apple spawns every time this many ticks elapse
	 */
	public static final int FRAMES_PER_APPLE_SPAWN = 30;

	public GameManager(Display display) {
		ticks = 0;
		this.display = display;
		this.snek = new Snek();
		heap = new Heap(64);
	}

	/**
	 * Advance the game state by one frame
	 */
	public void tick() {
		ticks++;

		// Move
		snek.move(display);

		// Check self-collision with head
		checkSnekSelfCollision();

		// Check if apple picked up
		Apple a = checkAppleCollision();
		if (a != null) {
			int val = (int) (Math.random() * 100);
			Node n = new Node(val, snek.list.tail.x, snek.list.tail.y);
			snek.grow(n);
			heap.insert(n);
			display.heapDisplay.repaint();
		}

		// Spawn apple as needed
		if (ticks % FRAMES_PER_APPLE_SPAWN == 0) {
			spawnApple();
		}
	}

	/**
	 * Spawns an apple in a random location on the screen
	 */
	public void spawnApple() {
		int x = (int) (Math.random() * display.getWidth());
		int y = (int) (Math.random() * display.getHeight());

		Apple apple = new Apple(x, y);
		apples.add(apple);
	}

	/**
	 * Checks collision with all Apples on screen. If the Snek has collided with
	 * one, returns it, otherwise returns null
	 * 
	 * @return
	 */
	public Apple checkAppleCollision() {
		Iterator<Apple> itr = apples.iterator();
		while (itr.hasNext()) {
			Apple a = itr.next();
			if (snek.headTouches(a)) {
				itr.remove();
				return a;
			}
		}
		return null;
	}

	/**
	 * Checks if Snek's head has collided with any of the child Nodes (i.e., the
	 * tail), and resets the Snek if this is the case
	 * 
	 * Skips the second Node (because it already touches the head a little)
	 * 
	 * TODO Skips Nodes that have been removed from the Heap
	 */
	public void checkSnekSelfCollision() {
		Node head = snek.list.head;
		if (head.next == null) {
			return;
		}
		// It's simpler to skip the second Node in the List because it touches the head
		// a little by virtue of being next to it
		Node curr = head.next.next;
		while (curr != null) {
			if (head.nodeTouches(curr)) {
				snek.resetSnek();
				heap.clear();
				display.heapDisplay.repaint();
				return;
			}
			curr = curr.next;
		}
	}

	/**
	 * Draws all apples to the screen
	 * 
	 * @param g
	 */
	public void drawApples(Graphics2D g) {
		try {
			for (Apple a : apples) {
				a.draw(g);
			}
		} catch (Exception e) {
			// silence exceptions
			// In some frames, the apple list will be modified mid-frame, and this will
			// cause an Exception. It will fix itself by the next frame, so it won't
			// visually matter.
		}
	}

}
