package LinkedListsP5;

import java.awt.Color;

import javax.swing.JFrame;

public class Driver {
	public static void main(String[] args) {
		Display panel = new Display(800, 800, Color.black);
		JFrame frame = new JFrame("Linked List Sampler");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
