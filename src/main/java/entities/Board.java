package entities;

import java.util.Random;


public class Board {
	
	/**
	 * @param currentState: Valores del tablero y su organizacion.
	 */
	private String[][] currentState;
		
	/**
	 * @param movements: Cantidad de movimientos que se han ejecutado sobre el tablero.
	 */
	private Integer movements;
	
	
	/**
	 * @param blank: Representa la ubicacion de la pieza blanca en el tablero.
	 */
	private Piece blank;
	
	
/**--------------------------------------------------------------Constructor--------------------------------------------------------------*/	
	/**
	 * Constructor.
	 */
	public Board() 
	{
		super();
	}

	
/**--------------------------------------------------------------Getter&Setter----------------------------------------------------------*/	
	/**
	 * @return the currentState
	 */
	public String[][] getCurrentState() 
	{
		return currentState;
	}

	/**
	 * @param currentState the currentState to set
	 */
	public void setCurrentState(String[][] currentState) 
	{
		this.currentState = currentState;
	}

	/**
	 * @return the movements
	 */
	public Integer getMovements() 
	{
		return movements;
	}

	/**
	 * @param movements the movements to set
	 */
	public void setMovements(Integer movements) 
	{
		this.movements = movements;
	}

	/**
	 * @return the blank
	 */
	public Piece getBlank() 
	{
		return blank;
	}

	/**
	 * @param blank the blank to set
	 */
	public void setBlank(Piece blank) 
	{
		this.blank = blank;
	}

/**--------------------------------------------------------------Negocio-----------------------------------------------------------------*/
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Incrementa en 1 la cantidad de movimientos ejecutados sobre el tablero.
	 */
	public void addMovement()
	{
		movements++;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Asigna valores aleatorios a la matriz que representa el tablero Taquin.
	 */
	public void randomizeBoard()
	{	
		for (int i = 0; i < this.currentState.length ; i++)
		{
			for (int j = 0; j < this.currentState.length; j++) 
			{
				Random rnumber = new Random();
				int number	 = rnumber.nextInt( this.currentState.length ) + 1; 
				currentState[i][j] = number+"";
			}
		}
	
	}
}
