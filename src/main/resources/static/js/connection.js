/**
 * 
 */
var TAG_ID_NAME = "NamePlayer"; // + No. de Jugador.
var TAG_ID_POINTS = "PointsPlayer"; // + No. de Jugador.
var TAG_ID_MOVEMENTS = "MovementsPlayer"; // + No. de Jugador.
var TAG_ID_BOARD = "BoardPlayer"; // + No. de Jugador.
var numBoards = 2;
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
	getForObject(null, "/api/game/new/", function(data) {});
	var socket = new SockJS('/hello');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) 
			{
				stompClient.subscribe('/topics/game', function(game) 
						{
							updateGame( JSON.parse(game.body) );
						});
			});
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
 * Se encarga de actualizar la interfaz gráfica cada ves que llega un mensaje desde el WebSocket.
 * @param game: Estado actual del juego.
 */
function updateGame( game )
{
	if( game.jugadores.length > numBoards )
	{
		addBoardsTemplates( );
	}
	if( game.newPlayer )
	{
		updateUserInfo( game );
	}
	if( game.newBoard )
	{
		updateUserBoard( game );		
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
function updateUserInfo( game )
{
	var player = game.jugadores[game.idPlayerToUpd];
	//console.log($("#NamePlayer"+player.id));
	$("#NamePlayer"+player.id).html( "Name: " + player.name );
	$("#PointsPlayer"+player.id).html( "Points: " + player.points );
	$("#MovementsPlayer"+player.id).html( "Movements: " + player.board.movements );
}


function updateUserBoard( game ){
	var player = game.jugadores[game.idPlayerToUpd];
	var position = 0;

	if( game.newBoard )
	{
		buildBoard( player );			
	}
	
	for( var i = 0 ; i < player.board.length; i++)
	{
		for( var j = 0 ; j < player.board.length; j++)
		{
			$( $("#BoardPlayer"+player.id+" .piece")[position]).text( player.board.currentState[i][j] );
			position++;
		}
	}
}

/**
 * Se encarga de construir y representar un tablero Taquin con elementos HTML.
 * @param player: Jugador que tiene el tabler Taquin a pintar.
 */
function buildBoard( player )
{
	var size = player.board.currentState.length;
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
	
	numBoards++;
	namePlayerField.attr( "id",TAG_ID_NAME+numBoards );
	pointsPlayerField.attr( "id",TAG_ID_POINTS+numBoards );
	movementsPlayerField.attr( "id", TAG_ID_MOVEMENTS+numBoards );
	boardPlayerField.attr( "id", TAG_ID_BOARD+numBoards );
	
	namePlayerField = templateCopy.find("#Name2");
	pointsPlayerField = templateCopy.find("#Point2");
	movementsPlayerField = templateCopy.find("#Movements2");
	boardPlayerField = templateCopy.find("#Board2");

	numBoards++;
	namePlayerField.attr( "id",TAG_ID_NAME+numBoards );
	pointsPlayerField.attr( "id",TAG_ID_POINTS+numBoards );
	movementsPlayerField.attr( "id", TAG_ID_MOVEMENTS+numBoards );
	boardPlayerField.attr( "id", TAG_ID_BOARD+numBoards );
	
	$("#TableroPrincipal").append(templateCopy);
}


function updatePlayer2(player){
	$("#p2-name").html( "Name: "+player.name );
	$("#p2-points").html( "Points: " + player.points );
	$("#p2-moves").html( "Moves: " + player.board.movements );
	var position = 0;
	for( var i = 0 ; i < size; i++){
		for( var j = 0 ; j < size; j++){
			$($("#player2 .piece")[position]).text( player.board.currentState[i][j] ); // falta aclarar que sea de p1 o de p2
			position++;
		}
	}
}
connect();