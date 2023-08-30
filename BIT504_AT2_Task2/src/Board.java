/**
 * Board class represents the Tic-Tac-Toe game board.
 * It manages the 2D array of Cell objects, checks the state of the game,
 * and handles the drawing of the grid and symbols on the game board.
 * 
 * Author: Jeremy Stephens 
 * Student ID: 5071232
 * Course: BIT 504 
 * Assignment: 2 
 */

import java.awt.*;

public class Board {
	// Define the width of the grid lines
	public static final int GRID_WIDTH = 8;
	// Define half the width of grid lines
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

	// Declare a 2D array of Cell objects
	Cell [][] cells;

	/** 
	 * Board constructor to initialize the 2D array of cells.
	 */
	public Board() {
		// Initialize the 2D array cells using row and column information
		cells = new Cell[GameMain.ROWS][GameMain.COLS];
		
		// Populate the 2D array with new Cell objects
		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				cells[row][col] = new Cell(row, col);
			}
		}
	}

	/**
	 * Check if the game board is in a draw state.
	 * @return true if it's a draw, false otherwise
	 */
	public boolean isDraw() {
		// Loop through all cells to see if any are still empty
		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				if (cells[row][col].content == Player.Empty) {
					return false; // Found an empty cell, so not a draw
				}
			}
		}
		return true; // No empty cells, it's a draw
	}

	/**
	 * Check for a winning move.
	 * @return true if the current player wins, false otherwise
	 */
	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
		// Check for 3-in-a-row horizontally
		if (cells[playerRow][0].content == thePlayer && cells[playerRow][1].content == thePlayer && cells[playerRow][2].content == thePlayer) {
			return true;
		}
		
		// Check for 3-in-a-row vertically
		if (cells[0][playerCol].content == thePlayer && cells[1][playerCol].content == thePlayer && cells[2][playerCol].content == thePlayer) {
			return true;
		}
		
		// Check for 3-in-a-row diagonally
		if (cells[0][0].content == thePlayer && cells[1][1].content == thePlayer && cells[2][2].content == thePlayer) {
			return true;
		}
		
		// Check for 3-in-a-row diagonally in the other direction
		if (cells[0][2].content == thePlayer && cells[1][1].content == thePlayer && cells[2][0].content == thePlayer) {
			return true;
		}
		
		// If none of the conditions are met, return false
		return false;
	}

	/**
	 * Paint the board and the cells.
	 * @param g Graphics object used for drawing.
	 */
	public void paint(Graphics g) {
		// Set the color and draw the horizontal & vertical lines
		g.setColor(Color.gray);
		for (int row = 1; row < GameMain.ROWS; ++row) {
			g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDHT_HALF,
				GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
				GRID_WIDTH, GRID_WIDTH);
		}
		for (int col = 1; col < GameMain.COLS; ++col) {
			g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDHT_HALF, 0,
				GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
				GRID_WIDTH, GRID_WIDTH);
		}

		// Draw the contents of each cell
		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				cells[row][col].paint(g);  // each cell paints itself
			}
		}
	}
}
