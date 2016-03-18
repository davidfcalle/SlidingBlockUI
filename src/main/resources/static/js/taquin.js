//--------------------------------------------------------------Variables---------------------------------------------------------------
/**
 * @param TAG_ID_NAME: Variable estatica y constante que contiene 
 * 					   como se se debe nombrar el ID para referecniar 
 * 					   el elemento HTML que muestra el nombre de cada jugador.
 */
var TAG_ID_NAME = "NamePlayer"; // + No. de Jugador.

/**
 * @param TAG_ID_POINTS: Variable estatica y constante que contiene 
 * 					     como se se debe nombrar el ID para referecniar 
 * 					     el elemento HTML que muestra los puntos de cada jugador.
 */
var TAG_ID_POINTS = "PointsPlayer"; // + No. de Jugador.

/**
 * @param TAG_ID_MOVEMENTS: Variable estatica y constante que contiene 
 * 					        como se se debe nombrar el ID para referecniar 
 * 					        el elemento HTML que muestra los movimientos que ha hehco de cada jugador.
 */
var TAG_ID_MOVEMENTS = "MovementsPlayer"; // + No. de Jugador.

/**
 * @param TAG_ID_BOARD: Variable estatica y constante que contiene 
 * 					    como se se debe nombrar el ID para referecniar 
 * 					    el elmento HTML que muestra el tablero Taquin de cada jugador.
 */
var TAG_ID_BOARD = "BoardPlayer"; // + No. de Jugador.

/**
 * @param TAG_ID_BOARD: Variable estatica y constante que contiene 
 * 					    como se se debe nombrar el ID para referecniar 
 * 					    el elmento HTML que muestra el tablero Taquin de cada jugador.
 */
var TAG_ID_SIZE_BOARD = "SizeBoardPlayer"; // + No. de Jugador.

/**
 * @param numBoards: Representa la cantidad de Tableros Taquin que se han pintado en la pagina.
 */
var numBoards = 2;

/**
 * @param stompClient: Controla la comunicacion via WebSocket.
 */
var stompClient = null;



//--------------------------------------------------------------Functions------------------------------------------------------------------

/**--------------------------------------------------------------Comunication-------------------------------------------------------------*/

//-------------------------------------------------------------------------------------------------------------------------------------
/**
 * Establece la comunicacion via WebSocket en "/hello".
 * Suscribiendose al topico "/topics/game", donde 
 * se notificaran las respectivas actualizaciones a la vista
 */
function connect() 
{
	var socket = new SockJS('/hello');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) 
			{
				stompClient.subscribe('/topics/game', function(game) 
						{
							var game = JSON.parse(game.body);
							if( game.newSuscriber )
								firstUpdate( game );
							else
								updateGame( game );
						});
			});
	//postForObject(null, "/api/game/new/", function(data) {});
	
}

//-------------------------------------------------------------------------------------------------------------------------------------
/**
 * Se encarga de desconectar el WebSocket.
 */
function disconnect() 
{
	if (stompClient != null) 
	{
		stompClient.disconnect();
	}
	
}

/**--------------------------------------------------------------Creation-----------------------------------------------------------------*/

//-------------------------------------------------------------------------------------------------------------------------------------
/**
 * Se encarga de construir y representar un tablero Taquin con elementos HTML.
 * @param player: Jugador que tiene el tabler Taquin a pintar.
 */
function buildBoard( player )
{
	var size = player.board.currentState.length;
	$("#SizeBoardPlayer"+player.id).html( "Size: " + size+" X "+size );
	$("#BoardPlayer"+player.id+" .piece").remove();
	for( var i = 0 ; i < size * size ; i++)
	{
		var templateCopy = $( $( $("#piece-template").html() ).clone() );
		templateCopy.css( { minWidth : ( (100 / size ) - 1 +"%" ), minHeight : 70 / size + "vh"  } );
		$("#BoardPlayer"+player.id).append(templateCopy);
	}
}

//-------------------------------------------------------------------------------------------------------------------------------------
/**
 * Se encarga de aregar 2 templates HTML para representar 
 * informacion del usuario y su tablero Taquin asociado.
 */
function addBoardsTemplates( )
{
	var templateCopy = $( $( $("#board-template").html() ).clone() );
	
	//Se recuperan elementos HTML de cada jugador para cambiarles su ID (NamePlayer1 -> NamePlayerN).
	var namePlayerField = templateCopy.find("#Name1");
	var pointsPlayerField = templateCopy.find("#Points1");
	var movementsPlayerField = templateCopy.find("#Movements1");
	var boardPlayerField = templateCopy.find("#Board1");
	var sizeBoardPlayerField = templateCopy.find("#Size1");
	
	numBoards++;
	namePlayerField.attr( "id",TAG_ID_NAME+numBoards );
	pointsPlayerField.attr( "id",TAG_ID_POINTS+numBoards );
	movementsPlayerField.attr( "id", TAG_ID_MOVEMENTS+numBoards );
	boardPlayerField.attr( "id", TAG_ID_BOARD+numBoards );
	sizeBoardPlayerField.attr( "id", TAG_ID_SIZE_BOARD+numBoards );
	
	//Se recuperan elementos HTML de cada jugador para cambiarles su ID (NamePlayer2 -> NamePlayerN).
	namePlayerField = templateCopy.find("#Name2");
	pointsPlayerField = templateCopy.find("#Point2");
	movementsPlayerField = templateCopy.find("#Movements2");
	boardPlayerField = templateCopy.find("#Board2");
	sizeBoardPlayerField = templateCopy.find("#Size2");

	numBoards++;
	namePlayerField.attr( "id",TAG_ID_NAME+numBoards );
	pointsPlayerField.attr( "id",TAG_ID_POINTS+numBoards );
	movementsPlayerField.attr( "id", TAG_ID_MOVEMENTS+numBoards );
	boardPlayerField.attr( "id", TAG_ID_BOARD+numBoards );
	sizeBoardPlayerField.attr( "id", TAG_ID_SIZE_BOARD+numBoards );
	
	$("#TableroPrincipal").append(templateCopy);
}

/**--------------------------------------------------------------ViewActualization-------------------------------------------------------*/

//-------------------------------------------------------------------------------------------------------------------------------------
/**
 * Se encarga de actualizar las respectivas secciones informadas por game
 * a traves de una notificacion via WebSocket en el topico.
 * @param game: Estado actual del juego.
 */
function updateGame( game )
{
	var player = game.jugadores[game.idPlayerToUpd];
	
	if( game.jugadores.length > numBoards )
	{
		addBoardsTemplates( );
	}
	
	if( game.newPlayer )
	{
		updateUserInfo( player );
	}
	
	if( game.newBoard )
	{	
		buildBoard( player );			
		updateUserBoard( player );
	}
	
	if( game.moveBoard )
	{
		movePieceOnBoard(player, game.typeMovement, game.piece_1_ToMove, game.piece_2_ToMove);
	}	
}

//-------------------------------------------------------------------------------------------------------------------------------------
/**
 * Se encarga de actualizar la seccion de informacion 
 * de un jugador: Nombre, Puntos y Movimientos.
 * @param game: Informacion con la que se actualizara esta seccion.
 */
function updateUserInfo( player )
{
	$("#NamePlayer"+player.id).html( "Player " + player.id +": " + player.name );
	$("#PointsPlayer"+player.id).html( "Points: " + player.points );
}

//-------------------------------------------------------------------------------------------------------------------------------------
/**
 * Se encarga de actualizar la seccion donde se encuentra el tablero Taquin
 * @param game: Informacion con la que se actualizara esta seccion.
 */
function updateUserBoard( player )
{
	var position = 0;
	var blankPos = player.board.blank;
	
	$("#MovementsPlayer" + player.id ).text( "Movements: " + player.board.movements );
	for( var i = 0 ; i < player.board.currentState.length; i++)
	{
		for( var j = 0 ; j < player.board.currentState.length; j++)
		{			
			if( i == blankPos.row && j == blankPos.column )
			{
				$( $("#BoardPlayer"+player.id+" .piece")[position]).css( "background-color", "black" );
				$( $("#BoardPlayer"+player.id+" .piece")[position]).text( "B" );
			}
			$( $("#BoardPlayer"+player.id+" .piece")[position]).text( player.board.currentState[i][j] );
			position++;
		}
	}
}

//-------------------------------------------------------------------------------------------------------------------------------------
/**
 * Se encarga de actualizar la pieza blanca a la nueva posicion indicada por newPosition.
 * @param player: Jugador que tiene el tablero donde se actulizara la pieza blanca.
 * @param currentPosition: Posicion actual de la pieza blanca.
 * @param newPosition: Nueva posicion de la pieza blanca.
 */
function updateBlankPosition(  currentPieceBlank,  newPieceBlank )
{	
	currentPieceBlank.css( "background-color", ""  );
	currentPieceBlank.css( { position: "" } );
	currentPieceBlank.css( { top: "" } );
	currentPieceBlank.css( { right: "" } );
	currentPieceBlank.text( newPieceBlank.text( ) );

	newPieceBlank.css( "background-color", "black"  );
	newPieceBlank.css( { position: "" } );
	newPieceBlank.css( { top: "" } );
	newPieceBlank.css( { right: "" } );
	newPieceBlank.text( "B" );
}

//-------------------------------------------------------------------------------------------------------------------------------------
/**
 * Se encarga de actualizar la interfaz grafica por primera vez 
 * que se suscribe al topico por si ya contiene informacion.
 * @param game: Estado actual del juego.
 */
function firstUpdate( game )
{
	var player;
	var boardsTam = game.jugadores.length;
	
	for( var i = 0; i < boardsTam; i++ )
	{
		if( game.jugadores.length > numBoards )
		{
			addBoardsTemplates( );
		}
		
		player = game.jugadores[i]
		updateUserInfo( player );
		buildBoard( player );			
		updateUserBoard( player, 0 );
	}
	
	getForObject( null, "/api/game/endUpdate/", function(){} );
}

/**--------------------------------------------------------------Movement---------------------------------------------------------------*/

//-------------------------------------------------------------------------------------------------------------------------------------
function moveRightAndLeft( player, blankPiece, piece, right )
{
	blankPiece.css({position: "relative"});
	piece.css({position: "relative"});

	var animationSize = blankPiece.width();
	var currentPos = blankPiece;
	var	newPos = piece;
	
	if( !right )
	{
		currentPos = piece;
		newPos = blankPiece;
	}
		
	blankPiece.animate( { right : "-="+animationSize }, 70, function(){ });
	piece.animate( { right : "+="+animationSize }, 70, function(){ updateBlankPosition(  currentPos, newPos ); } );

}

//-------------------------------------------------------------------------------------------------------------------------------------
function moveUpAndDown( player, blankPiece, piece, up )
{
	blankPiece.css( {position: "relative" } );
	piece.css( {position: "relative" } );

	var animationSize = blankPiece.height()
	var currentPos = blankPiece;
	var	newPos = piece;
	
	if( !up )
	{
		currentPos = piece;
		newPos = blankPiece;
	}
	
	blankPiece.animate( { top : "+="+animationSize }, 70, function(){ });
	piece.animate( { top : "-="+animationSize }, 70, function(){ updateBlankPosition(  currentPos, newPos ); });
}

//-------------------------------------------------------------------------------------------------------------------------------------
/**
 * Se encarga de intercmbiar las piezas (en las posiciones piece_1 y piece_2).
 * Simulando el movimiento de la pieza blanca a la direccion indicada.
 * @param player: Jugador que posee el tablero donde se moera la pieza blanca.
 * @param typeMovement: Indica la direccion hacia donde se movera la pieza blanca.
 *		   0: Hacia la derecha. 
 *		   1: Hacia la izquierda.
 *		   2: Hacia arriba.
 *		   3: Hacia abajo.
 * @param piece_1: Posicion de la pieza blanca actual.
 * @param piece_2: Posicion de la pieza hacia donde se movera la pieza blanca.
 */
function movePieceOnBoard( player, typeMovement, piece_1, piece_2 )
{	
	var board = player.board;
	var blankPiece = $( $( "#BoardPlayer" + player.id + " .piece" )[piece_1] );
	var blankPieceTo = $( $( "#BoardPlayer" + player.id + " .piece" )[piece_2] );
	$("#MovementsPlayer" + player.id ).text( "Movements: " + player.board.movements );

	switch( typeMovement ) 
	{
	    case 0:
	        moveRightAndLeft( player, blankPiece, blankPieceTo, true );
	        break;
	    case 1:
	    	moveRightAndLeft( player, blankPieceTo, blankPiece, false );
	        break;
	    case 2:
	        moveUpAndDown( player, blankPiece, blankPieceTo, true );
	        break;
	    case 3:
	    	moveUpAndDown( player, blankPieceTo, blankPiece, false );
	        break;
	}
}

connect();