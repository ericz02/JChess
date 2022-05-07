package chess;

/**
 * Generates a unicode textual chessboard 
 * Reference:  https://en.wikipedia.org/wiki/Chess_symbols_in_Unicode 
 * @author lji Modified by Kevin Reid February 1-15, 2022
 * 	Futher modified for re-use by Kevin Reid, Jerry Aviles, & Eric Zheng
 * @date March 25-April 17, 2022
 * 
 * Note: For group project, DO NOT USE MAIN METHOD! It will print an unwanted header.
 */

public class ChessBoardPrinter {
	
	// unicode values of pieces.
		static public final char WHITE_PAWN = '\u2659';
		static public final char WHITE_KNIGHT = '\u2658';
		static public final char WHITE_BISHOP = '\u2657';
		static public final char WHITE_ROOK = '\u2656';
		static public final char WHITE_QUEEN = '\u2655';
		static public final char WHITE_KING = '\u2654';
		
		// Black piece values are equiv white pieces + 6
		private static final int BLACK_PIECE_DISPLACEMENT = 6;
		
		static public final char BLACK_PAWN = WHITE_PAWN + BLACK_PIECE_DISPLACEMENT;
		static public final char BLACK_KNIGHT = WHITE_KNIGHT + BLACK_PIECE_DISPLACEMENT;
		static public final char BLACK_BISHOP = WHITE_BISHOP + BLACK_PIECE_DISPLACEMENT;
		static public final char BLACK_ROOK = WHITE_ROOK + BLACK_PIECE_DISPLACEMENT;
		static public final char BLACK_QUEEN = WHITE_QUEEN + BLACK_PIECE_DISPLACEMENT;
		static public final char BLACK_KING = WHITE_KING + BLACK_PIECE_DISPLACEMENT;

		// our choice for "empty square" - medium rectangle ... 
		static public final char EMPTY_SQUARE = '\u25AD';
		
	/*
	 * The above constants can be used individually in any combination to print any arrangement 
		of pieces on a chessboard. The following arrays are useful for printing the board at the START of
		the game.
	 */
		
	static private final  char [] WHITE_PIECES = 
		{
				WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_BISHOP,
				WHITE_KNIGHT, WHITE_ROOK
		};
	
	static private final  char [] BLACK_PIECES = 
		{
				BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_BISHOP,
				BLACK_KNIGHT, BLACK_ROOK
		};
	 
	
	private static final int ROWS = 8; 
	private static final int COLUMNS = ROWS;
	
	//The first row is row A, which is 65 in ASCII. Second row is row B = 66.
	public static final int ASCII_DISPLACEMENT_FOR_COLUMN_LETTERS = 64;
	
	private static final String HEADING = 
			"-----------------------------------------\n"
			+ "        Unicode Symbols Chessboard\n" 
			+ "-----------------------------------------\n";
	
	
	static private void displayHeading() {
		//Altered by Kevin Reid to print all caps.
		System.out.printf( "%S", HEADING);
	}
	
	public static void displayStartingBoard() {
		
		/*
		 Character to be printed 8 times on a line by a loop later.
		 Initialization to null here is PROBABLY unnecessary, but I won't risk it.
		*/
		char chesspiece = '\0';
		 char columnLetter;
		
		for(int row = ROWS ; row >= 1  ; --row ) {
			columnLetter = (char) (row + ASCII_DISPLACEMENT_FOR_COLUMN_LETTERS);
			System.out.printf("%-5d", row);

			/**
			 * COMPLETE LOGIC THAT DISPLAYS REST OF BOARD HERE 
			 */
			
			int indexOfCurrentPiece = 0; //Used for the constant arrays of pieces.
			
			/*
			 Switch to determine value of chesspiece before a loop prints it 8 times.
			 Cases 1 and 8 are special and contain their own loops.
			*/
			switch (row)
			{
			case 8: 
				chesspiece = '\0';	//We only want this variable printed in lines 2-7.
				
				//Get ready for inner loop.
				indexOfCurrentPiece = 0;
				System.out.print("      ");
				//Loop to print the 8 senior black pieces:
				for(int i = 0; i <COLUMNS; i++)
				{
					char currentPiece = (char) (BLACK_PIECES[indexOfCurrentPiece] );
					System.out.printf("%-16c", currentPiece);
					indexOfCurrentPiece++;
				}
			break;
			case 7:
				chesspiece =  BLACK_PAWN;
			break;
			case 6:
			case 5:
			case 4:
			case 3:
				chesspiece = EMPTY_SQUARE;
				break;
			case 2:
				chesspiece = WHITE_PAWN;
				break;
			case 1:
				//Same as case 8, but for white pieces instead of black.
				
				chesspiece = '\0';	//We only want this variable printed in lines 2-7.
				
				//Get ready for inner loop.
				indexOfCurrentPiece = 0;
				System.out.print("      ");
				//Loop to print senior white pieces:
				for(int i = 0; i <COLUMNS; i++)
				{
					char currentPiece = (char) (WHITE_PIECES[indexOfCurrentPiece]);
					System.out.printf("%-16c", currentPiece);
					indexOfCurrentPiece++;
				}
				break;
			default:
				System.err.println("Error. Invalid value in chesspiece switch.\n");
				System.exit(1);
			}//End switch.
			
			/*
			 Loop to print the value of chesspiece (determined by the above switch) 8 times. Prints nothing if
			 value is null, as it is for rows 1 and 8.
			*/
			for (int i = 0 ; i < COLUMNS; i++)
			{
				System.out.printf("%-16c", chesspiece);
			}
			
			System.out.println('\n');
		}//End outer for loop.
		
		for (int column = 1; column <= COLUMNS; column++)
		{
			System.out.printf("%7C", column + ASCII_DISPLACEMENT_FOR_COLUMN_LETTERS);
		}
		
	}//End displayStartingBoard method.
	
	
	// Only Public Method Used Directly By main() 
	static public void display() {
		displayHeading(); 
		displayStartingBoard(); 
	
	}

	/**
	 * Method to print a custom 8x8 array of chesspieces to the console.
	 * @param pieces the array of chess pieces to be printed.
	 */
	public static void displayCustomBoard(MoveableGamePiece[][] pieces)
	{
		char chesspiece;
		for (int row = ROWS-1; row >= 0; row--)
		{
			System.out.print(row + 1 + "   ");
			for (int col = 0; col < COLUMNS; col++)
			{
				MoveableGamePiece piece = pieces[row][col];
				if (piece == null)
				{
					System.out.printf("%-16c", EMPTY_SQUARE);
				}
				else
				{
					switch(piece.getClass().getSimpleName()) 
					{
					case "King":
						chesspiece = (piece.getColor() == Color.WHITE) ?  WHITE_KING : BLACK_KING;
						System.out.printf("%-16c", chesspiece);
						break;
					case "Queen":
						chesspiece = (piece.getColor() == Color.WHITE) ? WHITE_QUEEN : BLACK_QUEEN;
						System.out.printf("%-16c", chesspiece);
						break;
					case "Rook":
						chesspiece = (piece.getColor() == Color.WHITE) ? WHITE_ROOK : BLACK_ROOK;
						System.out.printf("%-16c", chesspiece);
						break;
					case "Bishop":
						chesspiece = (piece.getColor() == Color.WHITE) ? WHITE_BISHOP : BLACK_BISHOP;
						System.out.printf("%-16c", chesspiece);
						break;
					case "Knight":
						chesspiece = (piece.getColor() == Color.WHITE) ? WHITE_KNIGHT : BLACK_KNIGHT;
						System.out.printf("%-16c", chesspiece);
						break;
					case "Pawn":
						chesspiece = (piece.getColor() == Color.WHITE) ? WHITE_PAWN : BLACK_PAWN;
						System.out.printf("%-16c", chesspiece);
						break;
					default:
						System.err.println("Invalid value in switch in ChessBoardPrinter.displayCustomBoard()");
						System.exit(3);
					}
				}
				
				
			}
			
			System.out.println('\n');
				
		}
		
		for(int i = 1; i <= ROWS; i++)
		{
			System.out.printf("%7C", i+ASCII_DISPLACEMENT_FOR_COLUMN_LETTERS);
		}
		System.out.println('\n');
		
	}

	/**
	 * @param args None.
	 */
	public static void main(String[] args) {
		
		// invoke display method 
		ChessBoardPrinter.display();
		
		
	}
	
}