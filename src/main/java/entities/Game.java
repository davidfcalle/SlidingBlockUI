package entities;

import java.util.ArrayList;
import java.util.List;

public class Game 
{
	
	private List<Player> jugadores;
	private int numPlayers;
	private int idPlayerToUpd;
	private boolean newBoard;
	private boolean rebuildBoard;
	private boolean newPlayer;	
	
	
	public Game()
	{
		jugadores = new ArrayList<>();
		numPlayers = 0;
		newBoard = false;
		rebuildBoard= false;
	}

	/**
	 * @return the jugadores
	 */
	public List<Player> getJugadores() 
	{
		return jugadores;
	}

	/**
	 * @param jugadores the jugadores to set
	 */
	public void setJugadores(List<Player> jugadores) 
	{
		this.jugadores = jugadores;
	}

	/**
	 * @return the numPlayers
	 */
	public int getNumPlayers() 
	{
		return numPlayers;
	}

	/**
	 * @param numPlayers the numPlayers to set
	 */
	public void setNumPlayers(int numPlayers) 
	{
		this.numPlayers = numPlayers;
	}
	
	/**
	 * @return the idPlayerToUpd
	 */
	public int getIdPlayerToUpd() {
		return idPlayerToUpd;
	}

	/**
	 * @param idPlayerToUpd the idPlayerToUpd to set
	 */
	public void setIdPlayerToUpd(int idPlayerToUpd) {
		this.idPlayerToUpd = idPlayerToUpd;
	}

	/**
	 * @return the newBoard
	 */
	public boolean isNewBoard() {
		return newBoard;
	}

	/**
	 * @param newBoard the newBoard to set
	 */
	public void setNewBoard(boolean newBoard) 
	{
		this.newBoard = newBoard;
	}
	
	/**
	 * @return the rebuildBoard
	 */
	public boolean isRebuildBoard() 
	{
		return rebuildBoard;
	}

	/**
	 * @param rebuildBoard the rebuildBoard to set
	 */
	public void setRebuildBoard(boolean rebuildBoard) 
	{
		this.rebuildBoard = rebuildBoard;
	}	
	
	/**
	 * @return the newPlayer
	 */
	public boolean isNewPlayer() {
		return newPlayer;
	}

	/**
	 * @param newPlayer the newPlayer to set
	 */
	public void setNewPlayer(boolean newPlayer) {
		this.newPlayer = newPlayer;
	}

	/**
	 * Se encarga de crear un Player vacío para asignarle el Board que se agregará.
	 * @param board: Tablero de juego que se agregará al juego.
	 */
	public void addBoard( Board board ) 
	{
		Player player = new Player( );
		
		board.setMovements( 0 );
		player.setId(this.getJugadores().size() + 1);
		player.setBoard(board);
		player.setPoints( 0 );
		player.setName( "Player " + player.getId() + " - No Player Yet!" );
		
		this.addPlayer(player);
		
		this.idPlayerToUpd = player.getId() - 1;
		this.rebuildBoard = true; //AUN NO HACE NADA.
		this.newBoard = true;
		this.newPlayer = true;
	}
	
	/**
	 * Se encarga de asignar un Player a un tablero de Taquin disponible.
	 * @param player: Jugador al que se le asignará un tablero.
	 * @return actualPlayer: Una instancia de Player ID y tablero asignados 
	 * 						 Null si no existe tablero disponible aún.
	 */
	public Player addBoardToPlayer( Player player ) 
	{
		if( this.numPlayers >= this.jugadores.size() )
			return null;
		
		Player actualPlayer = this.jugadores.get( this.numPlayers );
		actualPlayer.setName( player.getName( ) );
		this.numPlayers++;
		
		this.idPlayerToUpd = actualPlayer.getId() - 1;
		this.rebuildBoard = false; //AUN NO HACE NADA.
		this.newBoard = false;
		this.newPlayer = true;
		
		return actualPlayer;
	}
	
	public void addPlayer( Player p ) 
	{
		this.jugadores.add( p );
	}
}
