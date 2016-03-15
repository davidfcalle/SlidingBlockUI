//--------------------------------------------------------------Variables---------------------------------------------------------------
/**
 * @param board: Tablero Taquin que esta manejando actualmente el juagdor. 
 */
var board = new Object();

/**
 * @param playerPlay: Información del juagdor actual. 
 * 					  (Solo lo uso para en este ejemplo hacer mas cosas pero es suficiente con usar idPlayer)
 */
var playerPlay = new Object();


/**
 * @param idPlayer: Identificador unico del jugador actual. 
 * 				    Sirve para controlar que solo mueva el tablero que tiene asignado.
 */
var idPlayer;

/**
 * @param namePlayer: Nombre unico del jugador actual. 
 */
var namePlayer;

//--------------------------------------------------------------Funciones------------------------------------------------------------------

/**--------------------------------------------------------------Creacion-----------------------------------------------------------------*/
/**
 * Crea un nuevo objeto Board para representar la informacion del tablero Taquin.
 * Asignar la posicion de la pieza en blanco y asignar valores aleatorios a la matriz.
 * consumiendo el servicio de la URL "/api/board/new/", para esto debe enviar el objeto Board serializado como datos de la peticion HTTP.
 * Se debe usar SERIALIZACION pero basica.
 */
function generateMatrix( )
{
	var size = $("#sizeBoard").val()
	if(size < 2 )
		size = 2;
	
	board.movements = 0;
	board.blank = new Object ();
	board.blank.row = $("#blankRow").val() - 1;
	board.blank.column = $("#blankColumn").val() - 1;

	board.currentState = new Array( size );
	for (var i = 0; i < size; i++)
		board.currentState[i] = new Array( size );

	for( var i = 0 ; i < size; i++)
	{
		for( var j = 0 ; j < size; j++)
		{
			board.currentState[i][j] = Math.round ( Math.random( ) * size );
		}
	}
	postForObject( board, "/api/board/new/", function(data){}, function(data){})
	//showGeneratedBoard( );
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Crea un nuevo objeto Player para representar la informacion del jugador actual.
 * E informar nombre y id para identicarse como nuevo jugador consumiendo el servicio
 * de la URL "/api/player/new/", para esto debe enviar el objeto Player serializado como datos de la peticion HTTP.
 * Se debe usar SERIALIZACION o usar el metodo basico generatePlayer.
 */
function generatePlayerPro( )
{
	createPlayer();
	//console.log( player );
	$.ajax({
		type : "post",
		url : "/api/player/new/",
		data : JSON.stringify( playerPlay ),
	    contentType: 'application/json; charset=utf-8',
		success : function(data){
			if ( data.name != undefined )
			{	
				playerPlay.board = data.board;
				board = data.board;
				showGeneratedBoard( );
			}
			else
			{
				alert("Cannot create player because there isn't board challenge yet!!");
			}
		},
		error :function(data)
		{
		}
	});
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Informa nombre y id para identificarse como nuevo jugador ante la vista
 * consumiendo el servicio de la URL "/api/player/{idPlayer}/new/{namePlayer}/", 
 * donde en {idPlayer} se debe colocar el id unico del jugador y en 
 * {namePlayer} el nombre a asignar al jugador.
 * Ejemplo: url: /api/player/1/new/dadsez/
 * 			Se identifica ante la vista como jugador No. 1 con el nombre dadsez.
 * NO se usa SERIALIZACION.
 */
function generatePlayer( )
{
	createPlayer();
	var url = "/api/player/"+idPlayer+"/new/"+namePlayer+"/";

	$.ajax(
	{
		type : "post",
		url : url,
	    contentType: 'application/json; charset=utf-8',
		success : function(data){
			if ( data.name == undefined )
			{
				alert("Cannot create player because there isn't board challenge yet!!");
			}
			else
			{
				//Solo se usa para actalizar el tablero del Cliente JS. Se puede quitar no es relevante.
				playerPlay.board = data.board;
				board = data.board;
				showGeneratedBoard( );
			}
		},
		error :function(data)
		{
		}
	});
}

function createPlayer( )
{
	var player = new Object();
	idPlayer = $("#playerId").val();
	playerPlay.id = idPlayer;
	
	namePlayer = $("#playerName").val();
	playerPlay.name = namePlayer;
	playerPlay.points = 0;
	playerPlay.board = new Object();

	$("#playerIdLabel").text( "Id Player: " + idPlayer );
	$("#playerNameLabel").text( "Player " + idPlayer + ": " + namePlayer );
}

/**--------------------------------------------------------------Movimiento---------------------------------------------------------------*/

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Mueve la pieza en blanco del tablero Taquin hacia la derecha.
 * Para esto consume el servicio de la URL "/api/player/{idPlayer}/board/move/right"
 * donde en {idPlayer} se debe colocar el id unico del jugador con el fin de saber que tablero editar.
 * Ejemplo: url: /api/player/1/board/move/right/
 * 			Se movera la pieza blanca hacia la derecha del tablero del jugador No. 1.
 * NO se usa SERIALIZACION.
 */
function moveBoardToRight( )
{
	var url = "/api/player/"+idPlayer+"/board/move/right/";
	moveBoard( url );
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Mueve la pieza en blanco del tablero Taquin hacia la izquierda.
 * Para esto consume el servicio de la URL "/api/player/{idPlayer}/board/move/left"
 * donde en {idPlayer} se debe colocar el id unico del jugador con el fin de saber que tablero editar.
 * Ejemplo: url: /api/player/{1}/board/move/left/
 * 			Se movera la pieza blanca hacia la izquierda del tablero del jugador No. 1.
 * NO se usa SERIALIZACION.
 */
function moveBoardToLeft( )
{
	var url = "/api/player/"+idPlayer+"/board/move/left/";
	moveBoard( url );
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Mueve la pieza en blanco del tablero Taquin hacia arriba.
 * Para esto consume el servicio de la URL "/api/player/{idPlayer}/board/move/up"
 * donde en {idPlayer} se debe colocar el id unico del jugador con el fin de saber que tablero editar.
 * Ejemplo: url: /api/player/{1}/board/move/up/
 * 			Se movera lapieza blanca hacia arriba del tablero del jugador No. 1.
 * NO se usa SERIALIZACION.
 */
function moveBoardToUp( )
{
	var url = "/api/player/"+idPlayer+"/board/move/up/";
	moveBoard( url );
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Mueve la pieza en blanco del tablero Taquin hacia abajo.
 * Para esto consume el servicio de la URL "/api/player/{idPlayer}/board/move/down"
 * donde en {idPlayer} se debe colocar el id unico del jugador con el fin de saber que tablero editar.
 * Ejemplo: url: /api/player/{1}/board/move/down/
 * 			Se movera la pieza blanca hacia abajo del tablero del jugador No. 1.
 * NO se usa SERIALIZACION.
 */
function moveBoardToDown( )
{
	var url = "/api/player/"+idPlayer+"/board/move/down/";
	moveBoard( url );	
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Mueve la pieza en blanco del tablero Taquin hacia la direccion indicada por la url.
 * NO se usa SERIALIZACION.
 */
function moveBoard( url )
{
	$.ajax(
		{
			type : "post",
			url : url,
			contentType: 'application/json; charset=utf-8',
			success : function(data){
			if ( data.name != undefined )
			{
				//Solo se usa para actualizar el tablero del Cliente JS. Se puede quitar no es relevante.
				playerPlay.board = data.board;
				board = data.board;				
				showGeneratedBoard( );
			}
			else
			{
				alert("Invalid Move!!");
			}},
			error :function(data)
			{
			}
		});	
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Mueve la pieza en blanco del tablero Taquin hacia la izquierda.
 * Para esto consume el servicio de la URL "/api/player//board/move/" y se debe 
 * enviar como datos de la peticion HTTP un objeto Player, serializado, 
 * donde su objeto Piece de Board este moificado de tal manera que represente el
 * movimiento hacia la izquierda.
 * Para este caso restamos 1 en la coordenada columna ya que al moverse hacia 
 * la izquierda la posicion de la pieza en blanco (Blank) disminuye en columna.
 * Se usa SERIALIZACION.
 */
function moveBoardToLeftPro( )
{
	playerPlay.board.blank.column--;
	//console.log( player );
	moveBoardPro( );
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Mueve la pieza en blanco del tablero Taquin hacia la derecha.
 * Para esto consume el servicio de la URL "/api/player//board/move/" y se debe 
 * enviar como datos de la peticion HTTP un objeto Player, serializado, 
 * donde su objeto Piece de Board este moificado de tal manera que represente el
 * movimiento hacia la derecha.
 * Para este caso sumamos 1 en la coordenada columna ya que al moverse hacia 
 * la derecha la posicion de la pieza en blanco (Blank) aumenta en columna.
 * Se usa SERIALIZACION.
 */
function moveBoardToRightPro( )
{
	playerPlay.board.blank.column++;
	//console.log( player );
	moveBoardPro( );
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Mueve la pieza en blanco del tablero Taquin hacia arriba.
 * Para esto consume el servicio de la URL "/api/player//move/" y se debe 
 * enviar como datos de la peticion HTTP un objeto Player, serializado, 
 * donde su objeto Piece de Board este moificado de tal manera que represente el
 * movimiento hacia arriba.
 * Para este caso restamos 1 en la coordenada fila ya que al moverse hacia 
 * arriba la posicion de la pieza en blanco (Blank) aumenta en fila.
 * Se usa SERIALIZACION.
 */
function moveBoardToUpPro( )
{
	playerPlay.board.blank.row--;
	//console.log( player );
	moveBoardPro( );
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Mueve la pieza en blanco del tablero Taquin hacia abajo.
 * Para esto consume el servicio de la URL "/api/player//move/" y se debe 
 * enviar como datos de la peticion HTTP un objeto Player, serializado, 
 * donde su objeto Piece de Board este moificado de tal manera que represente el
 * movimiento hacia abajo.
 * Para este caso restamos 1 en la coordenada fila ya que al moverse hacia 
 * abajo la posicion de la pieza en blanco (Blank) aumenta en fila.
 * Se usa SERIALIZACION.
 */
function moveBoardToDownPro( )
{
	playerPlay.board.blank.row++;
	//console.log( player );
	moveBoardPro( );
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Mueve la pieza en blanco del tablero Taquin hacia 
 * la direccion indicada en blank, atributo del objeto playPlayer.
 * Se usa SERIALIZACION.
 */
function moveBoardPro( )
{
	$.ajax({
		type : "post",
		url : "/api/board/move/",
		data : JSON.stringify(playerPlay),
	    contentType: 'application/json; charset=utf-8',
		success : function(data){
			if( data.name != undefined )
			{
				board = data.board;
				playerPlay.points = data.points;
				playerPlay.board = data.board;
				showGeneratedBoard( data.board );
			}
			else
			{
				alert(" Inavalid Move!!");
			}
		},
		error :function(data){
		}
	});	
}

/**
 * Se encarga de construir y representar un tablero Taquin con elementos HTML.
 */
function showGeneratedBoard( )
{
	var size = board.currentState.length;
	$("#blankRow").val( board.blank.row + 1 );
	$("#blankColumn").val( board.blank.column + 1 );
	$("#sizeBoard").val( size );
	$("#playerMovements").text( "Movements: " + board.movements );
	$("#blankColumn").attr( {readOnly:"readOnly"} );
	$("#blankRow").attr( {readOnly:"readOnly"} );
	$("#sizeBoard").attr( {readOnly:"readOnly"} );

	$("#board .piece").remove();
	for( var i = 0 ; i < size; i++)
	{
		for( var j = 0 ; j < size; j++)
		{
			var templateCopy = $( $( $("#piece-template").html() ).clone() );
			templateCopy.css( { minWidth : ( (100 / size ) - 1 +"%" ), minHeight : 70 / size + "vh"  } );
			
			if( i == board.blank.row && j == board.blank.column )
				templateCopy.text( "B" );
			else
				templateCopy.text( board.currentState[i][j] );
			
			$( "#board" ).append( templateCopy );
		}
	}
}