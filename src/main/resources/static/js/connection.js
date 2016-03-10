/**
 * 
 */
var stompClient = null;
var size = null;

function setConnected(connected) {
	console.log("contectado")
}

function connect() {
	var socket = new SockJS('/hello');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topics/game', function(game) {
			updateBoard( JSON.parse(game.body) );
		});
	});
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function rebuildBoard( size ){
	$(".piece").remove();
	for( var i = 0 ; i < size * size ; i++){
		var templateCopy = $( $( $("#piece-template").html() ).clone() );
		templateCopy.css( { minWidth : ( (100 / size ) - 1 +"%" ), minHeight : 70 / size + "vh"  } );
		var templateCopyP2 = $( $( templateCopy ).clone() ); // copia para el jugador 2
		$("#player1").append(templateCopy);
		$("#player2").append(templateCopyP2);
	}
	// para el tamanio de uno de los jugadores, 
}
function updateBoard(game){
	if( game.p1.board.currentState.length != size ){
		size = game.p1.board.currentState.length ;
		rebuildBoard( size );
	}
	updatePlayer1(game.p1);
	updatePlayer2(game.p1); // OJO, toca cambiarlo a p2, es solo para las pruebas.
}

function updatePlayer1(player){
	var position = 0;
	$("#p1-name").html( "Name: " + player.name );
	$("#p1-points").html( "Points: " + player.points );
	$("#p1-moves").html( "Moves: " + player.board.movements );
	for( var i = 0 ; i < size; i++){
		for( var j = 0 ; j < size; j++){
			$( $("#player1 .piece")[position]).text( player.board.currentState[i][j] ); // falta aclarar que sea de p1 o de p2
			position++;
		}
	}
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