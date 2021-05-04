package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GUI extends JFrame {
	private static final long serialVersionUID = 6627266978304465915L;
	private short spacing = 1;
	private short cellSize = 31;
	private short gridOffsetX = 12;
	private short gridOffsetY = 12;
	private int windowX = 580, windowY = 702;

	@Setter
	private Board board;
	@Setter
	private Piece piece;
	private int savedPiece;
	private int[] piecesPool = new int[] {0};
	
	public GUI(Board board, int savedPiece, List<Piece> pieces, int quantity) {
		setSize(windowX, windowY);
		setTitle("Tetris®");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		add(new BoardView());
		
		this.board = board;
		piecesPool = new int[quantity];
		for (int i = 0; i < quantity || i < piecesPool.length; i++) {
			piecesPool[i] = pieces.get(i).getPieceType();
		}
		this.savedPiece = savedPiece;
		
	}

	public void setPiecesPool(List<Piece> pieces, int quantity) {
		piecesPool = new int[quantity];
		for (int i = 0; i < quantity || i < piecesPool.length; i++) {
			piecesPool[i] = pieces.get(i).getPieceType();
		}
	}

	public void setSavedPiece(Piece piece) {
		savedPiece = piece.getPieceType();
	}

	class BoardView extends JPanel {
		private static final long serialVersionUID = 2344517835321478570L;

		public void paintComponent(Graphics g) {
			setExternalElements(g);
			paintIndicators(g);
			paintBoard(g);
		}
		
		private void paintBoard(Graphics g) {
			int[][] map = board.getBoard();
			for(int i=2; i<map.length; i++) {
				for(int j=0; j<map[i].length; j++) {
					if(map[map.length - i - 1][j]==2) g.setColor(Color.decode("#10d4f1"));
					else if(map[map.length - i - 1][j]==1) g.setColor(Color.decode("#f7ff15"));
					else if(map[map.length - i - 1][j]==3) g.setColor(Color.decode("#de09f1"));
					else if(map[map.length - i - 1][j]==4) g.setColor(Color.decode("#00d900"));
					else if(map[map.length - i - 1][j]==5) g.setColor(Color.decode("#dc1907"));
					else if(map[map.length - i - 1][j]==6) g.setColor(Color.decode("#e7b314"));
					else if(map[map.length - i - 1][j]==7) g.setColor(Color.decode("#1763ed"));
					else g.setColor(Color.darkGray);
					
					g.fillRect(gridOffsetX+spacing*j+cellSize*j, gridOffsetY+spacing*(i-2)+cellSize*(i-2), cellSize, cellSize);
				}
			}
		}

		private void setExternalElements(Graphics g) {
			g.setColor(Color.decode("#a4d2c8"));
			g.fillRect(0, 0, 2000, 2000);
			g.setColor(Color.white);
			g.fillRect(gridOffsetX, gridOffsetY, 319, 639);

			g.setColor(Color.decode("#73848b"));
			g.fillRect(345, gridOffsetY, 205, 320);
			g.fillRect(345, 350, 205, 150);

			g.setFont(new Font("Courier", Font.BOLD, 30));
			g.setColor(Color.white);
			g.drawString("Next", 415, 40);
			g.drawString("Hold", 415, 380);
		}

		private void paintIndicators(Graphics g) {
			for (byte i = 0; i < piecesPool.length; i++) {
				int height = 0;
				if (i == 0)
					height = 135;
				else if (i == 1)
					height = 220;
				else if (i == 2)
					height = 310;
				paintPreview(g, piecesPool[i], height);
			}
			paintPreview(g, savedPiece, 470);
		}

		private void paintPreview(Graphics g, int value, int height) {
			switch (value) {
			case 1:
				g.setColor(Color.white);
				g.fillRect(379, height - cellSize - 1, (cellSize * 4) + 5, cellSize + 2);
				g.setColor(Color.decode("#10d4f1"));
				for (byte j = 0; j < 4; j++) {
					g.fillRect(380 + cellSize * j + j, height - cellSize, cellSize, cellSize);
				}
				break;
			case 0:
				g.setColor(Color.white);
				g.fillRect(409, height - cellSize * 2 - 1, (cellSize * 2) + 3, (cellSize * 2) + 3);
				g.setColor(Color.decode("#f7ff15"));
				for (byte j = 0; j < 2; j++) {
					for (byte k = 0; k < 2; k++) {
						g.fillRect(410 + cellSize * j + j, height - (cellSize * (k + 1) + k) + 1, cellSize,
								cellSize);
					}
				}
				break;
			case 2:
				g.setColor(Color.white);
				g.fillRect(429, height - cellSize * 2 - 2, cellSize + 2, cellSize + 2);
				g.fillRect(397, height - cellSize - 1, (cellSize * 3) + 4, cellSize + 2);
				g.setColor(Color.decode("#de09f1"));
				g.fillRect(430, height - cellSize * 2 - 1, cellSize, cellSize);
				for (byte j = 0; j < 3; j++) {
					g.fillRect(398 + cellSize * j + j, height - cellSize, cellSize, cellSize);
				}
				break;
			case 3:
				g.setColor(Color.white);
				g.fillRect(429, height - cellSize * 2 - 2, (cellSize * 2) + 3, (cellSize) + 2);
				g.fillRect(429, height - cellSize * 2, (cellSize) + 2, (cellSize) + 2);
				g.fillRect(397, height - cellSize - 1, (cellSize * 2) + 3, (cellSize) + 2);
				g.setColor(Color.decode("#00d900"));
				for (byte j = 0; j < 2; j++) {
					g.fillRect(430 + cellSize * j + j, height - cellSize * 2 - 1, cellSize, cellSize);
				}
				for (byte j = 0; j < 2; j++) {
					g.fillRect(398 + cellSize * j + j, height - cellSize, cellSize, cellSize);
				}
				break;
			case 4:
				g.setColor(Color.white);
				g.fillRect(429, height - cellSize - 1, (cellSize * 2) + 3, (cellSize) + 2);
				g.fillRect(429, height - cellSize * 2, (cellSize) + 2, (cellSize) + 2);
				g.fillRect(397, height - cellSize * 2 - 2, (cellSize * 2) + 3, (cellSize) + 2);
				g.setColor(Color.decode("#dc1907"));
				for (byte j = 0; j < 2; j++) {
					g.fillRect(430 + cellSize * j + j, height - cellSize, cellSize, cellSize);
				}
				for (byte j = 0; j < 2; j++) {
					g.fillRect(398 + cellSize * j + j, height - cellSize * 2 - 1, cellSize, cellSize);
				}
				break;
			case 5:
				g.setColor(Color.white);
				g.fillRect(461, height - cellSize * 2 - 2, cellSize + 2, (cellSize) + 2);
				g.fillRect(461, height - cellSize * 2, (cellSize) + 2, (cellSize) + 2);
				g.fillRect(397, height - cellSize - 1, (cellSize * 3) + 4, (cellSize) + 2);
				g.setColor(Color.decode("#e7b314"));
				for (byte j = 0; j < 3; j++) {
					g.fillRect(398 + cellSize * j + j, height - cellSize, cellSize, cellSize);
				}
				g.fillRect(462, height - cellSize * 2 - 1, cellSize, cellSize);
				break;
			case 6:
				g.setColor(Color.white);
				g.fillRect(397, height - cellSize * 2 - 2, cellSize + 2, (cellSize) + 2);
				g.fillRect(397, height - cellSize * 2, (cellSize) + 2, (cellSize) + 2);
				g.fillRect(397, height - cellSize - 1, (cellSize * 3) + 4, (cellSize) + 2);
				g.setColor(Color.decode("#1763ed"));
				for (byte j = 0; j < 3; j++) {
					g.fillRect(398 + cellSize * j + j, height - cellSize, cellSize, cellSize);
				}
				g.fillRect(398, height - cellSize * 2 - 1, cellSize, cellSize);
				break;
			}
		}
	}
}
