package chess;

/**
 * Base class to provide data and functionality shared by all six types of chesspieces.
 * @author Kevin Reid, Jerry Aviles, & Eric Zheng.
 * @date April 8 - May 3, 2022
 * @implements MoveableGamePiece
 */

public abstract class ChessPiece implements MoveableGamePiece
{
	protected Color color;	//Do not alter once initialized!
	
	/*
	 * This data member is important because a pawn has the option to move two squares forward
	 * ONLY on its first move. Also, you cannot castle with a king or rook which has already moved.
	 */
	protected boolean hasNeverMoved;
	
	public ChessPiece(Color color) 
	{
		super();
		this.color = color;
		hasNeverMoved = true;
	}
	
	/**
	 * Precondition: The chesspiece is NOT attempting to castle, capture en passant, or violate
	 * the rules of "check."
	 */
	public abstract boolean moveIsValid(Position start, Position end, boolean moveIsACapture);
	
	public Color getColor()
	{
		return color;
	}

	public boolean getHasNeverMoved() 
	{
		return hasNeverMoved;
	}

	public void setHasNeverMoved(boolean hasNeverMoved) 
	{
		this.hasNeverMoved = hasNeverMoved;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (hasNeverMoved ? 1231 : 1237);
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
		ChessPiece other = (ChessPiece) obj;
		if (color != other.color)
			return false;
		if (hasNeverMoved != other.hasNeverMoved)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChessPiece [color=" + color + ", hasNotMovedYet=" + hasNeverMoved + "]";
	}
	
}
