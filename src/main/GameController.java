package main;

import resources.GameAction;

public class GameController extends GameLogic {

	public GameController(int level, int visibleUpcomingPieces) {
		super(level, visibleUpcomingPieces);
		new MoveDown();
		setControls();
		System.out.println(getControls().size());
	}
	
	class MoveDown extends GameAction{
		public MoveDown() {
			super(40);
			getControls().add(this);
		}
		
		@Override
		public void pressTask() {
			System.out.println(moveDown());
		}
		
		@Override
		public void releaseTask() {
			
		}
		
	}
}
