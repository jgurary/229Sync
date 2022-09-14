package StacksQueuesP3;

/**
 * An interface that allows lists to share the same DemoDisplay
 * 
 * @author Doctor Fish
 *
 */
public interface ArrayLike {

	/**
	 * Returns true if the given index has a value
	 * 
	 * @param n
	 * @return
	 */
	public boolean hasValue(int n);

	/**
	 * Returns the value at the given index. May return `DEFAULT` or go out of
	 * bounds if bad values are supplied
	 * 
	 * @param n
	 * @return
	 */
	public Item get(int n);

	/**
	 * Returns a String for printing the given index. May return "" or go out of
	 * bounds if bad values are supplied
	 * 
	 * @param n
	 * @return
	 */
	public String getString(int n);

	/**
	 * Returns the next spot tracker, if this data structure is currently using one.
	 * Returns 0 otherwise.
	 * 
	 * @return
	 */
	public int getNextSpot();

}
