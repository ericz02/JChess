package chess;

/**
 * @author Kevin Reid, Jerry Aviles, & Eric Zheng.
 * @date April 8 - May 3, 2022
 * Launcher class for PlayableChessBoard.java
 */

public class ChessLauncher 
{

	/**
	 * Method to launch the chess game.
	 * @param args None.
	 */
	public static void main(String[] args) 
	{
		PlayableChessBoard board = new PlayableChessBoard();
		board.play();
		
	}

}
