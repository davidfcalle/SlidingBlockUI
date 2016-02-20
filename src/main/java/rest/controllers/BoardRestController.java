package rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import repositories.BoardRepository;

import entities.Board;
@RestController
public class BoardRestController {
	
	@Autowired
	private BoardRepository boardRepository;
		
	/**
	 * el request mapping se encarga de decirle a qué URL "ecucha" el servicio
	 * el produces hace referencia a la manera en que los datos se van a serializar
	 * el retorno del método es un objeto Java, como es un rest controller, el mismo se encarga de serializar los datos
	 * @param name
	 * @return
	 */
	@RequestMapping(path="/api/boards/search/", produces="application/json")
	public List<Board> findByName(@RequestParam( name="name") String name){
		return boardRepository.findAllByName(name);
	}
	
}
