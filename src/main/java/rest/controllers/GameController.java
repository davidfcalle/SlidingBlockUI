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


@RestController
public class GameController {
	public final static String TOPIC_URI = "/topics/game";
	private Game game;
	
	private SimpMessagingTemplate template;
	
	@Autowired
	public GameController(SimpMessagingTemplate template) {
		game = new Game();
		this.template = template;
	}
	
	@RequestMapping(value="/api/game/new", method = RequestMethod.GET)
	public Game startNewGame(){
		game = new Game(); // solo para pruebas
		sendGameUpdate();
		return game;
	}
	
	@RequestMapping(value="/api/game")
	public Game getBoard(){
		return game;
	}
	
	
	@CrossOrigin(origins="*")
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
	}
	
	public void sendGameUpdate(){
		template.convertAndSend(TOPIC_URI , game);
	}

}
