package StacksQueuesP3;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Uses an arrayList to store some data
 * 
 * @author Doctor Fish
 *
 */
public class StackDemo extends DataStructure implements ArrayLike {

	private ArrayList<Item> arr;
	ArrayDemoDisplay display;
	/**
	 * Placeholder for a failed search
	 */
	public static final int DEFAULT = -1;
	/**
	 * Set to true to use the "alt" version of certain demo functions
	 */
	boolean USE_ALT = false;

	public StackDemo(int size, JPanel panel) {
		// A stack is never sorted: that doesn't make sense conceptually. Though you
		// might add items to a stack in order, which makes it ordered, you generally
		// don't search a stack, so this is irrelevant
		super(false);
		arr = new ArrayList<Item>(size);
		display = new ArrayDemoDisplay(size, this, panel);
	}

	@Override
	public void insert(int n) {
		push(n);
	}

	/**
	 * Insert into a stack (or queue) is called a "push", and simply appends it to
	 * the end. This is always O(1), though like an array/arrayList, if the array
	 * has to expand to fit the item, it's O(N) for that one insertion, giving us
	 * amortized O(1) overall if we want the stack to be able to grow (like this one
	 * can, since it uses ArrayList under the hood)
	 * 
	 * @param n
	 */
	public void push(int n) {
		Item item = new Item(NameGenerator.getRandomName(), n);
		arr.add(item);
		display.updateSize(arr.size());
	}

	/**
	 * We can't remove a specific item from a stack: we can only remove the top
	 * item. No matter which button you press in the demo, it will just remove the
	 * top item.
	 */
	@Override
	public boolean remove(int n) {
		Item arr = pop();
		System.out.println("removed: " + arr.value);
		return false;
	}

	/**
	 * Pop removes the topmost item and returns it. Always O(1)! This is the only
	 * way to remove items from the Stack (though many implementations also support
	 * something like clear(), which is just popping until empty)
	 * 
	 * @return
	 */
	public Item pop() {
		if (arr.size() == 0) {
			System.out.println("Stack empty!");
			return null;
		}
		Item i = arr.get(arr.size() - 1);
		arr.remove(arr.size() - 1);
		return i;
	}

	/**
	 * Sometimes we just want to observe the last item without removing it. This is
	 * also O(1)
	 * 
	 * @return
	 */
	public Item peek() {
		if (arr.size() == 0) {
			System.out.println("Stack empty!");
			return null;
		}
		Item i = arr.get(arr.size() - 1);
		return i;
	}

	/**
	 * There is no concept of "searching" a stack, we only ever care about the last
	 * item.
	 */
	@Override
	public int search(int n) {
		return DEFAULT;
	}

	/**
	 * There is no concept of "sorting" a stack, we only ever care about the last
	 * item.
	 * 
	 * There are examples of sorting stacks/queues you can find out there for mostly
	 * academic reasons: this isn't something you would normally do
	 */
	@Override
	public void sort() {

	}

	/**
	 * Refreshes the visual representation of this Data Structure
	 * 
	 * @param g
	 */
	public void refreshDisplay(Graphics2D g) {
		display.draw(g);
	}

	/**
	 * Returns true if the given index has a value
	 * 
	 * @param i
	 * @return
	 */
	public boolean hasValue(int n) {
		if (n > arr.size() - 1) {
			return false;
		}
		if (arr.get(n).value != DEFAULT) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the value at the given index. May go out of bounds if bad values are
	 * supplied
	 * 
	 * @param i
	 * @return
	 */
	public Item get(int n) {
		return arr.get(n);
	}

	/**
	 * The next spot to add into is always the next spot in the underlying array:
	 * ArrayLists always insert on the "end"! Another way to phrase this is that the
	 * size is the next blank spot in the underlying array, or if that array is
	 * full, it's the length of that array (which will grow if we add anything).
	 * 
	 * Note that the size() operation is O(1): ArrayList tracks the end of the list
	 * with a variable just like we did with nextSpot in the Array Demo
	 */
	@Override
	public int getNextSpot() {
		return arr.size();
	}

	@Override
	public String getString(int n) {
		return "" + arr.get(n).value;
	}

}
