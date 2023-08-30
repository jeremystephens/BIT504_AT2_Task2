/**
 * GameMain class provides the main logic and user interface for the Tic Tac Toe game.
 * This class sets up the game board, processes user input, and manages game states.
 * 
 * Author: Jeremy Stephens 
 * Student ID: 5071232
 * Course: BIT 504 
 * Assignment: 2 
 */

// Import Statements
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Main game class for Tic Tac Toe.
 * Manages game state, UI, and mouse interactions.
 */
public class GameMain extends JPanel implements MouseListener {

    // Game settings
    public static final int ROWS = 3;  // Grid rows
    public static final int COLS = 3;  // Grid columns
    public static final String TITLE = "Tic Tac Toe";

    // Drawing constants
    public static final int CELL_SIZE = 100;  // Width and height of each grid cell
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS; // Canvas width
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS; // Canvas height
    
    //Noughts and Crosses are displayed inside a cell, with padding from border
    public static final int CELL_PADDING = CELL_SIZE / 6; 	
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; 
    public static final int SYMBOL_STROKE_WIDTH = 8;

    // Game components
    private Board board;  // Represents the game board
    private GameState currentState;  // Current status of the game
    private Player currentPlayer;  // Current player (X or O)
    private JLabel statusBar;  // Label for game status (like whose turn it is)

    /** Constructor to initialize the game and UI components.*/     
    public GameMain() {   
        // Register mouse click event
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                GameMain.this.mouseClicked(e);
            }
        });
        
		// Setup the status bar (JLabel) to display status message       
        statusBar = new JLabel("         ");       
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));       
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));       
        statusBar.setOpaque(true);       
        statusBar.setBackground(Color.LIGHT_GRAY);  

		//Layout of the panel is in border layout
        setLayout(new BorderLayout());       
        add(statusBar, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));
        
        //Create new instance of the game "Board" class
        board = new Board();
        
        //Call the method to initialise the game board
        initGame(); 
    }
	
	public static void main(String[] args) {
		    // Run GUI code in Event Dispatch thread for thread safety.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
				//Create a main window to contain the panel
				JFrame frame = new JFrame(TITLE);
				
				//Create the main panel of the game and add it to the frame						
				GameMain gamePanel = new GameMain();
	            frame.add(gamePanel);
				
				//Set default close operation to exit the main panel frame
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            
				frame.pack();             
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setResizable(false);
	         }
		 });
	}
	
	/** Custom painting codes on this JPanel */
	public void paintComponent(Graphics g) {
		//Fill background and set colour to white
		super.paintComponent(g);
		setBackground(Color.WHITE);
		//Ask the game board to paint itself
		board.paint(g);
		
		//Set status bar message
		if (currentState == GameState.Playing) {          
			statusBar.setForeground(Color.BLACK);          
			if (currentPlayer == Player.Cross) {   
				statusBar.setText("X's Turn"); 							//Set the status bar to display the 'X' turn message
			} else {    
				statusBar.setText("O's Turn"); 							//Set the status bar to display the 'O' turn message
			}       
			} else if (currentState == GameState.Draw) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("It's a Draw! Click to play again."); //Set the status bar to display the 'Draw' message   
			} else if (currentState == GameState.Cross_won) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'X' Won! Click to play again."); 	//Set the status bar to display the 'X' wins message       
			} else if (currentState == GameState.Nought_won) {          
				statusBar.setForeground(Color.RED);          
				statusBar.setText("'O' Won! Click to play again.");    	//Set the status bar to display the 'O' wins message    
			}
		}
		
	  /** Initialise the game-board contents and the current status of GameState and Player) */
		public void initGame() {
			for (int row = 0; row < ROWS; ++row) {          
				for (int col = 0; col < COLS; ++col) {  
					// all cells empty
					board.cells[row][col].content = Player.Empty;           
				}
			}
			 currentState = GameState.Playing;
			 currentPlayer = Player.Cross;
		}
		
		/**After each turn check to see if the current player hasWon by putting their symbol in that position, 
		 * If they have the GameState is set to won for that player
		 * If no winner then isDraw is called to see if deadlock, if not GameState stays as PLAYING  
		 */
		public void updateGame(Player thePlayer, int row, int col) {
			//Check for win after play
			if(board.hasWon(thePlayer, row, col)) {
				//Check which player has won and update the currentstate to the appropriate gamestate for the winner
				if (thePlayer == Player.Cross){
					currentState = GameState.Cross_won;
				} else {
					currentState = GameState.Nought_won;
				}
				
			//Set the currentstate to the draw gamestate				
			} else if (board.isDraw()) { 
					currentState = GameState.Draw;
			}
			//Otherwise no change to current state of playing
		}
		
		/** Event handler for the mouse click on the JPanel. If selected cell is valid and Empty then current player is added to cell content.
		 *  UpdateGame is called which will call the methods to check for winner or Draw. if none then GameState remains playing.
		 *  If win or Draw then call is made to method that resets the game board.  Finally a call is made to refresh the canvas so that new symbol appears
		 */
		public void mouseClicked(MouseEvent e) {  
			// Get the coordinates of where the click event happened            
			int mouseX = e.getX();             
			int mouseY = e.getY();             
			// Get the row and column clicked             
			int rowSelected = mouseY / CELL_SIZE;             
			int colSelected = mouseX / CELL_SIZE;               			
			if (currentState == GameState.Playing) {                
				if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.Empty) {
					// Move  
					board.cells[rowSelected][colSelected].content = currentPlayer; 
					// Update currentState                  
					updateGame(currentPlayer, rowSelected, colSelected); 
					// Switch player
					if (currentPlayer == Player.Cross) {
						currentPlayer =  Player.Nought;
					} else {
					currentPlayer = Player.Cross;
					}
				}             
			} else {        
			// Game over and restart              
			initGame();            
			}
		
			//Redraw the graphics on the UI          
			repaint();
		}
		
	@Override
	public void mousePressed(MouseEvent e) {
		//  Auto-generated, event not used		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated, event not used		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Auto-generated,event not used
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated, event not used		
	}
}