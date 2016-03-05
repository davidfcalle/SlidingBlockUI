/**
 * 
 */
var stompClient = null;
var size = 3;
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

function sendName() {
	var name = document.getElementById('name').value;
	stompClient.send("/app/hello", {}, JSON.stringify({
		'name' : name
	}));
}

function updateBoard(game){
	updatePlayer1(game.p1);
	updatePlayer2(game.p2);
}

function updatePlayer1(player){
	var position = 0;
	for( var i = 0 ; i < size; i++){
		for( var j = 0 ; j < size; j++){
			$($("#player1 .piece")[position]).text( player.board.currentState[i][j] ); // falta aclarar que sea de p1 o de p2
			position++;
		}
	}
}

function updatePlayer2(player){
	var position = 0;
	for( var i = 0 ; i < size; i++){
		for( var j = 0 ; j < size; j++){
			$($("#player2 .piece")[position]).text( player.board.currentState[i][j] ); // falta aclarar que sea de p1 o de p2
			position++;
		}
	}
}
connect();