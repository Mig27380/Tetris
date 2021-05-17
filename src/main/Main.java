package main;

import javax.swing.JFrame;

/**
 * It just launches the application at level 1 and with 3 visible pieces
 * 
 * @author Miguel Ruiz Pryshlyak
 *
 */
public class Main extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new GameController(4, 3);
	}
}
