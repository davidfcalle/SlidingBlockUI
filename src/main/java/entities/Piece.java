package entities;

public class Piece {

	/**
	 * @param row: Fila donde se ubicara la pieza blanca.
	 */
	private Integer row;


	/**
	 * @param column: Columna donde se ubicara la pieza blanca.
	 */
	private Integer column;
	
/**--------------------------------------------------------------Constructor--------------------------------------------------------------*/
	public Piece()
	{
		
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
}
