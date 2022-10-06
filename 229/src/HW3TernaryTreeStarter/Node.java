package HW3TernaryTreeStarter;

import java.awt.Color;

public class Node {

	Node parent;
	Node left;
	Node middle;
	Node right;
	private Color color;
	/**
	 * This is used to differentiate very similar shades of Color: it matches the
	 * same border Color on the buttons, and should help you to tell Nodes of very
	 * similar shades apart
	 */
	public Color borderColor;
	public int id;
	public static int NEXT_ID = 0;

	public DisplayNode display;

	public Node(Color color, Color borderColor) {
		this.color = color;
		this.borderColor = borderColor;
		id = NEXT_ID;
		NEXT_ID++;
		display = new DisplayNode();
	}

	public Color getColor() {
		return color;
	}

}
