package entities;

import java.util.Random;


public class Board {
	
	private String[][] currentState;
		
	private Integer movements;
	
	
	/**
	 * @param blank: Representa la ubicacion
	 */
	private Piece blank;
	
	/**
	 * 
	 */
	public Board() 
	{
		super();
	}

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

	public void addMovement()
	{
		movements++;
	}
	
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
