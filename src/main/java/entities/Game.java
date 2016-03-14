package entities;

import java.util.ArrayList;
import java.util.List;


public class Game 
{
	
	private List<Player> players;
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
	
/**--------------------------------------------------------------Constructor--------------------------------------------------------------*/	
	public Game()
	{
		players = new ArrayList<>();
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

/**--------------------------------------------------------------Getter&Setter----------------------------------------------------------*/		
	/**
	 * @return the jugadores
	 */
	public List<Player> getJugadores() 
	{
		return players;
	}

	/**
	 * @param jugadores the jugadores to set
	 */
	public void setJugadores(List<Player> jugadores) 
	{
		this.players = jugadores;
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
	public int getIdPlayerToUpd()
	{
		return idPlayerToUpd;
	}

	/**
	 * @param idPlayerToUpd the idPlayerToUpd to set
	 */
	public void setIdPlayerToUpd(int idPlayerToUpd)
	{
		this.idPlayerToUpd = idPlayerToUpd;
	}

	/**
	 * @return the newBoard
	 */
	public boolean isNewBoard() 
	{
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
	public boolean isNewPlayer() 
	{
		return newPlayer;
	}

	/**
	 * @param newPlayer the newPlayer to set
	 */
	public void setNewPlayer(boolean newPlayer) 
	{
		this.newPlayer = newPlayer;
	}

	
	/**
	 * @return the newSuscriber
	 */
	public boolean isNewSuscriber() 
	{
		return newSuscriber;
	}

	/**
	 * @param newSuscriber the newSuscriber to set
	 */
	public void setNewSuscriber(boolean newSuscriber)
	{
		this.newSuscriber = newSuscriber;
	}

	/**
	 * @return the typeMovement
	 */
	public int getTypeMovement() 
	{
		return typeMovement;
	}

	/**
	 * @param typeMovement the typeMovement to set
	 */
	public void setTypeMovement(int typeMovement) 
	{
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

/**--------------------------------------------------------------Creacion-----------------------------------------------------------------*/
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Se encarga de crear un Player vacio, es deicr, sin nombre ni id como un "cascaron"
	 * para alli almacenar (temporalmente) el Board que se agregara.
	 * Por ultimo prende las banderas necesarias para informar a la vista que secciones debe actualizar.
	 * @param board: Tablero de juego que se agrego al juego.
	 */
	public Board addBoard( Board board ) 
	{
		Player player = new Player( );
		
		board.setMovements( 0 );
		player.setId( this.getJugadores().size() + 1 );
		player.setBoard( board );
		player.setPoints( 0 );
		player.setName( "Player " + player.getId() + " - No Player Yet!" );
		
		this.addPlayer( player );
		
		this.idPlayerToUpd = player.getId() - 1;
		this.rebuildBoard = true;
		this.newBoard = true;
		this.newPlayer = true;
		this.moveBoard = false;
		this.newSuscriber = true;
		
		return player.getBoard();
	}
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Se encarga de agregar un Player a la lista de juagdores.
	 * @param p: Player a agregar.
	 */	public void addPlayer( Player p ) 
	{
		this.players.add( p );
	}
/**--------------------------------------------------------------Asignacion---------------------------------------------------------------*/
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Verifica si el tablero que se ha pedido (representado en el atributo id de player) 
	 * existe y esta disponible, tambien si hay tableros suficientes para el nuevo player. 
	 * SI lo anterior se cumple se encarga de asignar el tablero pedido a Player.
	 * Donde player es, playerCreated, la instancia de Player creada anteriormente pero
	 * que solo contiene un tablero, es decir, aun no tiene un id y nombre de jugador 
	 * asignados, por ende a este "cascaron de Pllayer" se le asiganra el id y nombre 
	 * que contiene player, simuando asi la asignacion del tablero.
	 * Por ultimo prende las banderas necesarias para informar a la vista que secciones debe actualizar.
	 * @param player: Jugador al que se le asignara un tablero.
	 * @return playerCreated: Una instancia de Player si se pudo crear y asignarle el tablero.
	 * 						 Null si el tablero pedido aun no existe o no esta disponible.
	 */
	public Player addBoardToPlayer( Player player ) 
	{
		if( this.numPlayers >= this.players.size() || player.getId() > this.players.size() )
			return null;
		
		Player playerCreated = this.players.get( player.getId() - 1 );
		playerCreated.setId( player.getId() );
		playerCreated.setName( player.getName( ) );
		playerCreated.setPoints( player.getPoints() );
		this.numPlayers++;
		
		this.idPlayerToUpd = playerCreated.getId() - 1;
		this.rebuildBoard = false;
		this.newBoard = false;
		this.newPlayer = true;
		this.moveBoard = false;
		return playerCreated;
	}

/**--------------------------------------------------------------Movimiento---------------------------------------------------------------*/

	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Obtiene el jugador cuyo id es @idPlayerque, quien posee
	 * el tablero al que se le movera la pieza blanca. Para esto
	 * crea newPosBlank un objeto Piece con el que se  simula 
	 * el movimiento de la pieza blanca hacia la derecha.
	 * Luego se apoya en el metodo movePieceOnBoard para mover 
	 * la pieza blanca a la nueva posicion representada en newPosBlank.
	 * @param idPlayer: Jugador que tiene el tablero donde se moveran las piezas.
	 * @return player: Jugador con el tablero actualizado luego de mover la pieza blanca.
	 */
	public Player movePieceOnBoardToRight( Integer idPlayer ) 
	{
		Player player = this.players.get( idPlayer - 1 );
		Piece newPosBlank = new Piece();
		newPosBlank.setColumn( player.getBoard().getBlank().getColumn() + 1 );
		newPosBlank.setRow( player.getBoard().getBlank().getRow() );
		
		return this.movePieceOnBoard( player, newPosBlank );	
	}
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Obtiene el jugador cuyo id es @idPlayerque, quien posee
	 * el tablero al que se le movera la pieza blanca. Para esto
	 * crea newPosBlank un objeto Piece con el que se  simula 
	 * el movimiento de la pieza blanca hacia la izquierda.
	 * Luego se apoya en el metodo movePieceOnBoard para mover 
	 * la pieza blanca a la nueva posicion representada en newPosBlank.
	 * @param idPlayer: Jugador que tiene el tablero donde se moveran las piezas.
	 * @return player: Jugador con el tablero actualizado luego de mover la pieza blanca.
	 */
	public Player movePieceOnBoardToLeft( Integer idPlayer ) 
	{
		Player player = this.players.get( idPlayer - 1 );
		Piece newPosBlank = new Piece();
		newPosBlank.setColumn( player.getBoard().getBlank().getColumn() - 1 );
		newPosBlank.setRow( player.getBoard().getBlank().getRow() );
		
		return this.movePieceOnBoard( player, newPosBlank );	
	}
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Obtiene el jugador cuyo id es @idPlayerque, quien posee
	 * el tablero al que se le movera la pieza blanca. Para esto
	 * crea newPosBlank un objeto Piece con el que se  simula 
	 * el movimiento de la pieza blanca hacia arriba.
	 * Luego se apoya en el metodo movePieceOnBoard para mover 
	 * la pieza blanca a la nueva posicion representada en newPosBlank.
	 * @param idPlayer: Jugador que tiene el tablero donde se moveran las piezas.
	 * @return player: Jugador con el tablero actualizado luego de mover la pieza blanca.
	 */
	public Player movePieceOnBoardToUp( Integer idPlayer ) 
	{
		Player player = this.players.get( idPlayer - 1 );
		Piece newPosBlank = new Piece();
		newPosBlank.setColumn( player.getBoard().getBlank().getColumn() );
		newPosBlank.setRow( player.getBoard().getBlank().getRow() -1 );
		
		return this.movePieceOnBoard( player, newPosBlank );	
	}
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Obtiene el jugador cuyo id es @idPlayerque, quien posee
	 * el tablero al que se le movera la pieza blanca. Para esto
	 * crea newPosBlank un objeto Piece con el que se  simula 
	 * el movimiento de la pieza blanca hacia abajo.
	 * Luego se apoya en el metodo movePieceOnBoard para mover 
	 * la pieza blanca a la nueva posicion representada en newPosBlank.
	 * @param idPlayer: Jugador que tiene el tablero donde se moveran las piezas.
	 * @return player: Jugador con el tablero actualizado luego de mover la pieza blanca.
	 */
	public Player movePieceOnBoardToDown( Integer idPlayer ) 
	{
		Player player = this.players.get( idPlayer - 1 );
		Piece newPosBlank = new Piece();
		newPosBlank.setColumn( player.getBoard().getBlank().getColumn() );
		newPosBlank.setRow( player.getBoard().getBlank().getRow() + 1 );
		
		return this.movePieceOnBoard( player, newPosBlank );	
	}	
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Identifica hacia que posicion se movio la casilla en blanco, 
	 * y guarda en piece_1_ToMove y piece_2_ToMove las dos casillas afecatadas.
	 * Luego verifica si el movimiento es valido. Si lo anterior se cumple mueve 
	 * la pieza blanca hacia su nueva posicion y guarda en typeMovement hacia 
	 * donde se movio la pieza blanca para informarlo a la vista.
	 * Por ultimo prende las banderas necesarias para informar a la vista que secciones debe actualizar.
	 * @param player: Jugador que tiene el tablero donde se moveran las piezas.
	 * @param blankNewPos: Nueva posicion de la pieza blanca en el tablero.
	 * @return player: Jugador con el tablero actualizado luego de mover a pieza blanca.
	 * 				   Null si el movimiento no es valido.
	 */
	public Player movePieceOnBoard( Player p, Piece blankNewPos ) 
	{
		Player player = this.players.get( p.getId() - 1 );
		Piece blankActualPos = player.getBoard().getBlank();
		int size = player.getBoard().getCurrentState().length;
		this.piece_1_ToMove = ( size * blankActualPos.getRow() ) + blankActualPos.getColumn();
		this.piece_2_ToMove = ( size * blankNewPos.getRow() ) + blankNewPos.getColumn();
		System.out.println("P1: " + piece_1_ToMove + " P2: " + piece_2_ToMove );
		if( blankNewPos.getRow() < 0 || blankNewPos.getRow() >= size || blankNewPos.getColumn() < 0 || blankNewPos.getColumn() >= size )
			return null;
		
		if( blankActualPos.getRow() == blankNewPos.getRow() )
		{
			//Movio en columnas solo puede moverse a izquierda o derecha.
			
			int columnsMoves = blankNewPos.getColumn() - blankActualPos.getColumn();

			if( Math.abs( columnsMoves ) == 1 )
			{
				 if( columnsMoves > 0 )
					 this.typeMovement = 0; //Se movio a la derecha.
				 else
					 this.typeMovement = 1; //Se movio a la izquierda.
			}
			else
			{
				return null;
			}
		}	
		else if( blankActualPos.getColumn() == blankNewPos.getColumn() )
		{
			//Movio en filas solo puede moverse arriba o abajo.
			
			int rowsMoves = blankNewPos.getRow() - blankActualPos.getRow();

			if( Math.abs( rowsMoves ) == 1 )
			{
				 if( rowsMoves > 0 )
					 this.typeMovement = 2; //Se movio hacia arriba.
				 else
					 this.typeMovement = 3; //Se movio hacia abajo.
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
		/*Actualizar la tablero de juego del juagdor teniendo en cuenta hacia donde se movio la pieza en blanco*/
		String board [][] = player.getBoard().getCurrentState();
		board[blankActualPos.getRow()][blankActualPos.getColumn()] = board[blankNewPos.getRow()][blankNewPos.getColumn()];
		board[blankNewPos.getRow()][blankNewPos.getColumn()] = "B";
		player.getBoard().setCurrentState( board );
		player.getBoard().setBlank( blankNewPos );
		player.getBoard().addMovement();
		
		this.idPlayerToUpd = player.getId() - 1;
		this.newBoard = false;
		this.newPlayer = false;
		this.newSuscriber = false;
		this.moveBoard = true;	
		
		return player;
		
	}
}
