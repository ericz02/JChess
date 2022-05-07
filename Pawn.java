package chess;

/**
 * Class to represent a pawn in chess.
 * @author Kevin Reid, Jerry Aviles, & Eric Zheng.
 * @date April 8 - May 3, 2022
 * @implements MoveableGamePiece
 * @extends ChessPiece
 */

public final class Pawn extends ChessPiece implements MoveableGamePiece 
{
	
	public Pawn(Color color) 
	{
		super(color);
	}

	/**
	 * A pawn normally moves one square forward, but has the option to move two squares
	 * forward on its first move ONLY. A pawn moves one square diagonally forwards instead when
	 * capturing. Note that "Forward" means ascending for white but descending for black.
	 */
	@Override
	public boolean moveIsValid(Position start, Position end, boolean moveIsACapture) 
	{
		return moveIsACapture ? captureIsValid(start, end) : moveIsValid(start, end);
	}
	
	/**
	 * Overloaded helper method designed to be called by the inherited, overridden, public version.
	 * @param start The position of the starting square on the board.
	 * @param end	The position of the ending square on the board. 
	 * @return True if the move takes the pawn one square forward. Also true if the move is the pawn's
	 * FIRST move and takes the pawn two squares forward. False otherwise.
	 */
	private boolean moveIsValid(Position start, Position end)
	{
		//Pawns cannot move horizontally.
				if( (start.getColumn() != end.getColumn() ) )
				{
					return false;
				}
				
				int distanceLimit = 1;
				
				if(hasNeverMoved)
				{
					distanceLimit = 2;
				}
				
				if( (color == Color.WHITE) && ( ( end.getRow() - start.getRow() ) <= distanceLimit) 
						&& ( end.getRow() - start.getRow() > 0 ) )
						
				{
					return true;
				}
				else if( (color == Color.BLACK) && ( ( start.getRow() - end.getRow() ) <= distanceLimit) 
						&& ( start.getRow() - end.getRow() > 0 ) )
				{
					return true;
				}
				else
				{
					return false;
				}
	}

	/**
	 * Helper method to determine whether a capture move is valid.
	 * Precondition: The caller has determined that there is a piece to be captured, either on 
	 * the end square or by en passant.
	 * @param start The starting square of the proposed capture move.
	 * @param end   The ending square of the proposed move.
	 * @return True if the pawn can make the move, false otherwise.
	 */
	private boolean captureIsValid(Position start, Position end)
	{
		
		//Test horizontal movement one square.
		if( Math.abs( start.getColumn() - end.getColumn() ) != 1 )
		{
			return false;
		}
		//Test movement forward one square for white
		else if( (color == Color.WHITE) && (end.getRow() - start.getRow() ) == 1)
		{
			return true;
		}
		//Test movement forward one square for black.
		else if( ( ( start.getRow() - end.getRow() ) == 1) && (color == Color.BLACK) )
		{
			return true;
		}
		else
		{
			return false;
		}
	
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pawn [color=" + color + ", hasNeverMoved=" + hasNeverMoved + "]";
	}
	
	
}
