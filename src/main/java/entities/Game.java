package entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.SocketUtils;

public class Game 
{
	
	private List<Player> jugadores;
	private int numPlayers;
	private int idPlayerToUpd;
	private int typeMovement; // 0 = Move on column; 1 = Move on row
	private int piece_1_ToMove;
	private int piece_2_ToMove;
	private boolean newBoard;
	private boolean newPlayer;
	private boolean newSuscriber;
	private boolean moveBoard;
	private boolean rebuildBoard;
	
	
	public Game()
	{
		jugadores = new ArrayList<>();
		numPlayers = 0;
		newBoard = false;
		newSuscriber = false;
		rebuildBoard= false;
		moveBoard = false;
		
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
	 * @return the newSuscriber
	 */
	public boolean isNewSuscriber() {
		return newSuscriber;
	}

	/**
	 * @param newSuscriber the newSuscriber to set
	 */
	public void setNewSuscriber(boolean newSuscriber) {
		this.newSuscriber = newSuscriber;
	}

	/**
	 * @return the typeMovement
	 */
	public int getTypeMovement() {
		return typeMovement;
	}

	/**
	 * @param typeMovement the typeMovement to set
	 */
	public void setTypeMovement(int typeMovement) {
		this.typeMovement = typeMovement;
	}

	/**
	 * @return the moveBoard
	 */
	public boolean isMoveBoard() {
		return moveBoard;
	}

	/**
	 * @param moveBoard the moveBoard to set
	 */
	public void setMoveBoard(boolean moveBoard) {
		this.moveBoard = moveBoard;
	}

	
	/**
	 * @return the piece_1_ToMove
	 */
	public int getPiece_1_ToMove() {
		return piece_1_ToMove;
	}

	/**
	 * @param piece_1_ToMove the piece_1_ToMove to set
	 */
	public void setPiece_1_ToMove(int piece_1_ToMove) {
		this.piece_1_ToMove = piece_1_ToMove;
	}

	/**
	 * @return the piece_2_ToMove
	 */
	public int getPiece_2_ToMove() {
		return piece_2_ToMove;
	}

	/**
	 * @param piece_2_ToMove the piece_2_ToMove to set
	 */
	public void setPiece_2_ToMove(int piece_2_ToMove) {
		this.piece_2_ToMove = piece_2_ToMove;
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
		
		this.addPlayer( player );
		
		this.idPlayerToUpd = player.getId() - 1;
		this.rebuildBoard = true;
		this.newBoard = true;
		this.newPlayer = true;
		this.moveBoard = false;
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
		this.rebuildBoard = false;
		this.newBoard = false;
		this.newPlayer = true;
		this.moveBoard = false;
		
		return actualPlayer;
	}
	
	/**
	 * Identifica hacia que posición se movió la casilla en blanco,
	 * valida si ese movimiento es válido y avisa a la GUI para repintar.
	 * @param player
	 * @return
	 */
	public Player changeBoard( Player p ) 
	{
		Player player = this.jugadores.get( p.getId() - 1 );
		Piece blankActualPos = player.getBoard().getBlank();
		Piece blankNewPos = p.getBoard().getBlank();
		
		if( blankActualPos.getRow() == blankNewPos.getRow() )
		{
			this.typeMovement = 0;//movio en columna
			int columnsMoves = blankActualPos.getColumn() - blankNewPos.getColumn();
			int columnActual = p.getBoard().getCurrentState().length * blankActualPos.getColumn();
			int columnMoveTo = columnActual + ( p.getBoard().getCurrentState().length * columnsMoves ) ;

			if( Math.abs( columnsMoves ) == 1 )
			{
				this.piece_1_ToMove = columnActual + blankActualPos.getRow();
				this.piece_2_ToMove = columnMoveTo + blankActualPos.getRow();
			}
			else
			{
				return null;
			}
		}	
		else if( blankActualPos.getColumn() == blankNewPos.getColumn() )
		{
			this.typeMovement = 1; //movio en fila
			int rowsMoves = blankActualPos.getRow() - blankNewPos.getRow();
			int rowActual = p.getBoard().getCurrentState().length * blankActualPos.getRow();
			int rowMoveTo = rowActual - ( p.getBoard().getCurrentState().length * rowsMoves ) ;

			if( Math.abs( rowsMoves ) == 1 )
			{
				this.piece_1_ToMove = rowActual + blankActualPos.getColumn();
				this.piece_2_ToMove = rowMoveTo + blankActualPos.getColumn();
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
		
		System.out.println("P!: "+ piece_1_ToMove + "P: " + piece_2_ToMove );
		this.newBoard = false;
		this.newPlayer = false;
		this.newSuscriber = false;
		this.moveBoard = true;	
		player.setBoard( p.getBoard() );
		player.getBoard().addMovement();
		
		return player;
		
	}
	public void addPlayer( Player p ) 
	{
		this.jugadores.add( p );
	}
}
