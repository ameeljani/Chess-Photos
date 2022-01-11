/**
 * @author Ameel Jani
 * @author Victor Ochoa
 */


package view;
import models.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * This is the Controller Class. It is used the control and facilitate the playing of a chess game
 */

public class Controller {
	
	
	/**
	 * A 2D array of ChessPiece objects that represents the chess board the chess game is being played on 
	 */
	private ChessPiece[][] board;
	
	/**
	 * A String value that indicates which side (black or white) currently is allowed to move one of their pieces (whose turn it is)
	 */
	
	private String currentplayerside;
	
	/**
	 * A String value that indicates the winner (black or white) of the chess game being played
	 */
	
	private String winner;
	
	/**
	 * An integer value that indicates the file value of the chess board square currently occupied by the black King
	 */
	
	private int bkingfile;
	
	/**
	 * An integer value that indicates the rank value of the chess board square currently occupied by the black King
	 */
	
	private int bkingrank;
	
	/**
	 * An integer value that indicates the file value of the chess board square currently occupied by the white King
	 */
	
	private int wkingfile;
	
	/**
	 * An integer value that indicates the rank value of the chess board square currently occupied by the white King
	 */
	
	private int wkingrank;
	
	/**
	 * This field is a Point object. It represents the current square on the chess board that a Pawn object belonging to the player whose turn is not currently up resides on.
	 * The Pawn on this particular square is the only piece that can be potentially currently captured enpassant by another Pawn in the game. 
	 */
	
	private Point enpasssq;
	
	
	
	/**
	 * Constructor of the class; Sets up the initial state of the board with the pieces' original positions
	 * 
	 */
	
	
	
	public Controller()
	{
		board = new ChessPiece[8][8];
		winner=null;
		//empty spaces
		for(int i=2;i<=5;i++)
		{
			for(int j=0;j<8;j++)
			{
				board[i][j]=null;
			}
		}
		
		//black pawns
		for(int j=0;j<8;j++)
		{
			board[j][6]=new Pawn(j,6,"black");
		}
		
		//white pawns
		for(int j=0;j<8;j++)
		{
			board[j][1]=new Pawn(j,1,"white");
		}

		//black back row
		board[0][7]= new Rook(0,7,"black");
		board[1][7]= new Knight(1,7,"black");
		board[2][7]= new Bishop(2,7,"black");
		board[3][7]= new Queen(3,7,"black");
		board[4][7]= new King(4,7,"black");
		board[5][7]= new Bishop(5,7,"black");
		board[6][7]= new Knight(6,7,"black");
		board[7][7]= new Rook(7, 7,"black");

		//white back row
		board[0][0]= new Rook(0,0,"white");
		board[1][0]= new Knight(1,0,"white");
		board[2][0]= new Bishop(2,0,"white");
		board[3][0]= new Queen(3,0,"white");
		board[4][0]= new King(4,0,"white");
		board[5][0]= new Bishop(5,0,"white");
		board[6][0]= new Knight(6,0,"white");
		board[7][0]= new Rook(7,0,"white");
		
		
		bkingfile=4;
		bkingrank=7;
		wkingfile=4;
		wkingrank=0;
		enpasssq=null;
		
		
		
		
		
		
		
		
	}
	
	
	
	
	/**
	 * This method draws a graphical representation of the current chess board on the console for the user
	 * The drawing indicates the present locations of all the game pieces on the board
	 */
	
	public void drawboard()
	{
		for (int rank = 7; rank >= 0; rank--) {
			for (int file = 0; file < 8; file++) {

				if (board[file][rank] == null) {
					if (rank % 2 == 0) {
						if (file % 2 == 0) System.out.print("## ");
						else System.out.print("   ");
					} else {
						if (file % 2 == 0) System.out.print("   ");
						else System.out.print("## ");
					}

				} else {
					System.out.print(board[file][rank] + " ");
				}

			}
			System.out.println(rank + 1);
		}
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}
	
	
	
	/**
	 * This method directs and facilitates the playing of the game. It asks the user for input and moves the pieces on the board in accordance with those inputs, if valid
	 */
	
	public void play()
	{

		currentplayerside = "White";
		boolean game = true;
		boolean drawoffered=false;
		boolean isresign=false;
		Scanner s = new Scanner(System.in);
		char promotion='\0';
		while (game) {
			System.out.print(currentplayerside + "'s move: ");
			String line = s.nextLine();
			String[] arr=line.split(" ");
			String piece=arr[0];
			if(drawoffered==true)
			{
				if(piece.equals("draw"))
				{
					game=false;
					continue;
					
				}
				else
				{
					drawoffered=false;
				}
			}
			
			if(piece.equals("resign"))
			{
				if(currentplayerside.equals("White"))
				{
					winner="Black";
				}
				else
				{
					winner="White";
				}
				game=false;
				isresign=true;
				continue;
			}
			
			
			
			String move=arr[1];
			String other=null;
			
			if(arr.length==3)
			{
				other=arr[2];
			}
			
			
			promotion='\0';
			if(drawoffered==true)
			{
				if(piece.equals("draw"))
				{
					game=false;
					continue;
					
				}
				else
				{
					drawoffered=false;
				}
			}
			
			
			
			
			
			
			int pieceFile = ChessPiece.fileToInt(piece.charAt(0));
			int pieceRank = ChessPiece.rankToInt(piece.charAt(1));
			int moveFile = ChessPiece.fileToInt(move.charAt(0));
			int moveRank = ChessPiece.rankToInt(move.charAt(1));
			
			
			if(pieceFile<=-1 || pieceRank<=-1 || moveFile<=-1 || moveRank<=-1 || pieceRank>7)
			{
				System.out.println("Illegal move, try again");
				System.out.println();
				continue;
			}
			
			if(board[pieceFile][pieceRank]==null)
			{
				System.out.println("Illegal move, try again");
				System.out.println();
				continue;
			}
			
			String side=currentplayerside.toLowerCase();
			if(board[pieceFile][pieceRank].getColor().equals(side)==false)
			{
				System.out.println("Illegal move, try again");
				System.out.println();
				continue;
			}
			
			
			
			
			
			
			
			
			
			//look for draw
			
			if(other!=null)
			{
				String drawoffer=other;
				
				if(drawoffer.equals("draw?"))
				{
					drawoffered=true;
				}
				
				//promotion set
				else if(drawoffer.equals("N") || drawoffer.equals("R") || drawoffer.equals("Q") || drawoffer.equals("B"))
				{
					if(board[pieceFile][pieceRank] instanceof Pawn   && ((currentplayerside.equals("White") && pieceRank==6 && moveRank==7 && (pieceFile==moveFile || moveFile==pieceFile+1 || moveFile==pieceFile-1) ) || ((currentplayerside.equals("Black") && (pieceFile==moveFile|| moveFile==pieceFile+1 || moveFile==pieceFile-1) && pieceRank==1 && moveRank==0)))  )
					{
						promotion=drawoffer.charAt(0);
					}
					else
					{
						System.out.println("Illegal move, try again");
						System.out.println();
						continue;
					}
				}
				else
				{
					System.out.println("Illegal move, try again");
					System.out.println();
					continue;
					
				}
			}
			
			
			
			if(board[pieceFile][pieceRank] instanceof Pawn   && ((currentplayerside.equals("White") && pieceRank==6 && moveRank==7) || ((currentplayerside.equals("Black") && pieceRank==1 && moveRank==0)))  && promotion=='\0')
			{
				promotion='Q';
			}
			
			
			
			
			
			
			
			
			
			
			

			ChessPiece pieceToMove = board[pieceFile][pieceRank];
			
			
			
			
			
			
			
			
			

			if (pieceToMove != null && pieceToMove.getColor().equals(currentplayerside.toLowerCase())) {
				if (!pieceToMove.valid(moveFile, moveRank, board)) {
					System.out.println("Illegal move, try again");
					drawoffered=false;
				} else {
					
					//store old value
					
					ChessPiece destinationval;
					
					boolean ismoveddest=false;
					boolean ismovedorigin=false;
					
					if(board[moveFile][moveRank]==null)
					{
						destinationval=null;
					}
					
					else if(board[moveFile][moveRank] instanceof Pawn)
					{
						destinationval=new Pawn(moveFile,moveRank, board[moveFile][moveRank].getColor());
					}
					else if(board[moveFile][moveRank] instanceof Knight)
					{
						destinationval=new Knight(moveFile,moveRank, board[moveFile][moveRank].getColor());
					}
					else if(board[moveFile][moveRank] instanceof Queen)
					{
						destinationval=new Queen(moveFile,moveRank, board[moveFile][moveRank].getColor());
					}
					else if(board[moveFile][moveRank] instanceof Rook)
					{
						destinationval=new Rook(moveFile,moveRank, board[moveFile][moveRank].getColor());
						
						ismoveddest= ((Rook)(board[moveFile][moveRank])).ismoved;
						
					}
					else if(board[moveFile][moveRank] instanceof Bishop)
					{
						destinationval=new Bishop(moveFile,moveRank, board[moveFile][moveRank].getColor());
					}
					else
					{
						destinationval=new King(moveFile,moveRank, board[moveFile][moveRank].getColor());
						
						ismoveddest= ((King)(board[moveFile][moveRank])).ismoved;
						
					}
					
					
					
					//origin store
					
					if(board[pieceFile][pieceRank]!=null && (board[pieceFile][pieceRank] instanceof King || board[pieceFile][pieceRank] instanceof Rook))
					{
						
						
						
						if(board[pieceFile][pieceRank] instanceof King)
						{
							ismovedorigin=((King)(board[pieceFile][pieceRank])).ismoved;
						}
						else if(board[pieceFile][pieceRank] instanceof Rook)
						{
							ismovedorigin=((Rook)(board[pieceFile][pieceRank])).ismoved;
						}
					}
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					//update king's values
					
					if(board[pieceFile][pieceRank] instanceof King)
					{
						if(currentplayerside.equals("White"))
						{
							wkingfile=moveFile;
							wkingrank=moveRank;
						}
						else
						{
							bkingfile=moveFile;
							bkingrank=moveRank;
						}
					}
					
					
					
					//if valid castle
					
					
					
					
					boolean validcas=false;
					boolean validprom=false;
					boolean validenpas=false;
					
					
					if(board[pieceFile][pieceRank] instanceof King)
					{
						King k=(King) board[pieceFile][pieceRank];
						
						if(k.castlegood(board, pieceFile, pieceRank, moveFile, moveRank, side))
						{
							validcas=true;
						}
					}
					
					if(board[pieceFile][pieceRank] instanceof Pawn)
					{
                        Pawn p= (Pawn) board[pieceFile][pieceRank];
						
						if(p.ispromote(board, pieceFile, pieceRank, moveFile, moveRank, side))
						{
							validprom=true;
						}
					}
					
					
					//validenpas
					
					if(side.equals("white"))
					{
					   if(board[pieceFile][pieceRank] instanceof Pawn)
					   {
						if((moveFile == pieceFile + 1 || moveFile == pieceFile - 1) && moveRank == pieceRank + 1 && board[moveFile][moveRank] == null)
						{
							if(enpasssq!=null && enpasssq.getRank()==pieceRank && (enpasssq.getFile()==pieceFile+1 || enpasssq.getFile()==pieceFile-1))
							{
								if(board[enpasssq.getFile()][enpasssq.getRank()]!=null && board[enpasssq.getFile()][enpasssq.getRank()] instanceof Pawn && board[enpasssq.getFile()][enpasssq.getRank()].getColor().equals("black"))
								{
									validenpas=true;
								}
							}
						}
					   }
					}
					else
					{
						if(board[pieceFile][pieceRank] instanceof Pawn)
						{
						if((moveFile == pieceFile + 1 || moveFile == pieceFile - 1) && moveRank == pieceRank - 1 && board[moveFile][moveRank] == null)
						{
							if(enpasssq!=null && enpasssq.getRank()==pieceRank && (enpasssq.getFile()==pieceFile+1 || enpasssq.getFile()==pieceFile-1))
							{
								if(board[enpasssq.getFile()][enpasssq.getRank()]!=null && board[enpasssq.getFile()][enpasssq.getRank()] instanceof Pawn && board[enpasssq.getFile()][enpasssq.getRank()].getColor().equals("white"))
								{
									validenpas=true;
								}
							}
						}
						
						 }
					}
					
					
					
					
					
					
					if(validenpas)
					{
						ChessPiece.move(pieceFile, pieceRank, moveFile, moveRank, board);
						ChessPiece enepawn;
					    
						String opp="white";
						
						if(side.equals(opp))
						{
							opp="black";
						}
					    
					    enepawn=new Pawn(enpasssq.getFile(), enpasssq.getRank(), opp);
					    
					    board[enpasssq.getFile()][enpasssq.getRank()]=null;
					    
					    int kfile=wkingfile, krank=wkingrank;
					    
					    if(side.equals("black"))
					    {
					    	kfile=bkingfile;
					    	krank=bkingrank;
					    }
					    
					    
					    
					    
					    
					    boolean ans=King.incheck(board,opp,kfile, krank, kfile,krank);
						
						//restore if doesnt fix problem
						if(ans==true)
						{
							System.out.println("Illegal move, try again");
							board[pieceFile][pieceRank]=board[moveFile][moveRank];
							board[pieceFile][pieceRank].setFile(pieceFile);
							board[pieceFile][pieceRank].setRank(pieceRank);
							board[moveFile][moveRank]=null;
							board[enpasssq.getFile()][enpasssq.getRank()]=enepawn;
							continue;
						}
						
						
					}
					
					
					
					
					
					
					
					
					if(validprom)
					{
						
						ChessPiece.move(pieceFile, pieceRank, moveFile, moveRank, board);
						Pawn.promote(board, moveFile, moveRank, promotion, side);
						boolean checkgood=false;
						if(side.equals("white"))
						{
							checkgood=King.incheck(board, "black", wkingfile, wkingrank, wkingfile, wkingrank);
						}
						else
						{
							checkgood=King.incheck(board, "white", bkingfile, bkingrank, bkingfile, bkingrank);
						}
						
						
						if(checkgood==true)
						{
							System.out.println("Illegal move, try again");
							Pawn.unpromote(board, pieceFile, pieceRank, side);
							board[moveFile][moveRank]=destinationval;
							continue;
						}
						
					}
					
					
					
					
					
					
					
					
					if(validcas)
					{
						//King move
						ChessPiece.move(pieceFile, pieceRank, moveFile, moveRank, board);
						
						//Rook move
						
						if(moveFile==6 && moveRank==0)
						{
							ChessPiece.move(7, 0, 5, 0, board);
						}
						else if(moveFile==2 && moveRank==0)
						{
							ChessPiece.move(0, 0 , 3, 0, board);
						}
						else if(moveFile==6 && moveRank==7)
						{
							ChessPiece.move(7, 7, 5, 7, board);
						}
						else if(moveFile==2 && moveRank==7)
						{
							ChessPiece.move(0, 7, 3, 7, board);
						}
					}
					else
					{
						if(validprom==false && validenpas==false)
						{
						ChessPiece.move(pieceFile, pieceRank, moveFile, moveRank, board);
						}
					}
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
			if(validenpas==false && validprom==false && validcas==false)
			{
					
					
					//check if the player's king is in check now (not allowed)
					
					if(currentplayerside.equals("White"))
					{
						boolean currentcheck=King.incheck(board, "black", wkingfile, wkingrank, wkingfile,wkingrank);
						
						if(currentcheck)
						{
							//undo
							System.out.println("Illegal move, try again");
							drawoffered=false;
							
							if(board[moveFile][moveRank] instanceof King)
							{
								wkingfile=pieceFile;
								wkingrank=pieceRank;
							}
							
							
							
							board[pieceFile][pieceRank]=board[moveFile][moveRank];
							board[pieceFile][pieceRank].setFile(pieceFile);
							board[pieceFile][pieceRank].setRank(pieceRank);
							board[moveFile][moveRank]=destinationval;
							
							if(board[pieceFile][pieceRank] instanceof Rook)
							{
								Rook r=(Rook)board[pieceFile][pieceRank];
								r.ismoved=ismovedorigin;
							}
							if(board[pieceFile][pieceRank] instanceof King)
							{
								King k=(King)board[pieceFile][pieceRank];
								k.ismoved=ismovedorigin;
							}
							
							if(board[moveFile][moveRank] !=null && board[moveFile][moveRank] instanceof Rook)
							{
								Rook r=(Rook)board[moveFile][moveRank];
								r.ismoved=ismoveddest;
							}
							if(board[moveFile][moveRank] !=null && board[moveFile][moveRank] instanceof King)
							{
								King k=(King)board[moveFile][moveRank];
								k.ismoved=ismoveddest;
							}
							
							
							continue;
							
						}
					}
					else
					{
                        boolean currentcheck=King.incheck(board, "white", bkingfile, bkingrank, bkingfile,bkingrank);
						
						if(currentcheck)
						{
							//undo
							System.out.println("Illegal move, try again");
							drawoffered=false;
							
							if(board[moveFile][moveRank] instanceof King)
							{
								bkingfile=pieceFile;
								bkingrank=pieceRank;
							}
							
							board[pieceFile][pieceRank]=board[moveFile][moveRank];
							board[pieceFile][pieceRank].setFile(pieceFile);
							board[pieceFile][pieceRank].setRank(pieceRank);
							board[moveFile][moveRank]=destinationval;
							
							if(board[pieceFile][pieceRank] instanceof Rook)
							{
								Rook r=(Rook)board[pieceFile][pieceRank];
								r.ismoved=ismovedorigin;
							}
							if(board[pieceFile][pieceRank] instanceof King)
							{
								King k=(King)board[pieceFile][pieceRank];
								k.ismoved=ismovedorigin;
							}
							
							if(board[moveFile][moveRank] !=null && board[moveFile][moveRank] instanceof Rook)
							{
								Rook r=(Rook)board[moveFile][moveRank];
								r.ismoved=ismoveddest;
							}
							if(board[moveFile][moveRank] !=null && board[moveFile][moveRank] instanceof King)
							{
								King k=(King)board[moveFile][moveRank];
								k.ismoved=ismoveddest;
							}
							
							continue;
							
						}
					}
					
			}		
					
					//check if other king is in check now
					
					boolean othercheck=false;
					
					if(currentplayerside.equals("White"))
					{
						boolean currentcheck=King.incheck(board, "white", bkingfile, bkingrank, bkingfile,bkingrank);
						
						if(currentcheck)
						{
							othercheck=true;
							
							
							
						}
					}
					else
					{
                        boolean currentcheck=King.incheck(board, "black", wkingfile, wkingrank, wkingfile,wkingrank);
						
						if(currentcheck)
						{
							
							othercheck=true;
							
						}
					}
					
					
					//check for mate (for other player)
					
					boolean check=false;
					if(othercheck==true)
					{
						
						String oppos="white";
						
						if(side.equals("white"))
						{
							oppos="black";
						}
						
						
						
						
						boolean mate=ismate(oppos);
						
						if(mate)
						{
							winner=currentplayerside;
							game=false;
						}
						else
						{
							check=true;
							
						}
						
						
						
					}
					
					
					
					
					//update enpas
					
					if(board[moveFile][moveRank]!=null && board[moveFile][moveRank] instanceof Pawn)
					{
						if(moveFile == pieceFile  && moveRank == pieceRank + 2 && board[moveFile][moveRank].getColor().equals("white"))
						{
							enpasssq=new Point(moveFile, moveRank);
							Pawn.setensq(moveFile, moveRank);
						}
						else if(moveFile == pieceFile  && moveRank == pieceRank - 2 && board[moveFile][moveRank].getColor().equals("black"))
						{
							enpasssq=new Point(moveFile, moveRank);
							Pawn.setensq(moveFile, moveRank);
						}
						else
						{
							enpasssq=null;
							Pawn.cancelensq();
						}
					}
					else
					{
						enpasssq=null;
						Pawn.cancelensq();
					}
					
					
					
					
					
					
					
					
					
					drawboard();
					
					if(check)
					{
						System.out.println("Check");
					}
					currentplayerside = currentplayerside.equals("White") ? "Black" : "White";
				}
			} else {
				System.out.println("Illegal move, try again");
				drawoffered=false;
			}

			System.out.println();

		}
		
		if(winner!=null)
		{
			if(isresign==false)
			{
				System.out.println("Checkmate");
			}
			System.out.print(winner + " wins.");
		}
		
		if(drawoffered==true)
		{
			System.out.print("Draw");
		}
		
		
	}
	
	
	
	
	/**
	 * This method returns whether or not a particular side has been checkmated by the other. In other words, it checks if the current player has won the game or not
	 * @param currentplayer: This is the player whose turn it will be during the next iteration of the while method in the play() method
	 * @return a boolean that indicates whether the "currentplayer" side has lost the game by getting checkmated
	 */
	
	
	
	
	public boolean ismate(String currentplayer)
	{
		//kings own mvmt
		
		int currentfile=0;
		int currentrank=0;
		String opp=null;
		
		if(currentplayer.equals("white"))
		{
			currentfile=wkingfile;
			currentrank=wkingrank;
			opp="black";
		}
		else
		{
			currentfile=bkingfile;
			currentrank=bkingrank;
			opp="white";
		}
		
		
		
		
		ArrayList<Point> al= new ArrayList<Point>();
		al.add(new Point(currentfile+1,currentrank));
		al.add(new Point(currentfile-1,currentrank));
		al.add(new Point(currentfile-1,currentrank-1));
		al.add(new Point(currentfile-1,currentrank+1));
		al.add(new Point(currentfile,currentrank+1));
		al.add(new Point(currentfile,currentrank-1));
		al.add(new Point(currentfile+1,currentrank+1));
		al.add(new Point(currentfile+1,currentrank-1));
		
		
		for(int i=0;i<al.size();i++)
		{
			int file=al.get(i).getFile();
			int rank=al.get(i).getRank();
			if(board[currentfile][currentrank].valid(file, rank, board))
			{
				return false;
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		//try all moves on this side
		
		for(int file=0;file<8;file++)
		{
			for(int rank=0;rank<8;rank++)
			{
				
				if(board[file][rank]!=null && board[file][rank].getColor().equals(currentplayer))
				{
					for(int testfile=0;testfile<8;testfile++)
					{
						for(int testrank=0; testrank<8;testrank++)
						{
							
							if(board[file][rank] instanceof Pawn)
							{
								//check if valid
								if(!board[file][rank].valid(testfile, testrank, board))
								{
									continue;
								}
								
								
								//move
								
								boolean enpas=false;
								
								if(enpasssq!=null)
								{
									if(currentplayer.equals("white"))
									{
										
										if((testfile == file + 1 || testfile == file - 1) && testrank == rank + 1 && board[testfile][testrank] == null)
										{
											if(enpasssq!=null && enpasssq.getRank()==rank && (enpasssq.getFile()==file+1 || enpasssq.getFile()==file-1))
											{
												if(board[enpasssq.getFile()][enpasssq.getRank()]!=null && board[enpasssq.getFile()][enpasssq.getRank()] instanceof Pawn && board[enpasssq.getFile()][enpasssq.getRank()].getColor().equals("black"))
												{
													enpas=true;
												}
											}
										}
									}
									else
									{
										if((testfile == file + 1 || testfile== file - 1) && testrank == rank - 1 && board[testfile][testrank] == null)
										{
											if(enpasssq!=null && enpasssq.getRank()==rank && (enpasssq.getFile()==file+1 || enpasssq.getFile()==file-1))
											{
												if(board[enpasssq.getFile()][enpasssq.getRank()]!=null && board[enpasssq.getFile()][enpasssq.getRank()] instanceof Pawn && board[enpasssq.getFile()][enpasssq.getRank()].getColor().equals("white"))
												{
													enpas=true;
												}
											}
										}
									}
								}
								
								
								
								
								
								if(enpas)
								{
								    ChessPiece.move(file, rank, testfile, testrank, board);
								    ChessPiece enepawn;
								    
								 
								    
								    enepawn=new Pawn(enpasssq.getFile(), enpasssq.getRank(), opp);
								    
								    board[enpasssq.getFile()][enpasssq.getRank()]=null;
								    
								    
								    boolean ans=King.incheck(board,opp,currentfile, currentrank, currentfile,currentrank);
									
									//restore if doesnt fix problem
									if(ans==true)
									{
										board[file][rank]=board[testfile][testrank];
										board[file][rank].setFile(file);
										board[file][rank].setRank(rank);
										board[testfile][testrank]=null;
										board[enpasssq.getFile()][enpasssq.getRank()]=enepawn;
										
									}
									else
									{
										board[file][rank]=board[testfile][testrank];
										board[file][rank].setFile(file);
										board[file][rank].setRank(rank);
										board[testfile][testrank]=null;
										board[enpasssq.getFile()][enpasssq.getRank()]=enepawn;
										return false;
									}
									
								    
								    
								    
								    
								}
								
								else
								{
								
								
								
								ChessPiece destinationval2;
								boolean ismoveddest=false;
								boolean ismovedorigin=false;
								
								if(board[testfile][testrank]==null)
								{
									destinationval2=null;
								}
								
								else if(board[testfile][testrank] instanceof Pawn)
								{
									destinationval2=new Pawn(testfile,testrank, board[testfile][testrank].getColor());
								}
								else if(board[testfile][testrank] instanceof Knight)
								{
									destinationval2=new Knight(testfile,testrank, board[testfile][testrank].getColor());
								}
								else if(board[testfile][testrank] instanceof Queen)
								{
									destinationval2=new Queen(testfile,testrank, board[testfile][testrank].getColor());
								}
								else if(board[testfile][testrank] instanceof Rook)
								{
									destinationval2=new Rook(testfile,testrank, board[testfile][testrank].getColor());
									ismoveddest= ((Rook)(board[testfile][testrank])).ismoved;
								}
								else if(board[testfile][testrank] instanceof Bishop)
								{
									destinationval2=new Bishop(testfile,testrank, board[testfile][testrank].getColor());
								}
								else
								{
									destinationval2=new King(testfile,testrank, board[testfile][testrank].getColor());
									ismoveddest= ((King)(board[testfile][testrank])).ismoved;
								}
								
								ChessPiece.move(file, rank, testfile, testrank, board);
								
								
								//check if no check anymore
								
								
								boolean ans=King.incheck(board,opp,currentfile, currentrank, currentfile,currentrank);
								
								//restore if doesnt fix problem
								if(ans==true)
								{
									board[file][rank]=board[testfile][testrank];
									board[file][rank].setFile(file);
									board[file][rank].setRank(rank);
									board[testfile][testrank]=destinationval2;
									
									if(board[file][rank] instanceof Rook)
									{
										Rook r=(Rook)board[file][rank];
										r.ismoved=ismovedorigin;
									}
									if(board[file][rank] instanceof King)
									{
										King k=(King)board[file][rank];
										k.ismoved=ismovedorigin;
									}
									
									if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
									{
										Rook r=(Rook)board[testfile][testrank];
										r.ismoved=ismoveddest;
									}
									if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
									{
										King k=(King)board[testfile][testrank];
										k.ismoved=ismoveddest;
									}
								}
								else
								{
									board[file][rank]=board[testfile][testrank];
									board[file][rank].setFile(file);
									board[file][rank].setRank(rank);
									board[testfile][testrank]=destinationval2;
									
									if(board[file][rank] instanceof Rook)
									{
										Rook r=(Rook)board[file][rank];
										r.ismoved=ismovedorigin;
									}
									if(board[file][rank] instanceof King)
									{
										King k=(King)board[file][rank];
										k.ismoved=ismovedorigin;
									}
									
									if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
									{
										Rook r=(Rook)board[testfile][testrank];
										r.ismoved=ismoveddest;
									}
									if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
									{
										King k=(King)board[testfile][testrank];
										k.ismoved=ismoveddest;
									}
									return false;
								}
								
								
								
								}	
								
								
								
							}
							
							else
							{
								if(!(board[file][rank] instanceof King) && board[file][rank].valid(testfile, testrank, board))
								{
									//move
									
									ChessPiece destinationval2;
									boolean ismoveddest=false;
									boolean ismovedorigin=false;
									
									if(board[testfile][testrank]==null)
									{
										destinationval2=null;
									}
									
									else if(board[testfile][testrank] instanceof Pawn)
									{
										destinationval2=new Pawn(testfile,testrank, board[testfile][testrank].getColor());
									}
									else if(board[testfile][testrank] instanceof Knight)
									{
										destinationval2=new Knight(testfile,testrank, board[testfile][testrank].getColor());
									}
									else if(board[testfile][testrank] instanceof Queen)
									{
										destinationval2=new Queen(testfile,testrank, board[testfile][testrank].getColor());
									}
									else if(board[testfile][testrank] instanceof Rook)
									{
										destinationval2=new Rook(testfile,testrank, board[testfile][testrank].getColor());
										ismoveddest= ((Rook)(board[testfile][testrank])).ismoved;
									}
									else if(board[testfile][testrank] instanceof Bishop)
									{
										destinationval2=new Bishop(testfile,testrank, board[testfile][testrank].getColor());
									}
									else
									{
										destinationval2=new King(testfile,testrank, board[testfile][testrank].getColor());
										ismoveddest= ((King)(board[testfile][testrank])).ismoved;
									}
									
									ChessPiece.move(file, rank, testfile, testrank, board);
									
									//check if no check anymore
									
									boolean ans=King.incheck(board,opp,currentfile, currentrank, currentfile,currentrank);
									
									//restore if doesnt fix problem
									if(ans==true)
									{
										board[file][rank]=board[testfile][testrank];
										board[file][rank].setFile(file);
										board[file][rank].setRank(rank);
										board[testfile][testrank]=destinationval2;
										
										if(board[file][rank] instanceof Rook)
										{
											Rook r=(Rook)board[file][rank];
											r.ismoved=ismovedorigin;
										}
										if(board[file][rank] instanceof King)
										{
											King k=(King)board[file][rank];
											k.ismoved=ismovedorigin;
										}
										
										if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
										{
											Rook r=(Rook)board[testfile][testrank];
											r.ismoved=ismoveddest;
										}
										if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
										{
											King k=(King)board[testfile][testrank];
											k.ismoved=ismoveddest;
										}
									}
									else
									{
										board[file][rank]=board[testfile][testrank];
										board[file][rank].setFile(file);
										board[file][rank].setRank(rank);
										board[testfile][testrank]=destinationval2;
										
										if(board[file][rank] instanceof Rook)
										{
											Rook r=(Rook)board[file][rank];
											r.ismoved=ismovedorigin;
										}
										if(board[file][rank] instanceof King)
										{
											King k=(King)board[file][rank];
											k.ismoved=ismovedorigin;
										}
										
										if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof Rook)
										{
											Rook r=(Rook)board[testfile][testrank];
											r.ismoved=ismoveddest;
										}
										if(board[testfile][testrank] !=null && board[testfile][testrank] instanceof King)
										{
											King k=(King)board[testfile][testrank];
											k.ismoved=ismoveddest;
										}
										return false;
									}
									
									
									
									
								}
							}
							
							
							
						}
					}
				}
				
				
				
			}
		}
		
		
		
		
		return true;
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
