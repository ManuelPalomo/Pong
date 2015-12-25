package graphics;

import javax.swing.JFrame;

public class Screen {
	private JFrame frame;

	public Screen() {
		frame = setJFrame();
	}

	private JFrame setJFrame() {
		JFrame frame = new JFrame("Pong");
		//Add components
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(500, 500);
		frame.setVisible(true);

		return frame;
	}

}
