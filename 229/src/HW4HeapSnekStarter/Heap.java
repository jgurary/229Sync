package HW4HeapSnekStarter;

public class Heap {

	/**
	 * Underlying array representing the heap
	 */
	Node[] arr;

	/**
	 * Items currently in the heap, also the index of the next available spot.
	 */
	int size;

	/**
	 * Creates a new Heap with the given max capacity.
	 * 
	 * @param size
	 */
	public Heap(int size) {
		arr = new Node[size];
		this.size = 0;
	}

	/**
	 * TODO Returns, but does not remove, the smallest value.
	 * 
	 * @return
	 */
	public int peekMin() {
		return 0;
	}

	/**
	 * TODO Returns and removes the smallest value.
	 * 
	 * Returns null if empty
	 * 
	 * @return
	 */
	public Node getMin() {
		return null;
	}

	/**
	 * An alias for {@link #getMin()}
	 * 
	 * @return
	 */
	public Node pop() {
		return getMin();
	}

	/**
	 * TODO
	 * 
	 * @param toInsert
	 */
	public void insert(Node toInsert) {
		size++;
	}

	/**
	 * TODO Resets the heap, removing all items (resets values back to blank)
	 */
	public void clear() {

	}

	/**
	 * TODO Returns the sum of all items in the heap
	 * 
	 * @return
	 */
	public int getAverage() {
		return 0;
	}

	/**
	 * Returns the index of the parent of the given slot
	 * 
	 * @param n - the index of some value in the Heap's tree structure
	 * @return
	 */
	private int getParent(int n) {
		// Even indexes need to subtract 1, Odd division already rounds down.
		if (n % 2 == 0) {
			return n / 2 - 1;
		} else {
			return n / 2;
		}
	}

	/**
	 * Returns the index of the spot that's to the left of the given slot
	 * 
	 * @param n - the index of some "Node" in the Heap's tree structure
	 * @return
	 */
	private int getLeft(int n) {
		return 2 * n + 1;
	}

	/**
	 * Returns the index of the spot that's to the right of the given slot
	 * 
	 * @param n - the index of some "Node" in the Heap's tree structure
	 * @return
	 */
	private int getRight(int n) {
		return 2 * n + 2;
	}

	/**
	 * Returns true if the "Node" at the given location exists (e.g., is filled),
	 * otherwise false.
	 * 
	 * @param n
	 * @return
	 */
	public boolean exists(int n) {
		if (n < 0 || n > arr.length) {
			return false;
		}
		if (n < size) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Look at the value of a "Node" at some index without modifying it. Useful when
	 * drawing the heap, but otherwise generally not used.
	 * 
	 * Returns -1 if index is empty/null or out of bounds
	 * 
	 * @param n
	 * @return
	 */
	public int peekIndex(int n) {
		if (n > arr.length || arr[n] == null) {
			return -1;
		}
		return arr[n].value;
	}

	/**
	 * Returns true if the value at the given spot is a leaf of the Heap's
	 * underlying tree structure.
	 * 
	 * A leaf is a Node with no children.
	 * 
	 * @param n - the index of some "Node" in the Heap's tree structure
	 * @return
	 */
	private boolean isLeaf(int n) {
		if (n >= size / 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Swaps two "Nodes" in the Heap's underlying tree structure by swapping their
	 * positions in the tree.
	 * 
	 * @param a
	 * @param b
	 */
	private void swap(int a, int b) {
		Node temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

}
