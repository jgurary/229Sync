package HW4HeapSnekStarter;

import java.awt.Color;

import javax.swing.JFrame;

public class Driver {
	public static void main(String[] args) {
		HeapDisplay heapDisplay = new HeapDisplay(Constants.screenHeight * 11 / 16,
				Constants.screenHeight / 2, Color.black);
		Display mainDisplay = new Display(Constants.screenHeight / 2, Constants.screenHeight / 2,
				Color.black, heapDisplay);
		JFrame mainFrame = new JFrame("Heap Snek");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.add(mainDisplay);
		mainFrame.pack();
		mainFrame.setVisible(true);

		JFrame heapFrame = new JFrame("Heap Indicator");
		heapFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		heapFrame.add(heapDisplay);
		// Spawn to the right of the main display
		heapFrame.setLocation(Constants.screenHeight / 2, 0);
		heapFrame.pack();
		heapFrame.setVisible(true);
	}
}
