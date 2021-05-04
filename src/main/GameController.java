package main;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import resources.GameAction;

public class GameController {

	public static final int INITIAL_X = Board.BOARD_WIDTH / 2;
	public static final int INITIAL_Y = Board.BOARD_HEIGHT - 2;
	private static final int MAX_LVL = 29;
	private static final int MAX_VSBLLINES = 5;

	public static int visibleUpcomingPieces = 0;
	private boolean canSave = true;
	@Getter private int level = 1;
	@Getter private int score = 0;
	@Getter private int lines = 0;

	private Board board = new Board();
	private Piece currentPiece;
	private Piece savedPiece;
	private GUI gui;
	private List<Piece> piecesPool = new ArrayList<>();

	public GameController(int level, int visibleUpcomingPieces) {
		this.level = level < 1 ? 1 : level > MAX_LVL ? MAX_LVL : level;
		GameController.visibleUpcomingPieces = visibleUpcomingPieces < 0 ? 0 : visibleUpcomingPieces > MAX_VSBLLINES ? MAX_VSBLLINES : visibleUpcomingPieces;
		generateUpcomingPieces();
		nextPiece();
		savedPiece = new Piece();
		savedPiece.setPieceType(-1);
		this.gui = new GUI(board, savedPiece.getPieceType(), piecesPool, visibleUpcomingPieces);
	}
	
	public void setControls(List<GameAction> controls) {
		for(KeyListener listener:gui.getKeyListeners()) {
			gui.removeKeyListener(listener);
		}
		for(GameAction action:controls) {
			gui.addKeyListener(action);
		}
	}
	
	public void paint() {
		gui.setBoard(board);
		gui.setPiecesPool(piecesPool, visibleUpcomingPieces);
		gui.setSavedPiece(savedPiece);
		gui.setPiece(currentPiece);
		gui.repaint();
	}

	public void leavePiece() {
		for (Tile tile : currentPiece.getTiles()) {
			board.paintPos(currentPiece.getTileAbsoluteX(tile), currentPiece.getTileAbsoluteY(tile), tile.getValue());
		}
		nextPiece();
		board.checkRows();
		paint();
		canSave = true;
	}

	private void nextPiece() {
		currentPiece = piecesPool.remove(0);
		if (piecesPool.size() - 1 < visibleUpcomingPieces) {
			generateUpcomingPieces();
		}
	}

	private void generateUpcomingPieces() {
		List<Piece> pieces = new ArrayList<>();
		for (PiecesList p : PiecesList.values()) {
			pieces.add(p.getPiece());
		}
		Collections.shuffle(pieces);
		piecesPool.addAll(pieces);
	}
	
	private Piece getNewPieceOfType(Piece piece) {
		return PiecesList.values()[piece.getPieceType()].getPiece();
	}

	public void savePieces() {
		if(canSave) {
			if (savedPiece.getPieceType() != -1) {
				Piece auxiliary = getNewPieceOfType(savedPiece);
				savedPiece = getNewPieceOfType(currentPiece);
				currentPiece = auxiliary;
			} else {
				savedPiece = getNewPieceOfType(currentPiece);
				nextPiece();
			}
			canSave = false;
		}
	}
	
	public void dropDown() {
		while(currentPiece.movePiece(board, 0, -1));
		leavePiece();
	}
	
	public void increaseScore(int n) {
		score += n;
	}
	
	public void addLines(int n) {
		int lastLinesNumber = lines;
		lines += n;
		if(lastLinesNumber % 10 < lines % 10) levelUp();
	}
	
	public void levelUp() {
		level++;
	}
}
