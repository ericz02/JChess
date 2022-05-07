package chess;

import java.util.InputMismatchException;

/**
 * @author Kevin Reid, Jerry Aviles, & Eric Zheng.
 * @date April 8 - May 3, 2022
 * Class to simulate a chessboard and play chess.
 */

import java.util.Scanner;
import javax.swing.JOptionPane;

public class PlayableChessBoard 
{
	private final static int ROWS = 8;
	private final static int COLUMNS = 8;
	private Scanner keyboard;

	/*Array of References to the interface type. Null references represent empty
	 * squares on the board.
	 */
	private static MoveableGamePiece[][] pieces = new MoveableGamePiece[ROWS][COLUMNS];

	private Color player;
	private Position startOfMove;
	private Position endOfMove;
	
	//Used for determining check and checkmate:
	private Position whiteKingsSquare;
	private Position blackKingsSquare;

	static
	{
		setUpBoard();
	}
	
	//Tested successfully.
	public PlayableChessBoard()
	{
		init();
	}

	public Color getPlayer() 
	{
		return player;
	}

	/**
	 * The master method to play a game of chess.
	 */
	
	public void play() 
	{
		//Jerry/Eric, Put code to launch GUI here, on this line.
		GUI.display();
		testPrintPieces();
		
		do
		{
			//Switch player for new move.
			if(player == Color.WHITE)
			{
				player = Color.BLACK;
			}
			else if (player == Color.BLACK)
			{
				player = Color.WHITE;
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Error. Invalid color of player in PlayableChessBoard.play().");
				System.exit(4);
			}
			
			//If you want to move this into the GUI files, we can talk about that.
			JOptionPane.showMessageDialog(null, player + "'s turn.");
			
			try 
			{
			getNextMove();
			}
			catch(InputMismatchException e)
			{
				printMismatchError();
			}
			
			//Loop to check for an invalid Move and prompt user to try again.
			while( (!isValidMove(startOfMove, endOfMove, pieces) ) 
//			|| ( (isInCheck(king's square) )
				)
			{
				JOptionPane.showMessageDialog(null, "Illegal Move. Please try again.");
			//Be sure to send copy of result back to GUI.
				
				try 
				{
				getNextMove();
				}
				catch(InputMismatchException e)
				{
					printMismatchError();
				}
				
			}
			
			executeMove(startOfMove, endOfMove);
			
			GUI.display();
		}while(!isCheckmate());
		
		JOptionPane.showMessageDialog(null, "CHECKMATE! " + player + "WINS!");
		
		keyboard.close();
		System.exit(0);

	}

	/**
	 * Method to terminate program with error message if user confuses row numbers with column letters 
	 * while entering input.
	 * Prints "Don't get your letters and numbers mixed up next time!\nGoodbye."
	 */
	private void printMismatchError() 
	{
		JOptionPane.showMessageDialog(null, "Don't get your letters and numbers mixed up next time!\nGoodbye.");
		System.exit(6);
		
	}

	/**
	 * Method to return a deep copy of pieces.
	 * WARNING! DO NOT call the method "setHasNeverMoved" on any pieces in the
	 * array returned by this method! The ARRAY is a deep copy, but it might contain
	 * COPIES OF REFERENCES to the same original piece objects. Calling "setHasNeverMoved"
	 * on the array's contents might break the code. This method should only be called outside
	 * of this class as a last resort!
	 * @return A DEEP COPY of the array "pieces".
	 */
	public static MoveableGamePiece[][] getDeepCopyOfPieces()
	{
		MoveableGamePiece[][] copies = new MoveableGamePiece[ROWS][COLUMNS];
		
		for (int row = 0; row < ROWS; row ++)
		{
			for (int column = 0; column < COLUMNS; column ++)
			{
				copies[row][column] = pieces[row][column];
			}
		}
		
		return copies;
	}
	
	/**
	 * Method to print the pieces to the console. Useful for testing other methods.
	 * The elements of the array "pieces" gets interpreted as Unicode characters
	 * and printed to the console in a chessboard pattern.
	 */
	//Tested
	public void testPrintPieces() 
	{
		System.out.println();
		ChessBoardPrinter.displayCustomBoard(pieces);
	}

	@Override
	public String toString() {
		return "PlayableChessBoard [player=" + player + ", startOfMove=" + startOfMove + ", endOfMove=" + endOfMove
				+ ", whiteKingsSquare=" + whiteKingsSquare + ", blackKingsSquare=" + blackKingsSquare + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blackKingsSquare == null) ? 0 : blackKingsSquare.hashCode());
		result = prime * result + ((endOfMove == null) ? 0 : endOfMove.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((startOfMove == null) ? 0 : startOfMove.hashCode());
		result = prime * result + ((whiteKingsSquare == null) ? 0 : whiteKingsSquare.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayableChessBoard other = (PlayableChessBoard) obj;
		if (blackKingsSquare == null) {
			if (other.blackKingsSquare != null)
				return false;
		} else if (!blackKingsSquare.equals(other.blackKingsSquare))
			return false;
		if (endOfMove == null) {
			if (other.endOfMove != null)
				return false;
		} else if (!endOfMove.equals(other.endOfMove))
			return false;
		if (player != other.player)
			return false;
		if (startOfMove == null) {
			if (other.startOfMove != null)
				return false;
		} else if (!startOfMove.equals(other.startOfMove))
			return false;
		if (whiteKingsSquare == null) {
			if (other.whiteKingsSquare != null)
				return false;
		} else if (!whiteKingsSquare.equals(other.whiteKingsSquare))
			return false;
		return true;
	}

	/**
	 * Method to determine whether current player's king would be "in check" after a given move, either
	 * because the move puts him into check, or because the opponent has already put him into check and this move
	 * will not get him out.
	 * @param startOfMove The square where the proposed move begins.
	 * @param destination The destination of the piece being moved.
	 * @return True if player's king would be in check if positioned on boardSquare. False otherwise.
	 */
	//BROKEN METHOD! DO NOT USE!
	private boolean wouldBeInCheck(Position startOfMove, Position destination)
	{
		MoveableGamePiece[][] dummyPieces = getDeepCopyOfPieces();
		
		/*
		 * Execute the move on a DISPOSABLE DEEP COPY of the array. This enables us to make the move for
		 * testing without changing the "real" array.
		 */
		dummyPieces[destination.getRow()][destination.getColumn()] = 
				dummyPieces[startOfMove.getRow()][startOfMove.getColumn()];
		dummyPieces[startOfMove.getRow()][startOfMove.getColumn()] = null;
		
		Position kingsSquare;
		
		if(player == Color.WHITE)
		{
			kingsSquare = whiteKingsSquare;
		}
		else if(player == Color.BLACK)
		{
			kingsSquare = blackKingsSquare;
		}
		else
		{
			kingsSquare = null;
			JOptionPane.showMessageDialog(null, "Error. Invalid value of player in PlayableChessBoard.isInCheck"
					+ "(Positon, Position)");
			System.exit(4);
		}
		
		boolean inCheck = false;
		
		for (int row = 0; row < ROWS; row++)
		{
			for(int col = 0; col < COLUMNS; col++)
			{
				if( (dummyPieces[row][col] != null) && (dummyPieces[row][col].getColor() == player) )
				{
					//if any enemy piece can move to the king's square:
					Position attacker = new Position (row, col);
					if(isValidMove(attacker, kingsSquare, dummyPieces))
					{
						inCheck = true;
					}
				}
			}
		}
		
		return inCheck;
	}

	/**
	 * Method to check whether the piece the player is trying to move is the correct color.
	 * @param boardSquare The position in the array of the piece to be moved.
	 * @param pieces The array of pieces being tested.
	 * @return True if the piece is the player's color, false otherwise.
	 */
	//Tested successfully.
	private boolean pieceIsCorrectColor(Position boardSquare, MoveableGamePiece[][] pieces)
	{
		int row = boardSquare.getRow();
		int col = boardSquare.getColumn();
		
//		if (pieces[row][col] == null)
//		{
//			System.err.println("How can the piece be null? We ALREADY TESTED THAT in the calling method!");
//			System.exit(5);
//		}
		
		return ( pieces[row][col].getColor() == player);
	}

	/**
	 * Method to determine whether a given move is illegal because another piece is blocking it.
	 * Does NOT test whether Castling is blocked.
	 * @param start The square (array indices) where the move begins.
	 * @param end The destination square.
	 * @return True if another piece is blocking the move. False otherwise.
	 */
	private boolean moveIsBlocked(Position startingSquare, Position destination)
	{
		//FRIENDLY piece already on destination square.
		if( (pieces[destination.getRow()][destination.getColumn()] != null) &&
				(pieces[destination.getRow()][destination.getColumn()].getColor()
				== player) )
		{
			return true;
		} 

		/*
		 * Knights can jump over pieces on intermediate squares. Kings have no intermediate squares
		 * because they only move one square per turn.
		 */
		MoveableGamePiece pieceTryingToMove = pieces[startingSquare.getRow()]
				[startingSquare.getColumn()];  

		if ( (pieceTryingToMove.getClass().getSimpleName().equals("Knight") ) ||
				(pieceTryingToMove.getClass().getSimpleName().equals("King") ) )
		{
			return false;
		}

		//A piece of EITHER color on an intermediate square can block the move.
		if (intermediateSquareIsBlocked(startingSquare, destination))
		{
			return true;
		}

		return false;
	}

	/**
	 * Method to determine whether a proposed move for a pawn, bishop, rook, or queen moving more than 
	 * one square is blocked by a piece of either color on an intermediate square. Does NOT test
	 * whether destination square is blocked.
	 * Precondition: The move is horizontal (columns change but rows do not), vertical (rows change but columns
	 * do not), or properly diagonal (rows and columns change by the same amount).
	 * @param startingSquare The square (array indices) where the move begins.
	 * @param destination The destination square.
	 * 
	 * @return True if the move is blocked in that manner; false otherwise.
	 */
	//Tested successfully.
	private boolean intermediateSquareIsBlocked(Position startingSquare, Position destination) 
	{	
		if(startingSquare.equals(destination))
		{
			JOptionPane.showMessageDialog(null, "Invalid arguments passed to intermediateSquareIsBlocked.");
			System.exit(5);
		}
		
		//Hypothetically, Version 2.0 would probably break this up into multiple methods.
		
		boolean movingLeft = false;
		boolean movingRight = false;
		boolean movingUp = false;
		boolean movingDown = false;
		boolean notMovingHorizontally = false;
		boolean notMovingVertically = false;
		
		//Step one: figure out which way the piece is going.	
		if( (startingSquare.getColumn() - destination.getColumn() ) > 0)
		{
			movingLeft = true;
		}
		else if ( (startingSquare.getColumn() - destination.getColumn() ) < 0 )
		{
			movingRight = true;
		}
		else
		{
			notMovingHorizontally = true;
		}

		if ( (startingSquare.getRow() - destination.getRow() ) < 0)
		{
			movingUp = true;
		}
		else if ( (startingSquare.getRow() - destination.getRow() ) > 0)
		{
			movingDown = true;
		}
		else
		{
			notMovingVertically = true;
		}
		
		/*
		 *  Step two: use math on coordinates to count intermediate squares.
		 *  Subtract 1 at the end to avoid counting the destination square.
		 */	
		int  numberOfIntermediateSquares;
		Position [] intermediateSquares;
		
		try
		{
			/*
			 * If the row changes, either the column does not change or the piece is moving diagonally.
			 * If it is moving diagonally, then the row and column are changing by the same amount, so
			 * we only need to check one. However, if only the column is changing, then the math on the
			 * next line will result in -1 (an invalid array size), which signals that we should use the
			 * columns instead.
			 */
			numberOfIntermediateSquares = Math.abs( (startingSquare.getRow() - destination.getRow() ) ) - 1;
			intermediateSquares = new Position [numberOfIntermediateSquares];
		}
		catch(NegativeArraySizeException e)
		{
			numberOfIntermediateSquares = Math.abs( (startingSquare.getColumn() - destination.getColumn() ) ) - 1;
			intermediateSquares = new Position [numberOfIntermediateSquares];
		}
		
		//Step three: identify the immediate squares.
		
		int row = startingSquare.getRow();
		int col = startingSquare.getColumn();
		
		if(movingRight && notMovingVertically)
		{
			for (int i = 0; i < numberOfIntermediateSquares; i++)
			{
				intermediateSquares[i] = new Position(row, ++col);
			}
		}
		else if(movingLeft && notMovingVertically)
		{
			for (int i = 0; i < numberOfIntermediateSquares; i++)
			{
				intermediateSquares[i] = new Position(row, --col);
			}
		}
		else if(movingLeft && movingUp)
		{
			for (int i = 0; i < numberOfIntermediateSquares; i++)
			{
				intermediateSquares[i] = new Position(++row, --col);
			}
		}
		else if(movingLeft && movingDown)
		{
			for (int i = 0; i < numberOfIntermediateSquares; i++)
			{
				intermediateSquares[i] = new Position(--row, --col);
			}
		}
		else if(movingRight && movingUp)
		{
			for (int i = 0; i < numberOfIntermediateSquares; i++)
			{
				intermediateSquares[i] = new Position(++row, ++col);
			}
		}
		else if(movingRight && movingDown)
		{
			for (int i = 0; i < numberOfIntermediateSquares; i++)
			{
				intermediateSquares[i] = new Position(--row, ++col);
			}
		}
		else if(movingUp && notMovingHorizontally)
		{
			for (int i = 0; i < numberOfIntermediateSquares; i++)
			{
				intermediateSquares[i] = new Position(++row, col);
			}
		}
		else if(movingDown && notMovingHorizontally)
		{
			for (int i = 0; i < numberOfIntermediateSquares; i++)
			{
				intermediateSquares[i] = new Position(--row, col);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Fatal error in PlayableChessBoard.intermediateSquareIsBlocked.");
		}
		
		//Step four: if any of those squares are not null, move is blocked.
		for(Position pos : intermediateSquares)
		{
			if (pieces[pos.getRow()][pos.getColumn()] != null)
			{
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Method to execute a chess move. Does NOT check whether the move is legal.
	 * Postcondition: The destination square in the array "pieces" now holds a reference to the piece
	 * that was moved. Any piece previously referenced by the destination square has therefore been dereferenced, 
	 * thus the captured piece is no longer "on the board." The starting square now references null (an empty square).
	 * If a king has been moved, then whiteKingsSquare or blackKingsSquare has been updated to the destination square.
	 * @param startingSquare The square (array indices) where the move begins.
	 * @param destination The position of the destination square.
	 * 
	 */
	//Tested.
	private void executeMove(Position startingSquare, Position destination)
	{
		MoveableGamePiece pieceBeingMoved = pieces[startingSquare.getRow()]
				[startingSquare.getColumn()];

		//Make destination hold a reference to the piece being moved.
		pieces[destination.getRow()][destination.getColumn()] = pieceBeingMoved;
		
		pieceBeingMoved.setHasNeverMoved(false);

		//Make starting square of move empty.
		pieces[startingSquare.getRow()][startingSquare.getColumn()] = null;

		testPrintPieces();
		
		if (pieceBeingMoved.getClass().getSimpleName().equals("King"))
		{
			if(pieceBeingMoved.getColor() == Color.WHITE)
			{
				whiteKingsSquare = destination;
//				System.out.println("King's Square: " + whiteKingsSquare);
			}
			else if (pieceBeingMoved.getColor() == Color.BLACK)
			{
				blackKingsSquare = destination;
//				System.out.println("King's Square: " + blackKingsSquare);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Error. Invalid color of pieceBeingMoved in PlayableChessBoard"
						+ ".executeMove(Position, Position).");
				System.exit(5);
			}
		}
//		else	//Test code.
//		{
//			System.out.println("Outer if statement evaluated as false.");
//		}
		

	}

	/**
	 * 
	 * @return True if the player has checkmated his/her opponent. False otherwise.
	 */
	//STUB
	private boolean isCheckmate()
	{
		Position enemyKingsSquare;
		Color opponent;
		
		if(player == Color.WHITE)
		{
			enemyKingsSquare = blackKingsSquare;
			opponent = Color.BLACK;
		}
		else if (player == Color.BLACK)
		{
			enemyKingsSquare = whiteKingsSquare;
			opponent = Color.WHITE;
		}
		else
		{
			enemyKingsSquare = null; //Null assignments make complier happy.
			opponent = null;
			JOptionPane.showMessageDialog(null, "Error. Invalid color of player.");
			System.exit(4);
		}
		
		return false;
	}

	//Needs finishing.
	private boolean cantGetOutOfCheck(Position enemyKingsSquare, Color opponent) 
	{
		MoveableGamePiece[][] dummyPieces = getDeepCopyOfPieces();
		
		for (int row = 0; row < ROWS; row ++)
		{
			for(int column = 0; column < COLUMNS; column ++)
			{
				if( (dummyPieces[row][column] != null) && (dummyPieces[row][column].getColor()
						== opponent) )
						{
					
						}
			}
		}
		//STUB
		return false;
		
	}

	/**
	 * Method to get the input from the user/GUI.
	 * Postcondition: startOfMove and endOfMove hold the coordinates of player's intended move.
	 */
	public void getNextMove()
	{
		//We can replace the keyboard input with mouse events later, but I want you to see how this works.
		
		System.out.print("Enter the row number of your piece's starting square: ");
		startOfMove.setRow(keyboard.nextInt() - 1);
		
		System.out.print("Enter the starting column letter: ");
		keyboard.nextLine(); //Remove '\n' from buffer.
		String input = keyboard.nextLine();
		char temp = input.toUpperCase().charAt(0);
		startOfMove.setColumn( (int) temp - ChessBoardPrinter.ASCII_DISPLACEMENT_FOR_COLUMN_LETTERS - 1);
		
		System.out.print("Enter the row number of your piece's destination square: ");
		endOfMove.setRow(keyboard.nextInt() - 1);
		
		System.out.print("Enter the ending column letter: ");
		keyboard.nextLine(); //Remove '\n' from buffer.
		input = keyboard.nextLine();
		temp = input.toUpperCase().charAt(0);
		endOfMove.setColumn( (int) temp - ChessBoardPrinter.ASCII_DISPLACEMENT_FOR_COLUMN_LETTERS - 1);
	}

	/**
	 * Method to determine whether the move the user has entered is legal, assuming "check" is
	 * not preventing it. This method cannot determine whether "check" is a factor because the
	 * method isInCheck calls it!
	 * @param startingSquare   The square (array indices) where the move begins.
	 * @param destination  The destination square.
	 * @param pieces The array of pieces being tested.
	 * @return True if the move is legal. False otherwise.
	 */
	//Calls many of the above helper methods.
	//Tested.
	private boolean isValidMove(Position startingSquare, Position destination, MoveableGamePiece[][] pieces)
	{
		
		boolean moveIsACapture = false;
		
		if ( (startingSquare.getRow() < 0) || (startingSquare.getRow() > 7) || (startingSquare.getColumn()
				< 0) || (startingSquare.getColumn() > 7) || (destination.getRow() < 0) ||
				(destination.getRow() > 7) || (destination.getColumn() < 0) || (destination.getColumn() > 7) )
		{
			//Array indices out of bounds.
			return false;
		}
		
		MoveableGamePiece pieceBeingMoved = pieces[startingSquare.getRow()][startingSquare.getColumn()];
		
//		System.out.println(pieceBeingMoved);
//		System.out.println(startingSquare);
		
		/*
		 * If starting square has no piece on it, has opponent's piece on it,
		 * or matches destination square, or if another piece blocks the move, move is not valid.
		 */
		if ( pieceBeingMoved == null )
		{
			return false;
		}	
		
		else if(startingSquare.equals(destination) || (!pieceIsCorrectColor(startingSquare, pieces) ) || 
				moveIsBlocked(startingSquare, destination) )
		{
			return false;
		}
		else if ( (pieces[destination.getRow()][destination.getColumn()] != null) &&
				(pieces[destination.getRow()][destination.getColumn()].getColor() != player) )
		{
			moveIsACapture = true;	
		}
		//Ask the piece if it can make this move.
		return pieceBeingMoved.moveIsValid(startingSquare, destination, moveIsACapture);
	}

	//Helper Methods for constructors (tested):

	private void init()
	{	
		//White always moves first in chess, but the color gets swapped at the start of each turn.
		player = Color.BLACK; 
		whiteKingsSquare = new Position(0, 4);
		blackKingsSquare = new Position(7, 4);
		startOfMove = new Position(0,0);
		endOfMove = new Position(0,0);
		keyboard = new Scanner(System.in);
	}

	private static void setUpBoard() 
	{
		for (int row = 0; row < ROWS; row++ )
		{
			switch (row)
			{
			case 0:
				setUpSeniorPieces(Color.WHITE, row);
				break;
			case 1:
				for (int col = 0; col <COLUMNS; col++)
				{
					pieces[row][col] = new Pawn(Color.WHITE);
				}
				break;

				//Do nothing for these cases. Rows 2-5 (3-6 on an actual chessboard) should reference null.
			case 2:
			case 3:
			case 4:
			case 5:
				break;
			case 6:
				for (int col = 0; col <COLUMNS; col++)
				{
					pieces[row][col] = new Pawn(Color.BLACK);
				}
				break;
			case 7:
				setUpSeniorPieces(Color.BLACK, row);
				break;
			default:
				JOptionPane.showMessageDialog(null, "Invalid value of row in PlayableChessBoard.setUpBoard().");
				System.exit(1);

			}

		}

	}

	private static void setUpSeniorPieces(Color color, int row) 
	{
		for (int col = 0; col < COLUMNS; col++)
		{
			switch(col)
			{
			case 0: //columns A and H
			case 7:
				pieces[row][col] = new Rook(color);
				break;
			case 1: //Columns B and G
			case 6:
				pieces[row][col] = new Knight(color);
				break;
			case 2:	//Columns C and F
			case 5:
				pieces[row][col] = new Bishop(color);
				break;
			case 3:	//Column D
				pieces[row][col] = new Queen(color);
				break;
			case 4:	//Column E
				pieces[row][col] = new King(color);
				break;
			default:
				JOptionPane.showMessageDialog(null, "Invalid value of col in PlayableChessBoard.setUpSeniorPieces().");
				System.exit(2);

			}
		}
	}

	/**
	 * This main is for testing individual methods. Our program's real launch point is 
	 * ProtoChessLauncher.main();
	 * @param args None.
	 */
	public static void main(String[] args) 
	{
		PlayableChessBoard board = new PlayableChessBoard();

		//		System.out.println("Testing constructors:");
		//		for (int i = 0; i < 8; i++)
		//		{
		//			System.out.println("Row: " + i);
		//			for (int j = 0 ; j < 8; j++)
		//			{
		//				if (board.getPieces()[i][j] != null)
		//				{
		//					System.out.println(board.getPieces()[i][j].toString());
		//				}
		//			}
		//			System.out.println();
		//		}
		//		System.out.println(board);

		//		System.out.println("Testing Position class:");
//				Position pos = new Position(5,5);
		//		System.out.println(pos.getRow());
		//		System.out.println(pos.getColumn());
		//		System.out.println(pos);
		//		pos.setRow(7);
		//		pos.setColumn(0);
		//		System.out.println(pos);
//				Position pos2 = new Position(7,5);
		//		System.out.println(pos.equals(pos2));

//				System.out.println("Testing Bridge Pattern:");
//				MoveableGamePiece piece = new Pawn(Color.WHITE);
		//		System.out.println(piece);
		//		System.out.println(piece.getColor());
//				piece.setHasNeverMoved(false);
//				System.out.println("Never moved: " + piece.getHasNeverMoved());
//				System.out.println("Valid move: " + piece.moveIsValid(pos, pos2, true));
		//		System.out.println(piece);
				

//				System.out.println("Testing pieceIsCorrectColor method:");
//				Position whitePiece = new Position(0,0);
//				Position blackPiece = new Position(7,0);
//				System.out.println(board.pieceIsCorrectColor(whitePiece));
//				System.out.println(board.pieceIsCorrectColor(blackPiece));

//				System.out.println("Testing executeMove and testPrint methods: ");
//				board.testPrintPieces();
//				board.executeMove(new Position (1,3), new Position (3,3));
//				board.executeMove(new Position (6,4), new Position (4,4));
//				board.executeMove(new Position (0,2), new Position (4,6));
//				board.executeMove(new Position (7,3), new Position (4,6));
//				board.executeMove(new Position (3,3), new Position (4,4));
//				board.executeMove(new Position (1,4), new Position (3,4));
//				board.executeMove(new Position (0,4), new Position (1,4));
//				board.executeMove(new Position (7,4), new Position (6,4));
		
//				MoveableGamePiece[][] temp = board.getDeepCopyOfPieces();
//				
//				for (int i = 0; i < 8; i++)
//				{
//					System.out.println("Row: " + i);
//					for (int j = 0 ; j < 8; j++)
//					{
//						if (temp[i][j] != null)
//						{
//							System.out.println(temp[i][j].toString());
//						}
//					}
//					System.out.println();
//				}	
		
//		System.out.println(board.intermediateSquareIsBlocked(new Position(7,7), new Position(7,0) ) );
//		board.executeMove(new Position(1,4), new Position(3,4));
//		System.out.println(board.moveIsBlocked(new Position(7,1), new Position(5,2) ) );
//		System.out.println(board.isValidMove(new Position(6,1), new Position(5,0), board.pieces) );
		
//		System.out.println(board.wouldBeInCheck(new Position(1,4), new Position(3,4)));
//		board.executeMove(new Position(1,4), new Position(3,4));
//		System.out.println(board.wouldBeInCheck(new Position(6,6), new Position(4,6)));
//		board.executeMove(new Position(6,6), new Position(4,6));
//		System.out.println(board.wouldBeInCheck(new Position(0,3), new Position(4,7)));
//		board.executeMove(new Position(0,3), new Position(4,7));
//		System.out.println(board.wouldBeInCheck(new Position(6,5), new Position(5,5)));
//		board.executeMove(new Position(6,5), new Position(5,5));
	}
}
