package entities;

import java.util.ArrayList;
import java.util.List;


/**
 * @author David Suarez
 *
 */
public class Game {

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
		moveBoard = false;
		newPlayer = false;
	}

	public Game( Game other )
	{
		players = other.players;
		numPlayers = other.numPlayers;
		idPlayerToUpd = other.idPlayerToUpd;
		piece_1_ToMove = other.piece_1_ToMove;
		piece_2_ToMove = other.piece_2_ToMove;
		typeMovement = other.typeMovement;
		newBoard = other.newBoard;
		newSuscriber = other.newSuscriber;
		moveBoard = other.moveBoard;
		newPlayer = other.newPlayer;
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

	@Override
	public String toString() {
		return "Game [players=" + players + ", numPlayers=" + numPlayers + ", idPlayerToUpd=" + idPlayerToUpd
				+ ", typeMovement=" + typeMovement + ", piece_1_ToMove=" + piece_1_ToMove + ", piece_2_ToMove="
				+ piece_2_ToMove + ", newBoard=" + newBoard + ", newPlayer=" + newPlayer + ", newSuscriber="
				+ newSuscriber + ", moveBoard=" + moveBoard + "]";
	}


/**--------------------------------------------------------------Creation-----------------------------------------------------------------*/

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


/**--------------------------------------------------------------Assignment---------------------------------------------------------------*/

	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Verifica si el juagdor con id idPlayer existe y esta disponible.
	 * SI lo anterior se cumple se encarga retornar el tablero asociado
	 * al jugdor con idPlayer.
	 * @param idPlayer: Id del jugador al que se le buscara el tablero.
	 * @return playerCreated: Board asociado al jugador
	 * 						 Null si el jugador no existe o no esta disponible.
	 */
	 public Board getBoardByPlayer(Integer idPlayer) {
			if( idPlayer > this.numPlayers )
				return null;

			return this.players.get( idPlayer - 1 ).getBoard();
		}
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
	//	System.out.println( "boards: "+ this.numPlayers + "Players: " + this.players.size( ) + " id pedido: " + player.getId( ) );
		if( player.getId() > this.players.size() )
			return null;

		Player playerCreated = this.players.get( player.getId() - 1 );
		playerCreated.setId( player.getId() );
		playerCreated.setName( player.getName( ) );
		playerCreated.setPoints( player.getPoints() );
		playerCreated.getBoard().setMovements( 0 );
		this.numPlayers++;

		this.idPlayerToUpd = playerCreated.getId() - 1;
		this.newBoard = false;
		this.newPlayer = true;
		this.moveBoard = false;
		return playerCreated;
	}

	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Verifica si el juagdor con id idPlayer existe y esta disponible.
	 * SI lo anterior se cumple se encarga de asignar el nuevo tablero
	 * que viene en board a un Player con id idPlayer
	 * Por ultimo prende las banderas necesarias para informar a la vista que secciones debe actualizar.
	 * @param player: Jugador al que se le asignara un tablero.
	 * @return playerCreated: Una instancia de Player si se pudo crear y asignarle el tablero.
	 * 						 Null si el tablero pedido aun no existe o no esta disponible.
	 */
	public Player assignBoardToPlayer(Integer idPlayer, Board board )
	{
		if( idPlayer > this.players.size() )
			return null;

		Player player = this.players.get( idPlayer - 1 );
		player.setBoard( board );
		player.getBoard().setMovements( 0 );

		this.idPlayerToUpd = player.getId() - 1;
		this.newBoard = true;
		this.newPlayer = false;
		this.moveBoard = false;
		return player;
	}


/**--------------------------------------------------------------Movement---------------------------------------------------------------*/
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
	public boolean validateMovement( Integer idPlayer, Integer direction )
	{
		Integer row = this.players.get( idPlayer - 1 ).getBoard().getBlank().getRow();
		Integer column = this.players.get( idPlayer - 1 ).getBoard().getBlank().getColumn();
		int size = this.players.get( idPlayer - 1 ).getBoard().getCurrentState().length;

		int typeMovement = direction;
	//	boolean validMovement = true;
    	switch( typeMovement )
    	{
    	    case 0:
    	    	column++;
    	        break;
    	    case 1:
    	    	column--;
    	        break;
    	    case 2:
    	    	row--;
    	        break;
    	    case 3:
    	    	row++;
	    	    break;
        }
		
    	//System.out.println( "ROW: " + row + "COLUMN: " + column );
		if( row < 0 || row >= size || column < 0 || column >= size )
		{
			//System.out.println("MALOOOO");
			return false;
		}
		else
		{
			//System.out.println("BUENOOO");
			return true;
		}
	}
	
	
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

		//System.out.println("Va a mover esta: " + this.piece_1_ToMove + " y esta: " + this.piece_2_ToMove );
		if( blankNewPos.getRow() < 0 || blankNewPos.getRow() >= size || blankNewPos.getColumn() < 0 || blankNewPos.getColumn() >= size )
		{
			//System.out.println("CHAAO");
			return null;
		}
		else if( blankActualPos.getRow() == blankNewPos.getRow() )
		{
			//System.out.println( "ANTERIOR BLANCO C: " + player.getBoard().getBlank().getColumn() + " F: " +player.getBoard().getBlank().getRow());
			//System.out.println( "NUEVO BLANCO C: " + blankNewPos.getColumn() + " F: " +blankNewPos.getRow());
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
				//System.out.println("ESTE FUE INVALIDO: " + columnsMoves );
				return null;
			}
		}
		else if( blankActualPos.getColumn() == blankNewPos.getColumn() )
		{
			//System.out.println( "ANTERIOR BLANCO C: " + player.getBoard().getBlank().getColumn() + " F: " +player.getBoard().getBlank().getRow());
			//System.out.println( "NUEVO BLANCO C: " + blankNewPos.getColumn() + " F: " +blankNewPos.getRow());
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
				//System.out.println("ESTE FUE INVALIDO: " + rowsMoves );
				return null;
			}
		}
		else
		{
			//return null;
			//System.out.println("SIN OPCION");
		}

		/*Actualizar la tablero de juego del juagdor teniendo en cuenta hacia donde se movio la pieza en blanco*/
		String board [][] = player.getBoard().getCurrentState();
		//System.out.println("*********BOARD ANTES:*******");
		player.print( board );
		board[blankActualPos.getRow()][blankActualPos.getColumn()] = board[blankNewPos.getRow()][blankNewPos.getColumn()];
		board[blankNewPos.getRow()][blankNewPos.getColumn()] = "B";
		//System.out.println("*********BOARD DESPUES*****:");
		player.print( board );

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
