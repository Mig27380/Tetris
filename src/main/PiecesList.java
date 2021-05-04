package main;

import java.util.ArrayList;
import java.util.List;

public enum PiecesList {
	O(new Tile(-1, 0), new Tile(-1, 1), new Tile(0, 0), new Tile(0, 1)),
	I(new Tile(-1, 0), new Tile(0, 0), new Tile(1, 0), new Tile(2, 0)),
	T(new Tile(-1, 0), new Tile(0, 0), new Tile(0, 1), new Tile(1, 0)),
	S(new Tile(-1, 0), new Tile(0, 0), new Tile(0, 1), new Tile(1, 1)),
	Z(new Tile(-1, 1), new Tile(0, 1), new Tile(0, 0), new Tile(1, 0)),
	L(new Tile(-1, 0), new Tile(0, 0), new Tile(1, 0), new Tile(1, 1)),
	J(new Tile(-1, 1), new Tile(-1, 0), new Tile(0, 0), new Tile(1, 0));

	private Piece piece = new Piece();
	private List<Tile> tiles = new ArrayList<>();

	private PiecesList(Tile tile1, Tile tile2, Tile tile3, Tile tile4) {
		piece.setTiles(tile1, tile2, tile3, tile4);
		tiles.add(tile1);
		tiles.add(tile2);
		tiles.add(tile3);
		tiles.add(tile4);
		piece.setPieceType(this.ordinal());
		for (Tile t : tiles) {
			t.setValue(this.ordinal() + 1);
		}
	}

	public Piece getPiece() {
		piece.initializePiece();
		piece.setTiles(tiles.get(0), tiles.get(1), tiles.get(2), tiles.get(3));
		return piece;
	}
}
