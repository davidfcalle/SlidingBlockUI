var playerPlay = new Object();

function generateMatrix( )
{
	var board = new Object();
	var size = Math.round (Math.random( ) * 10 );
	if(size < 2 )
		size = 2;
	
	board.movements = 0;
	board.blank = new Object ();
	board.blank.row = 0;
	board.blank.column = Math.round ( Math.random( ) * (size-1) );

	board.currentState = new Array( size );
	for (var i = 0; i < size; i++)
		board.currentState[i] = new Array( size );

	for( var i = 0 ; i < size; i++)
	{
		for( var j = 0 ; j < size; j++)
		{
			board.currentState[i][j] = Math.round ( Math.random( ) %size);
		}
	}
	console.log(board);
	postForObject( board, "/api/board/new/", function(data){}, function(data){})
}

function generatePlayer( )
{
	var player = new Object();
	player.id = 0;
	player.name = "PEPE";
	player.points = 0;
	player.board = new Object();
	//console.log( player );
	$.ajax({
		type : "post",
		url : "/api/player/new/",
		data : JSON.stringify(player),
	    contentType: 'application/json; charset=utf-8',
		success : function(data){
			playerPlay.id = data.id;
			playerPlay.name = data.name;
			playerPlay.points = data.points;
			playerPlay.board = data.board;
			console.log("JUGADOR: " + playerPlay.name);
		},
		error :function(data){
			alert("LLL");
		}
	});
	//postForObject( player, "", function(data){ playerPlay = data ;}, function(data){})
	
}

function moveBoard( )
{
	playerPlay.board.blank.row++;
	//console.log( player );
	$.ajax({
		type : "post",
		url : "/api/board/edit/",
		data : JSON.stringify(playerPlay),
	    contentType: 'application/json; charset=utf-8',
		success : function(data){
			playerPlay.points = data.points;
			playerPlay.board = data.board;
			console.log("JUGADOR: " + playerPlay.board.blank.row);
		},
		error :function(data){
			alert("LLL");
		}
	});
	//postForObject( playerPlay, "/api/board/edit/", function(data){console.log("LLEGAAA: " + data.boar.blank);}, function(data){})
}