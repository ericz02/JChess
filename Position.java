package chess;

/**
 * Class to represent a coordinate pair on a grid, such as a chessboard.
 * @author Kevin Reid, Jerry Aviles, & Eric Zheng.
 * @date April 8 - May 3, 2022
 */

public class Position 
{
	int row;
	int column;
	
	public int getRow() 
	{
		return row;
	}
	
	public void setRow(int row) 
	{
		this.row = row;
	}
	
	public int getColumn() 
	{
		return column;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public Position(int row, int column) 
	{
		super();
		this.row = row;
		this.column = column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		Position other = (Position) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [row=" + row + ", column=" + column + "]";
	}
	
}
