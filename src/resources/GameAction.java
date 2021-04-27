package resources;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GameAction implements KeyListener {

	private boolean pressed = false;
	private int lastKey = 0;
	private List<Integer> pressedKeys = new ArrayList<>();
	private final List<Integer> actionKeys;

	abstract void pressTask();

	abstract void releaseTask();

	protected GameAction(int keyCode) {
		actionKeys = new ArrayList<>();
		actionKeys.add(keyCode);
	}

	protected GameAction(List<Integer> actionKeys) {
		this.actionKeys = actionKeys;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (actionKeys.contains(e.getKeyCode())) {
			if ((e.getKeyCode() != lastKey) && !pressed) {
				pressTask();
				pressed = true;
			}
			lastKey = e.getKeyCode();
			if(!pressedKeys.contains(e.getKeyCode())) {
				pressedKeys.add(e.getKeyCode());
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.removeAll(Arrays.asList(e.getKeyCode()));
		if (pressedKeys.isEmpty() && pressed) {
			releaseTask();
			pressed = false;
			lastKey = 0;
		}
	}

	public boolean getPressed() {
		return pressed;
	}

	public int getLastKey() {
		return lastKey;
	}
}
