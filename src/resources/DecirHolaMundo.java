package resources;
import java.util.Arrays;

import javax.swing.JLabel;

public class DecirHolaMundo extends GameAction {
	private int count = 0;
	private JLabel label;

	private VariableTimer t = new VariableTimer(2000) {

		@Override
		public void task() {
			label.setText("" + ++count);
		}

	};

	public DecirHolaMundo(JLabel label) {
		super(Arrays.asList(37, 38, 39, 40));
		this.label = label;
	}

	@Override
	void pressTask() {
		t.task();
		t.setInterval(100, 200);
	}

	@Override
	void releaseTask() {
		t.setDelayedInterval(1000);
	}

	public void cancel() {
		t.stop();
	}

}
