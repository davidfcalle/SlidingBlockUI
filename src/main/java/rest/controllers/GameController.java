package rest.controllers;


import java.util.ArrayList;
import java.util.List;

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
	private List<Game> eventsQueue;
	private boolean updatingQueue;
	private Reviewer reviewer;
	private SimpMessagingTemplate template;
	
/**-------------------------------------------------------------------Comunicacion---------------------------------------------------------*/
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Constructor de GameController, donde instancia un objeto Game.
	 * @param template: Encargado de enviar mensajes usando WebSokets para actualizar la vista.
	 */
	@Autowired
	public GameController(SimpMessagingTemplate template) 
	{
		this.game = new Game();  //QUE SE ACTAULIZAN AL ENTRAR.i
		this.eventsQueue = new ArrayList<>();
		this.updatingQueue = false;
		this.reviewer = new Reviewer();
		this.template = template;
		this.reviewer.start();
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Agrega un nuevo evento, para luego notificar a la vista su respectiva actualizacion,
	 * a la cola de eventos controlando en no entrar en condicion de carrera.
	 */
	public void addEvent()
	{
		while( updatingQueue );
		
		updatingQueue = true;
		this.eventsQueue.add( new Game( this.game ) );
		updatingQueue = false;		
	}

	//---------------------------------------------------------------------------------------------------------------------------------------
	public void sendGameUpdate( Game newGame )
	{
		template.convertAndSend( TOPIC_URI , newGame );
	}
	
/**--------------------------------------------------------------Creacion-----------------------------------------------------------------*/
	
	//---------------------------------------------------------------------------------------------------------------------------------------		
	/**
	 * Crea una instancia de Game para iniciar un nuevo juego.
	 * @return game: El Game creado.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/game/new", method = RequestMethod.POST)
	public Game startNewGame()
	{
		this.game.setNewSuscriber(true );
		//System.out.println("START NEW GAME");
		this.addEvent();
		return game;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------			
	/**
	 * Agrega el tablero que viene como datos en la peticion HTTP 
	 * como un nuevo tablero reto al juego.
	 * @param board: El tablero a agregar.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/board/new", method = RequestMethod.POST)
	public void startNewBoard( @RequestBody Board board )
	{	
		this.game.addBoard( board );
		this.addEvent();
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Delega al objeto Game agregar un nuevo jugador al juego y asociarle un tablero. 
	 * Para esto le envia el objeto Player que viene como datos de la peticion HTTP.
	 * @param Player: El juagdor a agregar.
	 * @return p: Objeto Player que se agrego al juego.
	 * 			  Null si no se pudo agregar al jugador 
	 * 			  debido a que no hay tableros disponibles.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/player/new", method = RequestMethod.POST)
	public Player startNewPlayerPro( @RequestBody Player player )
	{
		Player p = this.game.addBoardToPlayer( player );
		
		if ( p != null ) 
			this.addEvent();
		
		return p;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Delega al objeto Game agregar un nuevo jugador al juego y asociarle un tablero. 
	 * Para esto crea un Player con id @idPlayer y nombre @namePlayer y lo envia.
	 * @param idPlayer: Id del juagdor a agregar.
	 * @param namePlayer: Nombre del juagdor a agregar.
	 * @return p: Objeto Player que se agrego al juego.
	 * 			  Null si no se pudo agregar al jugador 
	 * 			  debido a que no hay tableros disponibles.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/player/{idPlayer}/new/{namePlayer}", method = RequestMethod.POST)
	public Player startNewPlayer( @PathVariable Integer idPlayer, @PathVariable String namePlayer )
	{
		Player player  = new Player( );
		player.setId( idPlayer );
		player.setName( namePlayer );
		player.setPoints( 0 );
		Player p = this.game.addBoardToPlayer( player );
		
		if ( p != null ) 
			this.addEvent();
		
		return p;
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------------			
	/**
	 * Notifica que ya se ha actualizado el nuevo suscriptor. //AUN NO LO USO
	 */
	@RequestMapping(value="/api/game/endUpdate", method = RequestMethod.GET)
	public void gameStarted()
	{
		this.game.setNewSuscriber(false );
	}

/**--------------------------------------------------------------Movimiento---------------------------------------------------------------*/
	
	//-------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Delega al objeto Game cambiar la pieza blanca a la posicion 
	 * indicada en el objeto Piece (atributo blank de Player). 
	 * Para luego actualizar la vista con la nueva configuracion 
	 * del tablero Taquin si el movimiento es valido.
	 * @param player: Objeto Player que contiene el tablero a actualizar.
	 * @return p: Objeto Player que posee el tablero al que se le movio la pieza blanca.
	 * 			   Null si el movimiento no es valido.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/board/move", method = RequestMethod.POST)
	public Player movePieceOnBoardPro( @RequestBody Player player )
	{	
		Player p = this.game.movePieceOnBoard( player, player.getBoard().getBlank() );
		
		if ( p != null ) 
			this.addEvent();
		
		return p;
	}

	/**
	 * Delega al objeto Game cambiar de posicion la pieza blanca hacia la derecha. 
	 * Para luego actualizar la vista con la nueva configuracion del tablero Taquin. Si el movimiento es valido.
	 * @param idPlayer: Id del jugador que contiene el tablero a actualizar.
	 * @return p: Objeto Player que posee el tablero al que se le movio la pieza blanca.
	 * 			  Null si el movimiento no es valido.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/player/{idPlayer}/board/move/right", method = RequestMethod.POST)
	public Player movePieceOnBoardToRight( @PathVariable Integer idPlayer )
	{
		Player p = this.game.movePieceOnBoardToRight( idPlayer );
		
		if ( p != null ) 
			this.addEvent();
		
		return p;
	}
	
	/**
	 * Delega al objeto Game cambiar de posicion la pieza blanca hacia la izquierda. 
	 * Para luego actualizar la vista con la nueva configuracion del tablero Taquin. Si el movimiento es valido.
	 * @param idPlayer: Id del jugador que contiene el tablero a actualizar.
	 * @return p: Objeto Player que posee el tablero al que se le movio la pieza blanca.
	 * 			  Null si el movimiento no es valido.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/player/{idPlayer}/board/move/left", method = RequestMethod.POST)
	public Player movePieceOnBoardToLeft( @PathVariable Integer idPlayer )
	{
		Player p = this.game.movePieceOnBoardToLeft( idPlayer );
		
		if ( p != null ) 
			this.addEvent();
		
		return p;
	}
	
	/**
	 * Delega al objeto Game cambiar de posicion la pieza blanca hacia arriba. 
	 * Para luego actualizar la vista con la nueva configuracion del tablero Taquin. Si el movimiento es valido.
	 * @param idPlayer: Id del jugador que contiene el tablero a actualizar.
	 * @return p: Objeto Player que posee el tablero al que se le movio la pieza blanca.
	 * 			  Null si el movimiento no es valido.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/player/{idPlayer}/board/move/up", method = RequestMethod.POST)
	public Player movePieceOnBoardToUp( @PathVariable Integer idPlayer )
	{
		Player p = this.game.movePieceOnBoardToUp( idPlayer );
		
		if ( p != null ) 
			this.addEvent();
		
		return p;
	}
	
	/**
	 * Delega al objeto Game cambiar de posicion la pieza blanca hacia abajo
	 * Para luego actualizar la vista con la nueva configuracion del tablero Taquin. Si el movimiento es valido.
	 * @param idPlayer: Id del jugador que contiene el tablero a actualizar.
	 * @return p: Objeto Player que posee el tablero al que se le movio la pieza blanca.
	 * 			  Null si el movimiento no es valido.
	 */
	@CrossOrigin(origins="*")
	@RequestMapping(value="/api/player/{idPlayer}/board/move/down", method = RequestMethod.POST)
	public Player movePieceOnBoardToDown( @PathVariable Integer idPlayer )
	{
		Player p = this.game.movePieceOnBoardToDown( idPlayer );
		
		if ( p != null ) 
			this.addEvent();
		
		return p;
	}
	
	public class Reviewer extends Thread
	{

		private boolean running;
	    
		public Reviewer()
	    {
	      this.running = true;
	    }

	    @Override
	    public void run()
	    {
	      while ( this.running )
	      {
	        while ( !updatingQueue )
	        {
	        	updatingQueue = true;
	        	if( eventsQueue.size() > 0 )
	        	{
	        		Game newGame = eventsQueue.get( 0 );
		        	eventsQueue.remove( 0 );
		        	sendGameUpdate( newGame );
	        	}
	        	updatingQueue = false;
	        	
	        	try 
	        	{
					Thread.sleep( 50 );
				} 
	        	catch (InterruptedException e) 
	        	{
					e.printStackTrace();
				}
	        }
	        try 
        	{
				Thread.sleep( 200 );
			} 
	        catch (InterruptedException e) 
        	{
				e.printStackTrace();
			}
	      }
	   }
	    
	   public void stopReview()
	   {
		   this.running = false;
	   }
	}
}