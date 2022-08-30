package ArraysP1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import ArraysP1.Event.OpCode;

public class Display extends JPanel implements MouseInputListener, KeyListener {

	public DigitButton[] digitButtons = new DigitButton[9];
	public ArrayDemo demo;
	public OpCode opCode = OpCode.Insert;
	public boolean isSorted = true;

	/**
	 * Construct a panel with specified width, height, and background color
	 * 
	 * @param width
	 * @param height
	 * @param bgColor
	 */
	public Display(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		this.addKeyListener(this);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);

		for (int i = 0; i < digitButtons.length; i++) {
			digitButtons[i] = new DigitButton(i);
		}

		demo = new ArrayDemo(20, this, isSorted);
	}

//
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		// Edges look nicer with AA on (comment this out if your PC is terrible)
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawGradientBackground(g);
		placeDigitButtons(g);
		demo.display.moveTo(this.getWidth() / 2, Constants.PADDING_SMALL);
		demo.refreshDisplay(g);

	}

	/**
	 * Places the digit buttons in a grid and draws them
	 * 
	 * @param g
	 */
	public void placeDigitButtons(Graphics2D g) {
		double padding = 20;
		double netSpacing = padding + Constants.DIGIT_BUTTON_SIZE;
		int buttonsPerRow = 3;
		for (int i = 0; i < digitButtons.length; i++) {
			digitButtons[i].moveTo(netSpacing * (i % buttonsPerRow) + padding,
					netSpacing * (i / buttonsPerRow) + padding);
			digitButtons[i].draw(g);
		}
		g.drawString("op mode: " + opCode.toString(), 50, this.getHeight() - 50);
	}

	/**
	 * Draws a neat color gradient over the entire display area. It's not great, but
	 * it's better than light grey!
	 * 
	 * @param g
	 */
	public void drawGradientBackground(Graphics2D g) {
		GradientPaint gp = new GradientPaint(0, 0, Color.blue, this.getWidth(), this.getHeight(),
				Color.cyan);
		g.setPaint(gp);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// DON'T USE THIS ONE
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < digitButtons.length; i++) {
			Event event = digitButtons[i].click(e.getPoint());
			if (event != null) {
				switch (opCode) {
				case Insert:
					demo.insert(digitButtons[i].value);
					break;
				case Remove:
					demo.remove(digitButtons[i].value);
					break;
				case Search:
					demo.search(digitButtons[i].value);
					break;
				case Sort:
					demo.sort();
					break;
				default:
					break;

				}
			}
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if (k.getKeyChar() == 'a') {
			opCode = OpCode.Insert;
		} else if (k.getKeyChar() == 's') {
			opCode = OpCode.Remove;
		} else if (k.getKeyChar() == 'd') {
			opCode = OpCode.Search;
		} else if (k.getKeyChar() == 'f') {
			opCode = OpCode.Sort;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent k) {
	}

	@Override
	public void keyTyped(KeyEvent k) {
	}

}