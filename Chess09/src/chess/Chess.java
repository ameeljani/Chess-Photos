
/**
 * @author Ameel Jani
 * @author Victor Ochoa
 */







package chess;

import view.Controller;

public class Chess {
	
	/**
	 * Starts the chess game. Keeps the game going and when the game is ended, the player who wins is printed out. 
	 * @param args : Takes the String input.
	 */

	public static void main(String[] args) {
		
		
		Controller c= new Controller();
		String s=null;
		
		
		//draw board
		
		c.drawboard();
		
		
		
		
		
		//start game and prints out winner
		
		c.play();
		
		
		
		
		
		
		
		
		
		
		
		

	}

}
