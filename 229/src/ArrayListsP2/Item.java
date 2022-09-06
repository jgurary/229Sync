package ArrayListsP2;

import java.awt.Color;

/**
 * An item with a name, value, and an extra value. An example of some object we
 * might want to store in a data structure!
 * 
 * @author Doctor Fish
 *
 */
public class Item {

	String name;
	int value;
	double extra;
	Color color;

	/**
	 * An item with a random color
	 * 
	 * @param name
	 * @param value
	 * @param extra
	 */
	public Item(String name, int value, double extra) {
		this.name = name;
		this.value = value;
		this.extra = extra;
		color = new Color((int) (Math.random() * 0xFFFFFF));
	}

	/**
	 * An item with a random extra value between 0 and 1, and a random color
	 * 
	 * @param name
	 * @param value
	 */
	public Item(String name, int value) {
		this.name = name;
		this.value = value;
		this.extra = Math.random();
		color = new Color((int) (Math.random() * 0xFFFFFF));
	}

}
