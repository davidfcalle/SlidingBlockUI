/**
 * @param TAG_ID_NAME: Variable estática y constante que contiene 
 * 					   como se se debe nombrar el ID para referecniar 
 * 					   el elemento HTML que muestra el nombre de cada jugador.
 */
var TAG_ID_NAME = "NamePlayer"; // + No. de Jugador.

/**
 * @param TAG_ID_POINTS: Variable estática y constante que contiene 
 * 					     como se se debe nombrar el ID para referecniar 
 * 					     el elemento HTML que muestra los puntos de cada jugador.
 */
var TAG_ID_POINTS = "PointsPlayer"; // + No. de Jugador.

/**
 * @param TAG_ID_MOVEMENTS: Variable estática y constante que contiene 
 * 					        como se se debe nombrar el ID para referecniar 
 * 					        el elemento HTML que muestra los movimientos que ha hehco de cada jugador.
 */
var TAG_ID_MOVEMENTS = "MovementsPlayer"; // + No. de Jugador.

/**
 * @param TAG_ID_BOARD: Variable estática y constante que contiene 
 * 					    como se se debe nombrar el ID para referecniar 
 * 					    el elmento HTML que muestra el tablero Taquín de cada jugador.
 */
var TAG_ID_BOARD = "BoardPlayer"; // + No. de Jugador.

/**
 * @param TAG_ID_BOARD: Variable estática y constante que contiene 
 * 					    como se se debe nombrar el ID para referecniar 
 * 					    el elmento HTML que muestra el tablero Taquín de cada jugador.
 */
var TAG_ID_SIZE_BOARD = "SizeBoardPlayer"; // + No. de Jugador.

/**
 * @param numBoards: Representa la cantidad de Tableros Taquín que se han pintado en la página.
 */
var numBoards = 2;

/**
 * @param stompClient: Controla la comunicación vía WebSocket.
 */
var stompClient = null;
var size = null;


function voltear()
{
	var c1 = $("#score-1");
	var c2 = $("#score-2");
	c1.css({position: "relative"});
	c2.css({position: "relative"});
	var posC1 = c1.offset();
	var posC2 = c2.offset();
	var correrC1 = posC2.left;
	var correrC2 = posC1.left + correrC1;
	c1.animate({right : "-="+correrC1 },1000, function(){alert("JAJA")});

	c2.animate({right : "+="+correrC2 },1000, function(){alert("C2")});
}


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
	getForObject(null, "/api/game/new/", function(data) {});
	
}

/**
 * Se encarga de desconectar el WebSocket.
 */
function disconnect() 
{
	if (stompClient != null) 
	{
		stompClient.disconnect();
	}
	
//	console.log("Disconnected");
}

/**
 * Se encarga de actualizar la interfaz gráfica por primera vez 
 * que se suscribe al topico por si ya contiene información.
 * @param game: Estado actual del juego.
 */
function firstUpdate( game )
{
	var player;
	var boardsTam = game.jugadores.length;
	//console.log(game)
	
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
/**
 * Se encarga de actualizar la interfaz gráfica cada ves que llega un mensaje desde el WebSocket.
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
	
	if( game.rebuildBoard )
	{	
		if( game.newBoard )
		{
			buildBoard( player );			
		}
		updateUserBoard( player, game.typeMovement );
	}
	
	if( game.moveBoard )
	{
		moveBoard(player, game.typeMovement, game.piece_1_ToMove, game.piece_2_ToMove);
	}	
	
	//--------------------------
/*	if( game.p1.board.currentState.length != size ){
		size = game.p1.board.currentState.length ;
		rebuildBoard( size );
	}
	updatePlayer1(game.p1);
	updatePlayer2(game.p1); // OJO, toca cambiarlo a p2, es solo para las pruebas.*/
}

/**
 * Se encarga de actualizar la información de un jugador: Nombre, Puntos y Movimientos.
 * @param game: Jugador al que se actualizará la información.
 */
function updateUserInfo( player )
{
	//console.log($("#NamePlayer"+player.id));
	$("#NamePlayer"+player.id).html( "Player " + player.id +": " + player.name );
	$("#PointsPlayer"+player.id).html( "Points: " + player.points );
	$("#MovementsPlayer"+player.id).html( "Movements: " + player.board.movements );
}


function updateUserBoard( player )
{
	var position = 0;
	var blankPos = player.board.blank;

	for( var i = 0 ; i < player.board.currentState.length; i++)
	{
		for( var j = 0 ; j < player.board.currentState.length; j++)
		{
			if( i == blankPos.row && j == blankPos.column )
			{
				$( $("#BoardPlayer"+player.id+" .piece")[position]).text( "BB" );
			}
			else
			{
				$( $("#BoardPlayer"+player.id+" .piece")[position]).text( player.board.currentState[i][j] );
			}
			position++;
		}
	}
}

/**
 * Se encarga de mover la pieza blanca a la posición indicada pro el usuario.
 * @param player: Jugador que posee el tablero a mover.
 * @param typeMovement: Indica si a pieza blanca se mueve en Fila(1) o COlumnas(0).
 */
function moveBoard( player, typeMovement, piece_1, piece_2 )
{
	var blankPos = player.board.blank;
	var blankPiece = $($("#BoardPlayer"+player.id+" .piece")[piece_1]);
	var blankPieceTo = $($("#BoardPlayer"+player.id+" .piece")[piece_2]);

	blankPiece.css({position: "relative"});
	blankPieceTo.css({position: "relative"});
	var posC1 = blankPiece.offset();
	var posC2 = blankPieceTo.offset();
	var correrC1 = posC2.left;
	var correrC2 = posC1.left + correrC1;
	blankPiece.animate({right : "-="+correrC1 },1000, function(){ });

	blankPieceTo.animate({right : "+="+correrC2 },1000, function(){ });
	
	switch( typeMovement ) 
	{
	    case 0:
	        //code block
	        break;
	    case 1:
	        //code block
	        break;
	    case 2:
	        //code block
	        break;
	    case 3:
	       // code block
	        break;

	}
}

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

/**
 * Se encarga de aregar 2 templates HTML para representar 
 * información del usuario y su tablero Taquín asociado.
 */
function addBoardsTemplates( addBoardsTemplates )
{
	var templateCopy = $( $( $("#board-template").html() ).clone() );
	
	//Se recuperan elementos HTML de cada jugador para cambiarles su ID (NamePlayer1 -> NamePlayerN).
	var namePlayerField = templateCopy.find("#Name1");
	//console.log(namePlayerField);
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

connect();