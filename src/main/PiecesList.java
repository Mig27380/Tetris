package main;

public enum PiecesList {
	O(new Tile(-1, 0), new Tile(-1, 1), new Tile(0, 0), new Tile(0, 1)),
	I(new Tile(-1, 0), new Tile(0, 0), new Tile(1, 0), new Tile(2, 0)),
	T(new Tile(-1, 0), new Tile(0, 0), new Tile(0, 1), new Tile(1, 0)),
	S(new Tile(-1, 0), new Tile(0, 0), new Tile(0, 1), new Tile(1, 1)),
	Z(new Tile(-1, 1), new Tile(0, 1), new Tile(0, 0), new Tile(1, 0)),
	J(new Tile(-1, 1), new Tile(-1, 0), new Tile(0, 0), new Tile(1, 0)),
	L(new Tile(-1, 0), new Tile(0, 0), new Tile(1, 0), new Tile(1, 1));

	private Piece piece = new Piece();

	private PiecesList(Tile tile1, Tile tile2, Tile tile3, Tile tile4) {
		piece.setTiles(tile1, tile2, tile3, tile4);
		piece.setPieceType(this.ordinal());
		for (Tile t : piece.getTiles()) {
			t.setValue(this.ordinal() + 1);
		}
	}

	public Piece getPiece() {
		return piece;
	}
}
