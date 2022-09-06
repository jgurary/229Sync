package ArrayListsP2;

public abstract class DataStructure {

	public boolean isSorted;

	public DataStructure(boolean isSorted) {
		this.isSorted = isSorted;
	}

	/**
	 * Adds the given value to the data structure
	 * 
	 * @param i
	 */
	public abstract void insert(int n);

	/**
	 * Tries to remove the given value, returns true if something was removed
	 * 
	 * @param i
	 * @return
	 */
	public abstract boolean remove(int n);

	/**
	 * Attempts to find the given value, returns the location if it was found
	 * 
	 * @param i
	 * @return
	 */
	public abstract int search(int n);

	/**
	 * Sorts the data structure into order
	 */
	public abstract void sort();

}
