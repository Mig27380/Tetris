package main;

import lombok.Getter;
import lombok.Setter;

public class Tile {
	@Getter private int relativeX;
	@Getter private int relativeY;
	@Getter @Setter private int value = 0;
	@Getter private int centerX = 0;
	@Getter private int centerY = 0;
	

	public Tile(int x, int y) {
		this.relativeX = x;
		this.relativeY = y;
	}

	public void setCenter(int x, int y) {
		this.centerX = x;
		this.centerY = y;
	}
	
	public void setCoordinates(int x, int y) {
		this.relativeX = x;
		this.relativeY = y;
	}
	
	public boolean canMoveTile(Board board, int xMovement, int yMovement){
		return board.isInBounds(xMovement + relativeX, yMovement + relativeY) && board.isPosEmpty(xMovement + relativeX, yMovement + relativeY);
	}
	
	public void rotateTile(boolean clockwise) {
		int[][] rotationMatrix = clockwise ? new int[][] {{0, -1}, {1, 0}} : new int[][] {{0, 1}, {-1, 0}};
		setCoordinates(rotationMatrix[0][0] * relativeX + rotationMatrix[1][0] * relativeY, rotationMatrix[0][1] * relativeX + rotationMatrix[1][1] * relativeY);
	}
}