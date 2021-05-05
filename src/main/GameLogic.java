package main;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import resources.GameAction;

public class GameLogic {

	public static final int INITIAL_X = Board.BOARD_WIDTH / 2;
	public static final int INITIAL_Y = Board.BOARD_HEIGHT - 2;
	private static final int MAX_LVL = 29;
	private static final int MAX_VSBLLINES = 5;
	private static final int[] SCORE_PER_LINES = {40, 100, 300, 1200};
	private static final int SCORE_FOR_SOFTDROP = 1;
	private static final int SCORE_FOR_HARDDROP = 2;

	public static int visibleUpcomingPieces = 0;
	private boolean canSave = true;
	@Getter private int level = 1;
	@Getter private int score = 0;
	@Getter private int lines = 0;
	protected int cyclesOnGround = 0;

	@Getter private Board board = new Board();
	private Piece currentPiece;
	private Piece savedPiece;
	@Getter private GUI gui;
	private List<Piece> piecesPool = new ArrayList<>();
	@Getter private List<GameAction> controls = new ArrayList<>();

	public GameLogic(int level, int visibleUpcomingPieces) {
		this.level = level < 1 ? 1 : level > MAX_LVL ? MAX_LVL : level;
		GameLogic.visibleUpcomingPieces = visibleUpcomingPieces < 0 ? 0 : visibleUpcomingPieces > MAX_VSBLLINES ? MAX_VSBLLINES : visibleUpcomingPieces;
		generateUpcomingPieces();
		nextPiece();
		savedPiece = new Piece();
		savedPiece.setPieceType(-1);
		this.gui = new GUI(board, currentPiece, savedPiece.getPieceType(), piecesPool, visibleUpcomingPieces);
	}
	
	public boolean moveDown(boolean isSoftDrop) {
		boolean didItMove = currentPiece.movePiece(board, 0, -1);
		if(didItMove && isSoftDrop) {
			increaseScore(SCORE_FOR_SOFTDROP);
		}
		paint();
		return didItMove;
	}
	
	public boolean moveLeft() {
		boolean didItMove = currentPiece.movePiece(board, -1, 0);
		if(didItMove) cyclesOnGround--;
		paint();
		return didItMove;
	}
	
	public boolean moveRight() {
		boolean didItMove = currentPiece.movePiece(board, 1, 0);
		if(didItMove) cyclesOnGround--;
		paint();
		return didItMove;
	}
	
	public void setControls() {
		for(KeyListener listener:gui.getKeyListeners()) {
			gui.removeKeyListener(listener);
		}
		for(GameAction action:controls) {
			gui.addKeyListener(action);
		}
	}
	
	public boolean rotate(boolean clockwise) {
		boolean didItRotate = currentPiece.rotate(board, clockwise);
		if(didItRotate) cyclesOnGround--;
		paint();
		return didItRotate;
	}
	
	public void paint() {
		gui.setBoard(board);
		gui.setPiecesPool(piecesPool, visibleUpcomingPieces);
		gui.setSavedPiece(savedPiece);
		gui.setPiece(currentPiece);
		gui.setLevel(level);
		gui.setLines(lines);
		gui.setScore(score);
		gui.repaint();
	}

	public int leavePiece() {
		int lines;
		for (Tile tile : currentPiece.getTiles()) {
			board.paintPos(currentPiece.getTileAbsoluteX(tile), currentPiece.getTileAbsoluteY(tile), tile.getValue());
			if(currentPiece.getTileAbsoluteY(tile) >= 19) gameOver();
		}
		nextPiece();
		lines = board.cleanLines();
		paint();
		canSave = true;
		return lines;
	}

	private void nextPiece() {
		Piece piece = piecesPool.remove(0);
		piece.initializePiece();
		currentPiece = piece;
		if (piecesPool.size() - 1 < visibleUpcomingPieces) {
			generateUpcomingPieces();
		}
	}

	private void generateUpcomingPieces() {
		List<Piece> pieces = new ArrayList<>();
		for (int i=0; i<7; i++) {
			Piece piece = getNewPieceOfType(i);
			piece.initializePiece();
			pieces.add(piece);
		}
		Collections.shuffle(pieces);
		piecesPool.addAll(pieces);
	}
	
	private Piece getNewPieceOfType(int n) {
		return PiecesList.values()[n].getPiece();
	}

	public void savePieces() {
		if(canSave) {
			if (savedPiece.getPieceType() != -1) {
				Piece auxiliary = getNewPieceOfType(savedPiece.getPieceType());
				savedPiece = getNewPieceOfType(currentPiece.getPieceType());
				currentPiece = auxiliary;
			} else {
				savedPiece = getNewPieceOfType(currentPiece.getPieceType());
				nextPiece();
			}
			paint();
			canSave = false;
		}
	}
	
	public void dropDown() {
		while(currentPiece.movePiece(board, 0, -1)) {
			increaseScore(SCORE_FOR_HARDDROP);
		}
		addLines(leavePiece());
	}
	
	public void increaseScore(int n) {
		score += n;
	}
	
	public void addLines(int n) {
		int lastLinesNumber = lines;
		if(n>0) increaseScore(SCORE_PER_LINES[n-1] * level);
		lines += n;
		if(lastLinesNumber / 10 < lines / 10) levelUp();
	}
	
	public void levelUp() {
		level++;
	}

	public void gameOver() {
		
	}
	
}
