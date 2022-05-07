package chess;

/**
 * Class to represent a king in chess.
 * @author Kevin Reid, Jerry Aviles, & Eric Zheng.
 * @date April 8 - May 3, 2022
 * @implements MoveableGamePiece
 * @extends ChessPiece
 */

public final class King extends ChessPiece implements MoveableGamePiece
{
	
	public King(Color color) 
	{
		super(color);
	}

	/**
	 * King can move exactly one square in any direction.
	 * Because an instance of King cannot directly talk to the other pieces on the board, it is the CALLING CLASS'S
	 * RESPONSIBILITY to determine whether the king is in check, is trying to move into check, or is able to
	 * castle.
	 */
	//Tested
	@Override
	public boolean moveIsValid(Position start, Position end, boolean moveIsACapture) 
	{
		return ( (Math.abs( start.getRow() - end.getRow() ) < 2) && 
				( Math.abs(start.getColumn() - end.getColumn() ) < 2) );
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
		return "King [color=" + color + ", hasNeverMoved=" + hasNeverMoved + "]";
	}
	
}
