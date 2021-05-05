package main;

import resources.GameAction;
import resources.VariableTimer;

public class GameController extends GameLogic {
	
	private static final int[] SPEED_CURVE = {800, 716, 633, 640, 550, 466, 383, 300, 216, 133, 100, 83, 83, 38,
			66, 66, 66, 50, 50, 50, 33, 33, 33, 33, 33, 33, 33, 33, 33, 33, 16};
	
	private boolean isMovingRight = false;
	private boolean isMovingLeft = false;
	private boolean isSoftDropping = false;
	
	private VariableTimer fallTimer = new VariableTimer(SPEED_CURVE[getLevel()]) {
		@Override
		public void task() {
			if(!moveDown(isSoftDropping)) {
				if(cyclesOnGround > (getLevel()<6 ? 2 : getLevel()<12 ? 3 : getLevel()<16 ? 4 : 5)) {
					leavePiece();
				}
				cyclesOnGround++;
			} else {
				cyclesOnGround = 0;
			}
		}
	};

	public GameController(int level, int visibleUpcomingPieces) {
		super(level, visibleUpcomingPieces);
		new FallDown();
		new MoveLeft();
		new MoveRight();
		new RotateClockWise();
		new RotateCounterClockWise();
		new DropDown();
		new SavePiece();
		new Exit();
		setControls();
	}
	
	@Override
	public void gameOver() {
		getBoard().paintAllPieces(-1);
		getControls().clear();
		new Exit();
		fallTimer.stop();
		setControls();
		getGui().gameOver();
		paint();
	}
	
	class FallDown extends GameAction{
		private static final int keyCode = 40;
		private int activeInterval = 80;
		
		public FallDown() {
			super(keyCode);
			getControls().add(this);
		}
		
		@Override
		public void pressTask() {
			fallTimer.task();
			fallTimer.setInterval(getLevel() < 12 ? activeInterval : (int)(SPEED_CURVE[getLevel()] - SPEED_CURVE[getLevel()] * 0.4));
			isSoftDropping = true;
		}
		
		@Override
		public void releaseTask() {
			fallTimer.setDelayedInterval(SPEED_CURVE[getLevel()]);
			isSoftDropping = false;
		}
		
	}
	
	class MoveLeft extends GameAction{
		private int initialDelay = 110;
		private int activeInterval = 50;
		private static final int keyCode = 37;
		
		private VariableTimer timer = new VariableTimer() {
			@Override
			public void task() {
				if(!isMovingRight) {
					moveLeft();
					isMovingLeft = true;
				}
			}
		};

		public MoveLeft() {
			super(keyCode);
			getControls().add(this);
		}

		@Override
		public void pressTask() {
			timer.task();
			timer.setInterval(activeInterval, initialDelay);
		}
		
		@Override
		public void releaseTask() {
			timer.stop();
			isMovingLeft = false;
		}
		
	}
	
	class MoveRight extends GameAction{
		private int initialDelay = 110;
		private int activeInterval = 50;
		private static final int keyCode = 39;
		
		private VariableTimer timer = new VariableTimer() {
			@Override
			public void task() {
				if(!isMovingLeft) {
					moveRight();
					isMovingRight = true;
				}
			}
		};

		public MoveRight() {
			super(keyCode);
			getControls().add(this);
		}

		@Override
		public void pressTask() {
			timer.task();
			timer.setInterval(activeInterval, initialDelay);
		}
		
		@Override
		public void releaseTask() {
			timer.stop();
			isMovingRight = false;
		}
		
	}
	
	class RotateClockWise extends GameAction{
		
		private static final int keyCode = 38;

		public RotateClockWise() {
			super(keyCode);
			getControls().add(this);
		}

		@Override
		public void pressTask() {
			rotate(true);
			
		}

		@Override
		public void releaseTask() {
			
		}
		
	}
	
	class RotateCounterClockWise extends GameAction{
		
		private static final int keyCode = 88;

		public RotateCounterClockWise() {
			super(keyCode);
			getControls().add(this);
		}

		@Override
		public void pressTask() {
			rotate(false);
		}

		@Override
		public void releaseTask() {
			
		}
	}
	
	class DropDown extends GameAction{

		private static final int keyCode = 32;

		public DropDown() {
			super(keyCode);
			getControls().add(this);
		}

		@Override
		public void pressTask() {
			dropDown();
		}

		@Override
		public void releaseTask() {
			
		}
	}

	class SavePiece extends GameAction{
		private static final int keyCode = 67;

		public SavePiece() {
			super(keyCode);
			getControls().add(this);
		}

		@Override
		public void pressTask() {
			savePieces();
			
		}

		@Override
		public void releaseTask() {
			
		}
	}

	class Exit extends GameAction{
		private static final int keyCode = 27;
		
		public Exit() {
			super(keyCode);
			getControls().add(this);
		}
		
		@Override
		public void pressTask() {
			System.exit(0);
			
		}

		@Override
		public void releaseTask() {
			
		}
	}

}
