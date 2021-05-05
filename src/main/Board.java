package main;

import java.util.Arrays;

public class Board {
	public static final int BOARD_WIDTH = 10;
	public static final int BOARD_HEIGHT = 22;
	private int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH];
	
	public boolean isPosEmpty(int x, int y) {
		try {
			return board[y][x] == 0;
		} catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public boolean isInBounds(int x, int y) {
		return (x>=0 && x<BOARD_WIDTH) && (y>=0 && y<BOARD_HEIGHT);
	}
	
	public void paintPos(int x, int y, int value) {
		if(isInBounds(x, y) && isPosEmpty(x, y)) {
			board[y][x]=value;
		}
	}
	
	private boolean isRowFull(int height) {
		for(int i=0; i<board.length; i++) {
			if(isPosEmpty(i, height)) {
				return false;
			}
		}
		return true;
	}
	
	private void cleanRow(int height) {
		for(int i=height; i<board.length - 1; i++) {
			board[i] = board[i+1].clone();
		}
	}
	
	public int checkRows() {
		int lineCount = 0;
		for(int i=0; i<BOARD_HEIGHT; i++) {
			if(isRowFull(i)) {
				cleanRow(i);
				lineCount++;
			}
		}
		return lineCount;
	}
	
	public int cleanLines() {
		int lineCount;
		int totalLineCount = 0;
		do {
			lineCount = checkRows();
			totalLineCount += lineCount;
		} while(lineCount>0);
		return totalLineCount;
	}
	
	public int[][] getBoard(){
		return this.board.clone();
	}
	
	public void fillBoard(int n) {
		for(int i=0; i<BOARD_HEIGHT; i++) {
			Arrays.fill(board[i], n);
		}
	}
	
	public void paintAllPieces(int n) {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				if(!isPosEmpty(j, i)) board[i][j] = n;
			}
		}
	}
	
}
