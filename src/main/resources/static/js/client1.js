function generateMatrix( )
{
	var board = new Object();
	var size = Math.round (Math.random( ) * 10 );
	if(size < 2 )
		size = 2;
	
	board.movements = 0;
	board.blank = new Object ();
	board.blank.row = Math.round ( Math.random( ) * size );
	board.blank.column = Math.round ( Math.random( ) * size );

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
	postForObject( player, "/api/player/new/", function(data){console.log("LLEGAAA: " + data.name);}, function(data){})
}