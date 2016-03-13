package rest.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import entities.Board;
import entities.Game;
import entities.Player;


/**
 * @author dadsez
 *
 */
@RestController
public class GameController 
{
	
	public final static String TOPIC_URI = "/topics/game";
	private Game game;
	
	private SimpMessagingTemplate template;
	
	/**
	 * Constructor de @GameController .
	 * @param template: Encargado de enviar mensajes usando WebSokets para actualizar la GUI.
	 */
	@Autowired
	public GameController(SimpMessagingTemplate template) 
	{
		game = new Game();  //QUE SE ACTAULIZAN AL ENTRAR.i
		this.template = template;
		//System.out.println("CNSTR");
	}
	
	
	/**
	 * Crea una instancia de @Game para iniciar un nuevo juego.
	 * @return game: El @Game creado.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/game/new", method = RequestMethod.GET)
	public Game startNewGame()
	{
		this.game.setNewSuscriber(true );
		//System.out.println("START NEW GAME");
		this.sendGameUpdate();
		return game;
	}
	
	/**
	 * Notifica que ya se ha actualizado el nuevo suscriptor.
	 */
	@RequestMapping(value="/api/game/endUpdate", method = RequestMethod.GET)
	public void gameStarted()
	{
		this.game.setNewSuscriber(false );
	}
	
	/**
	 * Crea una instancia de @Board para agregar un nuevo tablero reto al juego juego.
	 * @param board: El tablero a crear.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/board/new", method = RequestMethod.POST)
	public void startNewBoard( @RequestBody Board board )
	{	
		this.game.addBoard(board);
		this.sendGameUpdate();
	}

	/**
	 * Crea una instancia de @Board para agregar un nuevo tablero reto al juego juego.
	 * @param board: El tablero a crear.
	 */
	//@CrossOrigin(origins="*")
	@RequestMapping(value="/api/board/edit", method = RequestMethod.POST)
	public Player editBoard( @RequestBody Player player )
	{	
		Player p = this.game.changeBoard( player );
		
		if ( p != null ) 
			this.sendGameUpdate();
		
		return p;
	}
	
	/**
	 * Crea una instancia de @Board para agregar un nuevo tablero reto al juego juego.
	 * @param board: El tablero a crear.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/player/new", method = RequestMethod.POST)
	public Player startNewPlayer( @RequestBody Player player )
	{
		Player p = this.game.addBoardToPlayer( player );
		
		if ( p != null ) 
			this.sendGameUpdate();
		
		return p;
	}
	
	/*@CrossOrigin(origins="*")
	@RequestMapping( value = "/api/{player}/board", method = RequestMethod.POST)
	public Board setNextPosition(@PathVariable Integer player, @RequestBody Board board){
		if( player == Player.PLAYER_1 ){
			game.getP1().setBoard(board);
			game.getP1().getBoard().addMovement();
			sendGameUpdate();
			return game.getP1().getBoard();
		}else {
			game.getP2().setBoard(board);
			game.getP2().getBoard().addMovement();
			sendGameUpdate();
			return game.getP2().getBoard();
		}
	}*/
	
	public void sendGameUpdate()
	{
		template.convertAndSend(TOPIC_URI , game);
	}

}
