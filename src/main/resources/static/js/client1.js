var playerPlay = new Object();

function generateMatrix( )
{
	var board = new Object();
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
	console.log(board);
	postForObject( board, "/api/board/new/", function(data){}, function(data){})
	showGeneratedBoard( board );
}

function generatePlayer( )
{
	var player = new Object();
	player.id = 0;
	player.name = $("#playerName").val();
	player.points = 0;
	player.board = new Object();
	//console.log( player );
	$.ajax({
		type : "post",
		url : "/api/player/new/",
		data : JSON.stringify(player),
	    contentType: 'application/json; charset=utf-8',
		success : function(data){
			if ( data != null )
			{	
				playerPlay.id = data.id;
				playerPlay.name = data.name;
				playerPlay.points = data.points;
				playerPlay.board = data.board;
				$("#playerLabel").text( "Player " + playerPlay.id + ": " );
				$("#playerName").val( playerPlay.name );
				$("#playerName").attr( {readOnly:"readOnly"});
				//console.log("JUGADOR: " + playerPlay.name);
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

function moveBoardToRight( )
{
	playerPlay.board.blank.column++;
	//console.log( player );
	moveBoard( );
}

function moveBoardToLeft( )
{
	playerPlay.board.blank.column--;
	//console.log( player );
	moveBoard( );
}

function moveBoardToUp( )
{
	playerPlay.board.blank.row--;
	//console.log( player );
	moveBoard( );
}

function moveBoardToDown( )
{
	playerPlay.board.blank.row++;
	//console.log( player );
	moveBoard( );
}

function moveBoard( )
{
	$.ajax({
		type : "post",
		url : "/api/board/edit/",
		data : JSON.stringify(playerPlay),
	    contentType: 'application/json; charset=utf-8',
		success : function(data){
			if( data != null )
			{
				playerPlay.points = data.points;
				playerPlay.board = data.board;
				showGeneratedBoard( data.board );
				//console.log("JUGADOR: " + playerPlay.board.blank.row);
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
function showGeneratedBoard( board )
{
	var size = board.currentState.length;
	$("#blankRow").val( board.blank.row +1 );
	$("#blankColumn").val( board.blank.column + 1 );
	$("#sizeBoard").val( size );
	$("#board .piece").remove();
	for( var i = 0 ; i < size; i++)
	{
		for( var j = 0 ; j < size; j++)
		{
			var templateCopy = $( $( $("#piece-template").html() ).clone() );
			templateCopy.css( { minWidth : ( (100 / size ) - 1 +"%" ), minHeight : 70 / size + "vh"  } );
			
			if( i == board.blank.row && j == board.blank.column )
				templateCopy.text( "BB" );
			else
				templateCopy.text( board.currentState[i][j] );
			
			$( "#board" ).append(templateCopy);
		}
	}
}

/*
function showGeneratedBoard ( board )
{
	$("#board tr").remove();
	var table = $("#board");
	
	var newRow = $( "<tr>" );
	var newColumn = $( "<th>" );
	newColumn.text( " " );
	newRow.append( newColumn );
	for (var i = 0; i < board.currentState.length; i++)
	{
		var newColumn = $( "<th>" );
		newColumn.text( i + 1 );
		newRow.append( newColumn );
	}
	table.append( newRow );
	
	for (var i = 0; i < board.currentState.length; i++)
	{	
		var newRow = $( "<tr>" );
		var newColumn = $( "<th>" );
		newColumn.text( i + 1 );
		newRow.append( newColumn );
		for (var j = 0; j < board.currentState.length; j++)
		{
			var newColumn = $( "<td>" );
			newColumn.text( board.currentState[i][j] );
			newRow.append( newColumn );
		}
		table.append( newRow );
	}
}*/