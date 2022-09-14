package StacksQueuesP3;

import java.awt.Graphics2D;
import java.util.Arrays;

import javax.swing.JPanel;

/**
 * After being revised to use Items instead of integers, this example is a
 * little silly. It moves the values around, but not the actual Items (which
 * also have a name, a color, and an extra value). We'll do better in the
 * ArrayList example!
 * 
 * Uses an array to store some data
 * 
 * @author Doctor Fish
 *
 */
public class ArrayDemo extends DataStructure implements ArrayLike {

	private Item arr[];
	ArrayDemoDisplay display;
	/**
	 * Placeholder for an empty slot, or a failed search
	 */
	public static final int DEFAULT = -1;
	/**
	 * Tracks the next available spot to insert into for unsorted insertion/removal.
	 */
	int nextSpot = 0;
	/**
	 * Set to true to use the "better" version of certain demo functions
	 */
	boolean USE_BETTER = true;
	JPanel panel;

	public ArrayDemo(int size, JPanel panel, boolean isSorted) {
		super(isSorted);
		arr = new Item[size];
		for (int i = 0; i < size; i++) {
			arr[i] = new Item(NameGenerator.getRandomName(), DEFAULT);
		}

		display = new ArrayDemoDisplay(size, this, panel);
		this.panel = panel;
	}

	@Override
	public void insert(int n) {
		if (isSorted) {
			insertSorted(n);
		} else {
			if (USE_BETTER) {
				insertUnsortedBetter(n);
			} else {
				insertUnsorted(n);
			}
		}
	}

	/**
	 * Expands to array to 1.5x its initial size, keeping the same values in the
	 * same order!
	 */
	public void grow() {
		int size = arr.length;
		int newSize = (int) (arr.length * 1.5);
		Item[] copy = arr.clone();
		arr = new Item[newSize];
		// Copy over the original values
		for (int i = 0; i < size; i++) {
			arr[i] = copy[i];
		}
		// Fill the rest of the array with blanks
		for (int i = size; i < newSize; i++) {
			arr[i] = new Item(NameGenerator.getRandomName(), DEFAULT);
		}
		display = new ArrayDemoDisplay(newSize, this, panel);
		System.out.println("grew to new size: " + arr.length);
	}

	/**
	 * When most people talk about array insert performance, they're talking about
	 * this one.
	 * 
	 * This is true even in applications that use unsorted arrays, because for
	 * performance reasons, leaving gaps is usually a bad idea! As we'll see later
	 * on, even when order doesn't actually matter, most implementations will keep
	 * the original order under the hood, which requires the shifting we'll see here
	 * even for unsorted data.
	 * 
	 * If the array is sorted, we can optimize this further by making our search for
	 * the spot where this value belongs more efficient (see the binary search
	 * below)
	 * 
	 * Excluding the search, which is O(N) here but might be O(logN) if we improved
	 * it, the insertion itself requires O(N) time because all values have to shift
	 * over to accommodate the newly inserted one!
	 * 
	 * @param n
	 */
	public void insertSorted(int n) {
		if (arr[arr.length - 1].value != DEFAULT) {
			grow();
			return;
		}

		int k = 0;
		// Our inefficient O(N) search for the first value that's bigger than this one
		while (k < arr.length) {
			// Got to the end and found nothing larger, insert at the end
			if (arr[k].value == DEFAULT) {
				arr[k].value = n;
				nextSpot++;
				return;
			}

			if (arr[k].value < n) {
				k++;
			} else {
				break;
			}
		}
		// Shift all existing values right to make a spot
		for (int i = arr.length - 2; i >= k; i--) {
			arr[i + 1].value = arr[i].value;
		}
		// Put the new value in the spot we made
		arr[k].value = n;

		// Although we don't need this for insertion and removal, it aids in search!
		// We can also make insertion a tiny bit faster by only right-shifting to this
		// point.
		nextSpot++;

	}

	/**
	 * Inserts an item in the first spot available
	 * 
	 * This could be O(1) insertion, because the actual insertion operation takes
	 * constant time, but technically it's O(N), because we have to search for the
	 * spot to insert into! Here we made it O(N) explicitly by writing the "search"
	 * into the function.
	 * 
	 * @param i
	 */
	public void insertUnsorted(int n) {
		for (int k = 0; k < arr.length; k++) {
			if (arr[k].value == DEFAULT) {
				arr[k].value = n;
				return;
			}
		}
		grow();
	}

	/**
	 * Inserts an item in the first spot available
	 * 
	 * This is O(1) insertion, as it requires no loop!
	 * 
	 * @param i
	 */
	public void insertUnsortedBetter(int n) {
		if (nextSpot + 1 >= arr.length) {
			grow();
			return;
		} else {
			arr[nextSpot].value = n;
			nextSpot++;
		}
	}

	@Override
	public boolean remove(int n) {
		if (isSorted) {
			return removeSorted(n);
		} else {
			if (USE_BETTER) {
				return removeUnsortedBetter(n);
			} else {
				return removeUnsorted(n);
			}
		}
	}

	/**
	 * When most people talk about array removal performance, they're talking about
	 * this one.
	 * 
	 * The search here is O(logN), which beats the unsorted variant, but the removal
	 * itself requires us to shift all the remaining items to the left so there are
	 * no gaps. This process requires N/2 moves on average, which is O(N) in
	 * traditional notation
	 * 
	 * @param n
	 * @return
	 */
	public boolean removeSorted(int n) {
		int i = searchSorted(n);
		if (i != DEFAULT) {
			// left-shift all values beyond that point
			for (int k = i; k < arr.length - 1; k++) {
				arr[k].value = arr[k + 1].value;
			}
			arr[arr.length - 1].value = DEFAULT;

			// Here we could make removal a tiny bit faster by only left-shifting to the
			// nextSpot
			// We also know that the nextSpot should now be blank, like so:
			if (nextSpot < arr.length - 1) {
				arr[nextSpot].value = DEFAULT;
			}
			nextSpot--;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The search here is O(N), but the removal itself is O(1)
	 * 
	 * @param n
	 * @return
	 */
	public boolean removeUnsorted(int n) {
		int i = searchUnsorted(n);
		if (i != DEFAULT) {
			arr[i].value = DEFAULT;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The search here is O(N), and the removal requires just one extra operation
	 * beyond the standard version: updating the nextSpot index. This is what
	 * enables is to insert in O(1) time, but it adds essentially zero overhead to
	 * removal, which is still O(1) as well
	 * 
	 * @param n
	 * @return
	 */
	public boolean removeUnsortedBetter(int n) {
		int i = searchUnsorted(n);
		if (i != DEFAULT) {
			// over-write the empty slot to leave no blanks
			// since order doesn't matter, we'll just take the last item!
			arr[i].value = arr[nextSpot - 1].value;
			arr[nextSpot - 1].value = DEFAULT;
			nextSpot--;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int search(int n) {
		if (isSorted) {
			return searchSorted(n);
		} else {
			return searchUnsorted(n);
		}
	}

	/**
	 * If the array is unsorted, we have no choice but to exhaustively search
	 * through all N items, so it's O(N)
	 * 
	 * @param n
	 * @return
	 */
	public int searchUnsorted(int n) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].value == n) {
				return i;
			}
		}
		return DEFAULT;
	}

	/**
	 * A sorted array offers us some better options: here's a simple binary search,
	 * offering O(logN) performance!
	 * 
	 * Binary search is much easier to write with recursion, keep that in mind for
	 * later.
	 * 
	 * @param n
	 * @return
	 */
	public int searchSorted(int n) {
		// The start of the interval we're looking in
		int start = 0;
		// The end of the interval we're looking in
		// This is why we tracked the "end" of the array with nextSpot!
		int end = nextSpot;
		// the midpoint, which is always the point we're checking
		int i = (start + end) / 2;

		int count = 0;

		while (start <= end) {
			// Try an array size that's very large, and observe the change here...
			System.out.println("Binary Search Iterations: " + count);
			System.out.println("start " + start + " end " + end + " midpoint: " + i);
			count++;

			if (arr[i].value < n) {
				start = i + 1;
			} else if (arr[i].value == n) {
				return i;
			} else {
				end = i - 1;
			}
			i = (start + end) / 2;
		}
		// Once start passes end, and we haven't found the value, it isn't there.
		System.out.println();
		return DEFAULT;
	}

	/**
	 * We'll handwave around sort for now and come back to it later! But if you need
	 * to sort, use the built in sort, and remember that it's roughly O(NlogN)
	 */
	@Override
	public void sort() {
		Arrays.sort(arr);
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
		if (arr[n].value != DEFAULT) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the value at the given index. Goes out of bounds if bad values are
	 * supplied
	 * 
	 * @param i
	 * @return
	 */
	public Item get(int n) {
		return arr[n];
	}

	@Override
	public int getNextSpot() {
		return nextSpot;
	}

	@Override
	public String getString(int n) {
		return "" + arr[n].value;
	}

}
