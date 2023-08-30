/**
 * Cell class represents a single cell on the Tic Tac Toe game board.
 * This class stores the state of the cell (empty, cross, or nought)
 * and provides methods to manipulate and render its state.
 *
 * Author: Jeremy Stephens
 * Student ID: 5071232
 * Course: BIT 504
 * Assignment: 2
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Cell {
	// Enum type representing the content of the cell: empty, cross, or nought.
	Player content;

	// Row and column position of this cell on the board grid.
	int row, col;

	/**
	 * Constructor: Initialize cell with given row and column indices.
	 *
	 * @param row Row index for this cell.
	 * @param col Column index for this cell.
	 */
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		clear();  // Initialize cell state as empty.
	}

	/**
	 * Renders the cell's content on the graphical canvas.
	 *
	 * @param g Graphics context for rendering the cell.
	 */
	public void paint(Graphics g) {
		// Graphics2D allows setting of pen's stroke size
		Graphics2D graphic2D = (Graphics2D) g;
		graphic2D.setStroke(new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		// Calculate the coordinates for drawing symbols ('X' or 'O').
		int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;
		int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;

		// Draw 'X' or 'O' based on the cell content.
		if (content == Player.Cross) {
			graphic2D.setColor(Color.RED);
			int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
			int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;
			graphic2D.drawLine(x1, y1, x2, y2);
			graphic2D.drawLine(x2, y1, x1, y2);
		} else if (content == Player.Nought) {
			graphic2D.setColor(Color.BLUE);
			graphic2D.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);
		}
	}

	/**
	 * Reset the cell's content to empty.
	 */
	public void clear() {
		content = Player.Empty;  // Set the value of content to Empty
	}
}