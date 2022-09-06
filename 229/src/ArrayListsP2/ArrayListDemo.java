package ArrayListsP2;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JPanel;

/**
 * Uses an arrayList to store some data
 * 
 * @author Doctor Fish
 *
 */
public class ArrayListDemo extends DataStructure implements ArrayLike {

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

	public ArrayListDemo(int size, JPanel panel, boolean isSorted) {
		super(isSorted);
		// The argument that ArrayList takes in its constructor is the initial capacity
		// of the underlying Array. If you don't specify one, it makes an one with a
		// capacity of 10. Each time the array grows, it multiplies by 1.5x in size.

		// Sidenote: you can't see the underlying capacity of the ArrayList. The size()
		// method always returns the actual number of items. However, if you know
		// roughly how many items you're going to use, it helps to pass an initial size,
		// as it avoids the ArrayList needing to copy the underyling array as it grows
		arr = new ArrayList<Item>(size);
		display = new ArrayDemoDisplay(size, this, panel);
	}

	@Override
	public void insert(int n) {
		if (isSorted) {
			if (USE_ALT) {
				insertSorted(n);
			} else {
				insertSortedAlt(n);
			}
		} else {
			if (USE_ALT) {
				insertUnsortedAlt(n);
			} else {
				insertUnsorted(n);
			}
		}
	}

	/**
	 * Here we execute an O(N) search to find the spot to insert into, but then we
	 * call `arr.add(i, item)` to do all the right-shifting magic for us. Read more
	 * about this variant of add in {@link #insertUnsortedAlt}.
	 * 
	 * The result is O(N), just like our Array example
	 * 
	 * @param n
	 */
	public void insertSorted(int n) {
		Item item = new Item(NameGenerator.getRandomName(), n);
		int i = 0;
		int size = arr.size();
		while (i < size && arr.get(i).value < n) {
			i++;
		}
		arr.add(i, item);
		display.updateSize(arr.size());
	}

	/**
	 * We can push most of the functionality to sort() if we are too lazy to write
	 * it ourselves! The built-in sort is O(NlogN), but tends to do better when the
	 * list is already nearly sorted, so this is not *much* slower than our version
	 * that finds the spot, and in some cases might be faster!
	 * 
	 * @param n
	 */
	public void insertSortedAlt(int n) {
		Item item = new Item(NameGenerator.getRandomName(), n);
		arr.add(item);
		arr.sort(compareValues());
		display.updateSize(arr.size());
	}

	/**
	 * Insertion (called 'add' in the ArrayList library) is essentially the same as
	 * our "Better" unsorted insert that doesn't allow gaps and tracks the next item
	 * 
	 * It's O(1) MOST of the time, as it simply adds the item to the end of the
	 * list.
	 * 
	 * When the array is full, we need create the new larger array, which has some
	 * small overhead, and copy all the elements into the new array, which requires
	 * N operations. Since this happens 1/N times we insert, and the other (N-1)
	 * insertions are constant time, the average is ((N-1) * 1 + N)/N, which is
	 * (N-1+N)/N ~= 2N/N ~= 2
	 * 
	 * We call this O(1) Amortized time, because the average is O(1), but the
	 * worst-case peak is O(N)
	 * 
	 * @param n
	 */
	public void insertUnsorted(int n) {
		Item item = new Item(NameGenerator.getRandomName(), n);
		arr.add(item);
		display.updateSize(arr.size());
	}

	/**
	 * You can add at some index, like 0 here, but this will be O(N) time as all the
	 * other elements have to shift right. A common misconception is that adding to
	 * a specific index like this is also O(1), but it's not!
	 * 
	 * They could have made this O(1): an item could simply be moved to the end to
	 * make room for the new one. However, ArrayList always keeps the other items in
	 * the same order when inserting, so this isn't an option.
	 * 
	 * Note that arr.add(arr.size(), item) is the same as arr.add().
	 * 
	 * As a final caveat: arr.set() is constant time, even when specifying an index,
	 * since the elements don't move (and thus nothing must shift)
	 * 
	 * @param n
	 */
	public void insertUnsortedAlt(int n) {
		Item item = new Item(NameGenerator.getRandomName(), n);
		arr.add(0, item);
		display.updateSize(arr.size());
	}

	@Override
	public boolean remove(int n) {
		if (isSorted) {
			return removeSorted(n);
		} else {
			if (USE_ALT) {
				return removeValueGood(n);
			} else {
				return removeValueBad(n);
			}
		}
	}

	/**
	 * If you have a reference to the Item, you can remove using that reference.
	 * This is O(N) due to the search required to find the item as well as the
	 * shifting left required afterwards
	 * 
	 * Be wary: just because an Item has the same value, doesn't mean it's the same
	 * Item!
	 * 
	 * @param n
	 * @return
	 */
	public boolean removeItem(Item n) {
		return arr.remove(n);
	}

	/**
	 * This is ok: it's O(N) to find the item, and again O(N) to remove it due to
	 * shifting left.
	 * 
	 * The problem here is that the array shifts right after the remove, but this is
	 * ok because we return
	 * 
	 * @param n
	 * @return
	 */
	public boolean removeValueGood(int n) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).value == n) {
				arr.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * This is actually broken: when a value is removed, everything shifts over.
	 * This causes us to skip a value
	 * 
	 * @param n
	 * @return
	 */
	public void removeAllValuesBad(int n) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).value == n) {
				arr.remove(i);
			}
		}
	}

	/**
	 * This works, but it's awkward. Stepping back an index is weird.
	 * 
	 * @param n
	 */
	public void removeAllValuesBetter(int n) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).value == n) {
				arr.remove(i);
				i--;
			}
		}
	}

	/**
	 * Using the remove function on the Iterator is the intended way, and the most
	 * natural. The built in remove handles backstepping for you and ensures no
	 * items are skipped.
	 * 
	 * @param n
	 */
	public void removeAllValuesGood(int n) {
		Iterator<Item> itr = arr.iterator();
		while (itr.hasNext()) {
			Item i = itr.next();
			if (i.value == n) {
				itr.remove();
			}
		}
	}

	/**
	 * This is bad because it's O(N) twice: the call to remove(i) has to execute a
	 * search to find the index!
	 * 
	 * @param n
	 * @return
	 */
	public boolean removeValueBad(int n) {
		for (Item i : arr) {
			if (i.value == n) {
				return arr.remove(i);
			}
		}
		return false;
	}

	/**
	 * Must easier than the array counterpart: ArrayList handles the shifting for us
	 * when we remove!
	 * 
	 * @param n
	 * @return
	 */
	public boolean removeSorted(int n) {
		int i = searchSorted(n);
		if (i != DEFAULT) {
			arr.remove(i);
			return true;
		}
		return false;
	}

	@Override
	public int search(int n) {
		if (isSorted) {
			return searchSorted(n);
		} else {
			return searchUnsorted(n);
		}
	}

	public int searchUnsorted(int n) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).value == n) {
				return i;
			}
		}
		return DEFAULT;
	}

	/**
	 * We can implement the same binary search here!
	 * 
	 * @param n
	 * @return
	 */
	public int searchSorted(int n) {
		int start = 0;
		int end = arr.size();
		int i = (start + end) / 2;
		while (start <= end) {
			int value = arr.get(i).value;
			if (value < n) {
				start = i + 1;
			} else if (value == n) {
				return i;
			} else {
				end = i - 1;
			}
			i = (start + end) / 2;
		}
		return DEFAULT;
	}

	/**
	 * Sorting an ArrayList requires a Comparator, which is a fancy of telling Java
	 * how you want your items sorted.
	 */
	@Override
	public void sort() {
		arr.sort(compareValues());
	}

	/**
	 * Compares two Items by value
	 * 
	 * @param arr
	 * @return
	 */
	private Comparator<Item> compareValues() {
		// This "simple" example of a Comparator probably looks odd if you haven't seen
		// the syntax before. We've declared an anonymous class here (a new Comparator)
		// and specified the type of thing we are comparing with <Item>
		return new Comparator<Item>() {
			// We implement the compare method to finish. Reading the documentation gives
			// you a simple guide on how to use compare. If o1 < o2, return a negative
			// number, if equal return 0, and if o1 > o2, return a positive. Simply flip
			// that to sort ascending vs descending
			@Override
			public int compare(Item o1, Item o2) {
				return o1.value - o2.value;
			}
		};
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
