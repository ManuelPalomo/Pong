package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import actors.Ball;
import actors.Paddle;
import utils.Cons;

public class Screen {
	private JFrame frame;
	private PongCanvas canvas;
	private JPanel container;

	public Screen() {
		frame = setJFrame();
	}

	private JFrame setJFrame() {
		frame = new JFrame("Pong");
		// Add components
		frame.add(setComponents());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(Cons.WIDTH, Cons.HEIGHT);
		frame.setVisible(true);

		return frame;
	}

	private JPanel setComponents() {
		container = new JPanel();
		container.setLayout(new BorderLayout());
		
		canvas = new PongCanvas();
		
		container.add(canvas, BorderLayout.CENTER);

		return container;

	}
	
	/**
	 * Calls the PongCanvas repaint method
	 */
	public void repaint(){
		canvas.repaint();
		
	}
	

}
