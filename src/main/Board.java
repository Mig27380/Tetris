package main;

import java.util.Arrays;

import lombok.Getter;

public class Board {
	public static final int BOARD_WIDTH = 10;
	public static final int BOARD_HEIGHT = 22;
	@Getter private int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH];
	
	public Board() {
		cleanBoard();
	}
	
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
	
	private boolean checkRow(int height) {
		for(int i:board[height]) {
			if(i==0) {
				return false;
			}
		}
		return true;
	}
	
	private int[] getRow(int height) {
		int[] row= new int[BOARD_WIDTH];
		for(int i=0; i<row.length; i++) {
			row[i] = board[height][i];
		}
		return row;
	}
	
	private void cleanRow(int height) {
		for(int i=BOARD_HEIGHT-2; i>=height; i--) {
			board[i] = getRow(i+1); 
		}
	}
	
	public int checkRows() {
		int lineCount = 0;
		for(int i=0; i<BOARD_HEIGHT; i++) {
			if(checkRow(i)) {
				cleanRow(i);
				lineCount++;
			}
		}
		return lineCount;
	}
	
	private void cleanBoard() {
		for(int i=0; i<BOARD_HEIGHT; i++) {
			Arrays.fill(board[i], 0);
		}
	}
	
}
