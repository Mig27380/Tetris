package main;

import lombok.Getter;

public class Piece {

	@Getter private Tile[] tiles;
	@Getter private int x;
	@Getter private int y;
	private int rotation = 0;
	@Getter private int pieceType = 0;

	public static final int INITIAL_X = Board.BOARD_WIDTH / 2;
	public static final int INITIAL_Y = Board.BOARD_HEIGHT-5;

	public Piece() {
		initializePiece();
	}

	public void rotate(Board board, boolean clockwise) {
		if (this.pieceType!=0) {
			int oldRotation = rotation;
			rotation = calculateRotation(clockwise);
			for(Tile tile : tiles) {
				tile.rotateTile(clockwise);
			}
			if(!offset(board, oldRotation, rotation)) {
				for(Tile tile : tiles) {
					tile.rotateTile(!clockwise);
				}
				rotation = calculateRotation(!clockwise);
			}
		}
	}
	
	public boolean offset(Board board, int oldRotation, int newRotation) {
			int[] offsetVal1 = new int[2];
			int[] offsetVal2 = new int[2];
			int[] endOffset = new int[2];
			int[][][] currentOffsetData;
			
			if(this.pieceType > 1) {
				currentOffsetData = OffsetData.JLSTZ_OFFSET.getOffsetData();
			}
			else {
				currentOffsetData = OffsetData.I_OFFSET.getOffsetData();
			}
			
			for(int[][] offsetData: currentOffsetData) {
				offsetVal1 = offsetData[oldRotation];
				offsetVal2 = offsetData[newRotation];
				for(int i=0; i<endOffset.length; i++) {
					endOffset[i] = offsetVal1[i] - offsetVal2[i];
				}
				if(canMovePiece(board, endOffset[0], endOffset[1])) {
					movePiece(board, endOffset[0], endOffset[1]);
					return true;
				}
			}
			
			return false;
	}
	
	private int calculateRotation(boolean clockwise) {
		int rotation = this.rotation;
		if (clockwise)
			rotation++;
		else
			rotation--;
		rotation = rotation>3 ? 0 : (rotation<0 ? 3 : rotation);
		return rotation;
	}

	public int getTileAbsoluteX(Tile tile) {
		return tile.getRelativeX() + x;
	}

	public int getTileAbsoluteY(Tile tile) {
		return tile.getRelativeY() + y;
	}

	public void initializePiece() {
		x = INITIAL_X;
		y = INITIAL_Y;
		rotation = 0;
		tiles = new Tile[4];
	}

	public void setTiles(Tile tile1, Tile tile2, Tile tile3, Tile tile4) {
		tiles[0] = tile1;
		tiles[1] = tile2;
		tiles[2] = tile3;
		tiles[3] = tile4;
	}
	
	public boolean canMovePiece(Board board, int xMovement, int yMovement) {
		for (Tile t : tiles) {
			if (!t.canMoveTile(board, xMovement + x, yMovement + y))
				return false;
		}
		return true;
	}
	
	public boolean movePiece(Board board, int xMovement, int yMovement) {
		if(!canMovePiece(board, xMovement, yMovement)) return false;
		x+=xMovement;
		y+=yMovement;
		System.out.println("New x: " + x + " - New y: " + y);
		return true;
	}
	
	public void setPieceType(int n) {
		this.pieceType = n;
		if(n == 1) x--;
	}

}
