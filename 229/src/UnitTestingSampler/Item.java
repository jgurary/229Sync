package UnitTestingSampler;

import java.awt.Color;

public class Item {

	int value;
	Color color;

	/**
	 * Creates a new item with a random color
	 * 
	 * @param value
	 */
	public Item(int value) {
		this.value = value;
		this.color = new Color((int) (Math.random() * 0xFFFFFF));
	}

}
