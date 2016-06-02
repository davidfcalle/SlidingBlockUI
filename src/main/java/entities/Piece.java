package entities;

/**
 * @author David Suarez
 *
 */
public class Piece 
{

	/**
	 * row: Fila donde se ubicara la pieza blanca.
	 */
	private Integer row;


	/**
	 * column: Columna donde se ubicara la pieza blanca.
	 */
	private Integer column;
	
/**--------------------------------------------------------------Constructor--------------------------------------------------------------*/
	
	
	public Piece()
	{
		
	}

	
	public Piece(Integer row, Integer column) 
	{
		super();
		this.row = row;
		this.column = column;
	}


/**--------------------------------------------------------------Getter&Setter----------------------------------------------------------*/		
	
	/**
	 * @return the row
	 */
	public Integer getRow() 
	{
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(Integer row)
	{
		this.row = row;
	}

	/**
	 * @return the column
	 */
	public Integer getColumn() 
	{
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(Integer column) 
	{
		this.column = column;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Piece [row=" + row + ", column=" + column + "]";
	}
}