package chess;

/**
 * Interface for various types of moaveable game pieces, such as chesspieces
 * or checkers.
 * @author Kevin Reid, Jerry Aviles, & Eric Zheng.
 * @date April 8 - May 3, 2022
 */

public interface MoveableGamePiece 
{
	public abstract Color getColor();
	
	/**
	 * Method to determine whether the piece can make a particular move under standard conditions.
	 * Preconditions: 1. start and end are two different valid squares on the board.
	 * 2. No other pieces or special conditions are impeding the proposed move. 
	 * @param start The position of the starting square on the board.
	 * @param end	The position of the ending square on the board.
	 * @param moveIsACapture True if the move involves capturing another piece; false otherwise.
	 * @return  True if the type of piece in question can legally move from start to end.
	 * 		False otherwise. 
	 */
	public abstract boolean moveIsValid(Position start, Position end, boolean moveIsACapture);
	
	public boolean getHasNeverMoved();
	
	public void setHasNeverMoved(boolean hasNeverMoved);
}
