package StacksQueuesP3;

import javax.swing.JFrame;

public class Driver {
	public static void main(String[] args) {

		// TODO detect and create bigger display for 4k displays
		Display panel = new Display(1000, 800);
		JFrame frame = new JFrame();
		frame.setTitle("Stacks or Queues Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
