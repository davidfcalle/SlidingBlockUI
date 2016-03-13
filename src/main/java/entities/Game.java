package entities;

import java.util.ArrayList;
import java.util.List;


public class Game 
{
	
	private List<Player> jugadores;
	private int numPlayers;
	private int idPlayerToUpd;
	private int typeMovement; // 0 = Move to RIGHT; 1 = Move to LEFT; 2 = Move to UP; 3 = Move to DOWN.
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
		idPlayerToUpd = -1;
		piece_1_ToMove = -1;
		piece_2_ToMove = -1;
		typeMovement = -1;
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
	public boolean isMoveBoard() 
	{
		return moveBoard;
	}

	/**
	 * @param moveBoard the moveBoard to set
	 */
	public void setMoveBoard(boolean moveBoard) 
	{
		this.moveBoard = moveBoard;
	}

	
	/**
	 * @return the piece_1_ToMove
	 */
	public int getPiece_1_ToMove() 
	{
		return piece_1_ToMove;
	}

	/**
	 * @param piece_1_ToMove the piece_1_ToMove to set
	 */
	public void setPiece_1_ToMove(int piece_1_ToMove) 
	{
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
	 * Se encarga de crear un Player vacio para asignarle el Board que se agregara.
	 * @param board: Tablero de juego que se agregara al juego.
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
	 * @param player: Jugador al que se le asignara un tablero.
	 * @return actualPlayer: Una instancia de Player ID y tablero asignados 
	 * 						 Null si no existe tablero disponible aun.
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
	 * Identifica hacia que posicion se movi la casilla en blanco,
	 * verifica si ese movimiento es valido y avisa a la GUI para repintar.
	 * @param player: Jugador que tiene el tablero donde se moveran las piezas.
	 * @return player: Jugador con el tablero actualizado luego de mover a pieza en blanco.
	 */
	public Player changeBoard( Player p ) 
	{
		Player player = this.jugadores.get( p.getId() - 1 );
		Piece blankActualPos = player.getBoard().getBlank();
		Piece blankNewPos = p.getBoard().getBlank();
		int size = player.getBoard().getCurrentState().length;
		this.piece_1_ToMove = ( size * blankActualPos.getRow() ) + blankActualPos.getColumn();
		this.piece_2_ToMove = ( size * blankNewPos.getRow() ) + blankNewPos.getColumn();
		
		if( blankNewPos.getRow() < 0 || blankNewPos.getRow() > size || blankNewPos.getColumn() < 0 || blankNewPos.getColumn() > size )
			return null;
		
		if( blankActualPos.getRow() == blankNewPos.getRow() )
		{
			//Movi en columnas solo puede moverse a izquierda o derecha.
			
			int columnsMoves = blankNewPos.getColumn() - blankActualPos.getColumn();

			if( Math.abs( columnsMoves ) == 1 )
			{
				 if( columnsMoves > 0 )
					 this.typeMovement = 0; //Se movi a la derecha.
				 else
					 this.typeMovement = 1; //Se movi a la izquierda.
			}
			else
			{
				return null;
			}
		}	
		else if( blankActualPos.getColumn() == blankNewPos.getColumn() )
		{
			//Movi en filas solo puede moverse arriba o abajo.
			
			int rowsMoves = blankNewPos.getRow() - blankActualPos.getRow();

			if( Math.abs( rowsMoves ) == 1 )
			{
				 if( rowsMoves > 0 )
					 this.typeMovement = 2; //Se movi hacia arriba.
				 else
					 this.typeMovement = 3; //Se movi hacia abajo.
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
		/*Actualizar la tablero de juego del juagdor teniendo en cuenta hacia donde se movi la pieza en blanco*/
		String board [][] = player.getBoard().getCurrentState();
		board[blankActualPos.getRow()][blankActualPos.getColumn()] = board[blankNewPos.getRow()][blankNewPos.getColumn()];
		board[blankNewPos.getRow()][blankNewPos.getColumn()] = "BB";
		player.getBoard().setCurrentState( board );
		player.getBoard().setBlank( blankNewPos );
		player.getBoard().addMovement();
		
		this.idPlayerToUpd = player.getId() - 1;
		System.out.println("P1: "+ piece_1_ToMove + "P2: " + piece_2_ToMove );
		this.newBoard = false;
		this.newPlayer = false;
		this.newSuscriber = false;
		this.moveBoard = true;	
		
		return player;
		
	}
	public void addPlayer( Player p ) 
	{
		this.jugadores.add( p );
	}
}
