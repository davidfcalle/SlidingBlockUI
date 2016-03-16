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


//--------------------------------------------------------------Functions------------------------------------------------------------------

/**--------------------------------------------------------------Creation-----------------------------------------------------------------*/
/**
 * Crea un nuevo objeto Board para representar la informacion del tablero Taquin.
 * Asignar la posicion de la pieza en blanco y asignar valores aleatorios a la matriz.
 * consumiendo el servicio de la URL "/api/board/new/", para esto debe enviar el objeto Board serializado como datos de la peticion HTTP.
 * Se debe usar SERIALIZACION pero basica.
 */
function generateMatrix( )
{
	var create = true;
	var size = $("#sizeBoard").val()
	var row = $("#blankRow").val() - 1;
	var column = $("#blankColumn").val() - 1;
	var idPlayer = $("#idPlayerToChallenge").val();
	console.log( "r: "+ row + "c: "+ column +"S: " +size );
	
	if( size < 2 )
	{
		alert( "Board's size cannot be less than two!!" );
		create = false;
	}
	
	if ( row < 0 || row >= size || column < 0 || column >= size )
	{
		alert( "Blank piece's positon invalid!! ")
		create = false;
	}

	if( create )
	{
		board.movements = 0;	
		board.blank = new Object ();
		board.blank.row = row;
		board.blank.column = column;
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
	}
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
	if ( createPlayer() )
	{	
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
				
				$("#playerId").val( idPlayer );
				$("#playerName").val( namePlayer );
				$("#playerId").attr( {readOnly:"readOnly"} );
				$("#playerName").attr( {readOnly:"readOnly"} );
				$( "#btnCreatePlayer").attr( "disabled", "disabled" );
				$( "#btnCreatePlayer").css( "background-color", "#gray" );
				$( "#btnCreateMatrix").attr( "disabled", "disabled" );
				$( "#btnCreateMatrix").css( "background-color", "gray" );
				$( "#control").css( { display: "" } );	
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
	if ( createPlayer() )
	{
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
					
					$("#playerId").attr( {readOnly:"readOnly"} );
					$("#playerName").attr( {readOnly:"readOnly"} );
					$( "#btnCreatePlayer").attr( "disabled", "disabled" );
					$( "#btnCreatePlayer").css( "background-color", "#gray" );
					$( "#btnCreateMatrix").attr( "disabled", "disabled" );
					$( "#btnCreateMatrix").css( "background-color", "gray" );
					$( "#control").css( { display: "" } );	
					$( "#control").css( { display: "" } );	
				}
			},
			error :function(data)
			{
			}
		});
	}
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
function createPlayer( )
{
	var player = new Object();
	
	$("#playerId").removeAttr( "readOnly" );
	$("#playerName").removeAttr( "readOnly" );

	idPlayer = $("#playerId").val();
	namePlayer = $("#playerName").val();
	
	if ( idPlayer <= 0 || idPlayer == undefined )
	{
		alert( "Sorry, we don't accept negative or zero players :C Only positive people! :D");
		return false;
	}
	
	if ( namePlayer.length == 0 || namePlayer == undefined )
	{
		alert( "Please, write your player name!! ");
		return false;
	}
	
	playerPlay.id = idPlayer;
	playerPlay.name = namePlayer;
	playerPlay.points = 0;
	playerPlay.board = new Object();
			
	return true;
}

/**--------------------------------------------------------------Assignment---------------------------------------------------------------*/

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Informa id del jugador al que le asignara el tablero reto
 * consumiendo el servicio de la URL "/api/player/{idPlayer}/challenge/new", 
 * donde en {idPlayer} se debe colocar el id unico del jugador a retar
 * Ejemplo: url: /api/player/3/challenge/new/
 * 			Envia un nuevo tablero reto al jgador  No. 3.
 * NO se usa SERIALIZACION.
 */
function startChallenge( )
{	
	$("#labelSize").text( "Challenge size:" );	
	$("#labelBlankPiece").text( "Challenge blank piece:" );
	
	$("#sizeBoard").val( 0 )
	$("#blankRow").val( 0 )
	$("#blankColumn").val( 0 ) 
	$( "#sizeBoard" ).removeAttr( "readOnly" );	
	$( "#blankColumn" ).removeAttr( "readOnly" );
	$( "#blankRow" ).removeAttr( "readOnly" );
	$( "#btnChallenge ").css( "display", "");
	$("#playerToChallenge").css( "display", "");
}

//-----------------------------------------------------------------------------------------------------------------------------------------------
/**
 * Informa id del jugador al que le asignara el tablero reto
 * consumiendo el servicio de la URL "/api/player/{idPlayer}/challenge/new", 
 * donde en {idPlayer} se debe colocar el id unico del jugador a retar
 * Ejemplo: url: /api/player/3/challenge/new/
 * 			Envia un nuevo tablero reto al jgador  No. 3.
 * NO se usa SERIALIZACION.
 */
function challengePlayer( )
{
	var create = true;
	var size = $("#sizeBoard").val()
	var row = $("#blankRow").val() - 1;
	var column = $("#blankColumn").val() - 1;
	var idPlayerStr = $("#idPlayerToChallenge").val();
	
	if( idPlayerStr === "N" )
	{
		var boardAux = board;
		generateMatrix( );
		board = boardAux;
		$("#labelSize").text( "Board Size :" );	
		$("#labelBlankPiece").text( "Blank piece:" );
		$("#blankRow").val( board.blank.row + 1 );
		$("#blankColumn").val( board.blank.column + 1 );
		$("#sizeBoard").val( board.currentState.length );	
		$("#sizeBoard").attr( {readOnly:"readOnly"} );	
		$("#blankColumn").attr( {readOnly:"readOnly"} );
		$("#blankRow").attr( {readOnly:"readOnly"} );
		$( "#btnChallenge ").css( "display", "none");
		$("#playerToChallenge").css( "display", "none");
	}	
	else
	{	
		var idPlayer = parseInt( idPlayerStr );
		if( size < 2 )
		{
			alert( "Challenge board's size cannot be less than two!!" );
			create = false;
		}
		console.log( "r: "+ row + "c: "+ column +"S: " +size );
		if ( row < 0 || row >= size || column < 0 || column >= size )
		{
			alert( "Challenge blank piece's positon invalid!! ")
			create = false;
		}
		
		if ( idPlayer <= 0 || idPlayer == undefined )
		{
			alert( "Sorry, we don't have negative or zero players :C Only positive people! :D");
			create = false;
		}
			
		if( create )
		{
			var boardChallenge = new Object( );
			boardChallenge.movements = 0;	
			boardChallenge.blank = new Object ( );
			boardChallenge.blank.row = row;
			boardChallenge.blank.column = column;
			boardChallenge.currentState = new Array( size );
			
			for (var i = 0; i < size; i++)
				boardChallenge.currentState[i] = new Array( size );
		
			for( var i = 0 ; i < size; i++)
			{
				for( var j = 0 ; j < size; j++)
				{
					boardChallenge.currentState[i][j] = Math.round ( Math.random( ) * size );
				}
			}
			postForObject( boardChallenge, "/api/player/"+idPlayer+"/challenge/", function(data){}, function(data){});
			$("#labelSize").text( "Board Size :" );	
			$("#labelBlankPiece").text( "Blank piece:" );
			$("#blankRow").val( board.blank.row + 1 );
			$("#blankColumn").val( board.blank.column + 1 );
			$("#sizeBoard").val( board.currentState.length );	
			$("#sizeBoard").attr( {readOnly:"readOnly"} );	
			$("#blankColumn").attr( {readOnly:"readOnly"} );
			$("#blankRow").attr( {readOnly:"readOnly"} );
			$( "#btnChallenge ").css( "display", "none");
			$("#playerToChallenge").css( "display", "none");
		}
	}
}


/**--------------------------------------------------------------Movement---------------------------------------------------------------*/

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
			
			templateCopy.css( "backgroun-color", "" );
			templateCopy.css( { minWidth : ( (100 / size ) - 1 +"%" ), minHeight : 70 / size + "vh"  } );
			
			if( i == board.blank.row && j == board.blank.column )
			{
				templateCopy.css( "background-color", "black" );
				templateCopy.text( " " );
			}
			else
				templateCopy.text( board.currentState[i][j] );
			
			$( "#board" ).append( templateCopy );
		}
	}
}

function guau( )
{
	moveBoardToRight();
	moveBoardToRight();
	moveBoardToLeft();
	moveBoardToDown();
	moveBoardToRight();
	moveBoardToUp();
	moveBoardToLeft();
	moveBoardToDown();	
	moveBoardToLeft();
	moveBoardToLeft();
	moveBoardToUp();
	moveBoardToDown();
	moveBoardToDown();
	moveBoardToDown();
	moveBoardToRight();
	moveBoardToUp();
	moveBoardToUp();
	moveBoardToUp();
}