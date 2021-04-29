package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

public class GameController {

	public static final int INITIAL_X = Board.BOARD_WIDTH / 2;
	public static final int INITIAL_Y = Board.BOARD_HEIGHT - 2;
	private static final int MAX_LVL = 29;
	private static final int MAX_VSBLLINES = 5;

	private int visibleUpcomingPieces = 0;
	@Getter private int level = 1;
	@Getter private int score = 0;
	@Getter private int lines = 0;

	private Board board = new Board();
	private Piece currentPiece;
	private Piece savedPiece;
	private List<Piece> piecesPool = new ArrayList<>();

	public GameController(int level, int visibleUpcomingPieces) {
		this.level = level < 1 ? 1 : level > MAX_LVL ? MAX_LVL : level;
		this.visibleUpcomingPieces = visibleUpcomingPieces < 0 ? 0 : visibleUpcomingPieces > MAX_VSBLLINES ? MAX_VSBLLINES : visibleUpcomingPieces;
	}

	public void paint(Board board) {
		
	}

	public void leavePiece() {
		for (Tile tile : currentPiece.getTiles()) {
			board.paintPos(currentPiece.getTileAbsoluteX(tile), currentPiece.getTileAbsoluteY(tile), tile.getValue());
		}
		nextPiece();
		board.checkRows();
		paint(board);
	}

	private Piece nextPiece() {
		Piece toUse = piecesPool.get(0);
		piecesPool.remove(0);
		if (piecesPool.size() - 1 < visibleUpcomingPieces) {
			generateUpcomingPieces();
		}
		return toUse;
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
		if (savedPiece != null) {
			Piece auxiliary = getNewPieceOfType(savedPiece);
			savedPiece = getNewPieceOfType(currentPiece);
			currentPiece = auxiliary;
		} else {
			savedPiece = getNewPieceOfType(currentPiece);
			nextPiece();
		}
	}

	public void addLines(int n) {
		lines += n;
	}
	
	public void levelUp() {
		level++;
	}
}
