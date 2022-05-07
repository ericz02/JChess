package chess;

/**
 * Class to represent a bishop in chess.
 * @author Kevin Reid, Jerry Aviles, & Eric Zheng.
 * @date April 8 - May 3, 2022
 * @implements MoveableGamePiece
 * @extends ChessPiece
 */

public final class Bishop extends ChessPiece implements MoveableGamePiece 
{

	public Bishop(Color color) 
	{
		super(color);
	}

	/**
	 * A bishop can only move diagonally.
	 */
	@Override
	//Tested
	public boolean moveIsValid(Position start, Position end, boolean moveIsACapture) 
	{
		return ( Math.abs(start.getRow() - end.getRow() ) == Math.abs(start.getColumn() - end.getColumn() ) );
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
		return "Bishop [color=" + color + ", hasNeverMoved=" + hasNeverMoved + "]";
	}
}
