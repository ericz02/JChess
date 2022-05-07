package chess;

/**
 * Class to represent a knight in chess.
 * @author Kevin Reid, Jerry Aviles, & Eric Zheng.
 * @date April 8 - May 3, 2022
 * @implements MoveableGamePiece
 * @extends ChessPiece
 */

public final class Knight extends ChessPiece implements MoveableGamePiece 
{

	public Knight(Color color) 
	{
		super(color);
	}

	/**
	 * Knight can only move in a 2 x 1 'L'.
	 */
	@Override
	//Tested.
	public boolean moveIsValid(Position start, Position end, boolean moveIsACapture) {
		
		if ( (Math.abs(start.getColumn() - end.getColumn() ) == 2) && (Math.abs(start.getRow() - end.getRow() ) == 1) )
		{
			return true;
		}
		else if ( (Math.abs(start.getRow() - end.getRow() ) == 2) && (Math.abs(start.getColumn() - end.getColumn() ) == 1) )
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
		return "Knight [color=" + color + ", hasNeverMoved=" + hasNeverMoved + "]";
	}

}
