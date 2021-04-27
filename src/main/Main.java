package main;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import resources.DecirHolaMundo;

public class Main extends JFrame{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		setSize(200,200);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JLabel label = new JLabel();
		add(label);
		addKeyListener(new DecirHolaMundo(label));
	}
}
