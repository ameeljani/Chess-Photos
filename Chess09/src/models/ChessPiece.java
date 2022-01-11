/**
 * @author Ameel Jani
 * @author Victor Ochoa
 */



package models;

/**
 * This is the ChessPiece Class. It represents the pieces used in the chess game
 * It is used to keep track of Pieces and their locations in the game. 
 * 
 */

public abstract class ChessPiece {
	
	
	
	/**
	 * This is a static method that converts a user-inputed file value to a file coordinate matching up with the program's 2D array representation of the chess board
	 * @param f: the file coordinate of a square on the chess board the user enters
	 * @return an integer value used to identify the file of the square the user is referring to in the program's 2D array representation of the chess board
	 */
	
	
	public static int fileToInt(char f) {
		switch(f) {
			case 'a':
				return 0;
			case 'b':
				return 1;
			case 'c':
				return 2;
			case 'd':
				return 3;
			case 'e':
				return 4;
			case 'f':
				return 5;
			case 'g':
				return 6;
			case 'h':
				return 7;
			default:
				return -1;
		}
	}
	
	/**
	 * This is a static method that converts a user-inputed rank value to a rank coordinate matching up with the program's 2D array representation of the chess board
	 * @param f: the rank coordinate of a square on the chess board the user enters
	 * @return an integer value used to identify the rank of the square the user is referring to in the program's 2D array representation of the chess board
	 */
	
	
	public static int rankToInt(char r) {
		return Integer.parseInt(String.valueOf(r)) - 1;
	}
	
	
	/**
	 * This is a static method that moves a piece on the chess board from one square to another
	 * @param oldFile: an integer which is the file coordinate of the square on the chess board that the piece we want to move currently occupies
	 * @param oldRank: an integer which is the rank coordinate of the square on the chess board that the piece we want to move currently occupies
	 * @param newFile: an integer which is the file coordinate of the square on the chess board that is the intended destination of the piece we want to move
	 * @param newFile: an integer which is the rank coordinate of the square on the chess board that is the intended destination of the piece we want to move
	 * @param board: a 2D array which is the chess board the game is being played on
	 */
	
	
	

	public static void move(int oldFile, int oldRank, int newFile, int newRank, ChessPiece[][] board) {
		board[newFile][newRank] = board[oldFile][oldRank];
		board[newFile][newRank].setFile(newFile);
		board[newFile][newRank].setRank(newRank);
		
		if(board[newFile][newRank] instanceof King)
		{
			King k= (King) board[newFile][newRank];
			k.ismoved=true;
		}
		
		if(board[newFile][newRank] instanceof Rook)
		{
			Rook r= (Rook) board[newFile][newRank];
			r.ismoved=true;
		}
		
		
		board[oldFile][oldRank] = null;
	}
	
	/**
	 * An integer value that is the current file coordinate of the ChessPiece object on the board
	 */
	
	private int file;
	
	/**
	 * An integer value that is the current rank coordinate of the ChessPiece object on the board
	 */
	
	private int rank;
	
	/**
	 * A String value that is the current color of the ChessPiece object
	 */
	
	private String color;
	
	
	
	/**
	 * An abstract method that determines if a particular move is valid for a particular ChessPiece piece/object on the board
	 * @param newFile: the prospective file this piece will move to
	 * @param newRank: the prospective file this piece will move to
	 * @param board: the game chess board
	 * @return a boolean value that tells us whether the move to newFile, newRank is valid for this ChessPiece object
	 * 
	 */
	
	public abstract boolean valid(int fileMove, int rankMove, ChessPiece[][] board);
	
	
	/**
	 * Constructor of the class
	 * @param file: the file of this piece
	 * @param rank: the rank of this piece
	 * @param color: the color of this piece
	 * 
	 */
	
	
	
	public ChessPiece(int file, int rank, String color)
	{
		this.file = file;
		this.rank = rank;
		this.color = color;
	}
	
	/**
	 * Returns the file coordinate of this piece on the board
	 * @return the file coordinate of this piece on the board
	 */
	
	public int getFile()
	{
		return this.file;
	}
	
	/**
	 * Returns the rank coordinate of this piece on the board
	 * @return the rank coordinate of this piece on the board
	 */
	
	
	public int getRank()
	{
		return this.rank;
	}
	
	/**
	 * Returns the color of this piece
	 * @return the color of this piece
	 */
	
	public String getColor()
	{
		return color;
	}
	
	/**
	 * Sets the file field of a ChessPiece object to an integer passed in as a parameter
	 * @param newFile: the new file of this piece
	 */
	
	public void setFile(int newFile)
	{
		this.file = newFile;
	}
	
	/**
	 * Sets the rank field of a ChessPiece object to an integer passed in as a parameter
	 * @param newRank: the new rank of this piece
	 */
	
	public void setRank(int newRank)
	{
		this.rank = newRank;
	}
	
	/**
	 * Sets the color field of a ChessPiece object to a String passed in as a parameter
	 * @param newColor: the new color of this piece
	 */
	
	public void setColor(String newColor)
	{
		this.color= newColor;
	}
	
	/**
	 * A method that returns the string representation of the color of a ChessPiece object
	 * @return a one-character String that represents the color of the piece
	 */

	@Override
	public String toString() {
		if (this.color.equals("black"))
			return "b";
		else
			return "w";
	}
	

}
